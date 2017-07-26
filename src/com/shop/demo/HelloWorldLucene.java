package com.shop.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.Version;

public class HelloWorldLucene {
	public void addDocument(Goods goods) {
		IndexWriter indexWriter = null;

		try {
			indexWriter = LuceneUtil.getIndexWriter();
			indexWriter.addDocument(DocumentUtil.goodsToDocument(goods));
			;
			indexWriter.commit();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@SuppressWarnings("deprecation")
	public List<Goods> queryGoods(String name) {
		List<Goods> goodsList = new ArrayList<Goods>();
		IndexSearcher searcher = null;
		try {
			searcher = LuceneUtil.getIndexSearcher();
			QueryParser parser = new QueryParser(Version.LUCENE_CURRENT, "name", ConfigureLucene.getAna());
			Query query = parser.parse(name);
			TopDocs topDocs = searcher.search(query, 5);
			System.out.println("真正命中的结果数：" + topDocs.totalHits);

			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (int i = 0; i < scoreDocs.length; i++) {
				ScoreDoc scoreDoc = scoreDocs[i];
				System.out.println("真正的命中率：" + scoreDoc.score);
				System.out.println("存储的是文档编号：" + scoreDoc.doc);
				Document doc = searcher.doc(scoreDoc.doc);
				System.out.println(doc.get("id"));
				System.out.println(doc.get("name"));
				System.out.println(doc.get("price"));
				System.out.println(doc.get("remark"));
				System.out.println("-------");
				goodsList.add(DocumentUtil.documentToGoods(doc));
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return goodsList;
	}

	public void deleteDocument(int id) {
		IndexWriter indexWriter = null;
		try {
			indexWriter = LuceneUtil.getIndexWriter();
			indexWriter.deleteDocuments(new Term("id", id + ""));
			indexWriter.commit();
		} catch (Exception e) {
			try {
				indexWriter.rollback();
				throw new RuntimeException(e);
			} catch (IOException e1) {
				throw new RuntimeException(e1);
			}
		}
	}

	public void updateDocument(Goods goods) {
		IndexWriter indexWriter = null;
		try {
			indexWriter = LuceneUtil.getIndexWriter();
			indexWriter.updateDocument(
					new Term("id", goods.getId().toString()),
					DocumentUtil.goodsToDocument(goods));
			indexWriter.commit();
		} catch (Exception e) {
			try {
				indexWriter.rollback();
				throw new RuntimeException(e);
			} catch (IOException e1) {
				throw new RuntimeException(e1);
			}
		}
	}

	@SuppressWarnings("deprecation")
	public List<Goods> queryByPage(String name, int currentPage) {
		int number = 5;
		List<Goods> goodsList = new ArrayList<Goods>();
		IndexSearcher indexSearcher = null;
		try {
			indexSearcher = LuceneUtil.getIndexSearcher();
			QueryParser parser = new QueryParser(Version.LUCENE_CURRENT, "name", ConfigureLucene.getAna());
			Query query = parser.parse(name);
			TopDocs topDocs = indexSearcher.search(query, currentPage * number);
			System.out.println("" + topDocs.totalHits);
			int totalPage = 0;
			if (topDocs.totalHits % number != 0) {
				totalPage = topDocs.totalHits / number + 1;
			} else {
				totalPage = topDocs.totalHits / number;
			}
			System.out.println("" + totalPage);

			ScoreDoc[] scoreDocs = topDocs.scoreDocs;

			System.out.println("" + scoreDocs.length);
			for (int i = (currentPage - 1) * number; i < scoreDocs.length; i++) {
				ScoreDoc scoreDoc = scoreDocs[i];
				System.out.println("" + scoreDoc.score);
				System.out.println("" + scoreDoc.doc);
				Document document = indexSearcher.doc(scoreDoc.doc);
				goodsList.add(DocumentUtil.documentToGoods(document));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return goodsList;
	}

	public static void main(String[] args) {
		HelloWorldLucene helloWorld =new HelloWorldLucene();
		
		for (int i = 1; i <= 10; i++) {
			Goods goods = new Goods();
			goods.setId(i);
			goods.setName("商品"+String.valueOf(i));
			goods.setPrice(Double.valueOf(i*i*i));
			goods.setRemark("备注"+String.valueOf(i));
			helloWorld.addDocument(goods);
		}
		for (int i = 1; i <= 10; i++) {
			Goods goods = new Goods();
			goods.setId(i*10);
			goods.setName("电脑商品"+String.valueOf(i));
			goods.setPrice(Double.valueOf(i*i*i*100));
			goods.setRemark("电脑备注"+String.valueOf(i));
			helloWorld.addDocument(goods);
		}
		List<Goods> goodsList = helloWorld.queryGoods("电脑");
		for (Goods good : goodsList) {
			System.out.println("商品编号：" + good.getId() + ",商品名称："
					+ good.getName() + ",商品价格：" + good.getPrice() + ",商品的详细信息："
					+ good.getRemark());
		}
	}

}
