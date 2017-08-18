package com.shop.demo.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Date;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.util.Version;

public class CreatIndex {
	
	
	private static IndexWriter indexWriter = null;
	
	private static String INDEX_DIR = "E:/lucene"; 

	@SuppressWarnings("deprecation")
	public static boolean newIndex(String path) {
		Date date = new Date();
		File file = new File(path);
		File[] files = file.listFiles();
		for (File file2 : files) {
			if(file2.isFile()){
				System.out.println("文件名："+file2.getName());
				System.out.println("路径:"+file.getPath());
				String content = txt2String(file2);
				try {
					File indexFile = new File(INDEX_DIR);
					if(!indexFile.exists()){
						indexFile.mkdirs();
					}
					IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_CURRENT,ConfigureLucene.getAna());
					indexWriter = new IndexWriter(ConfigureLucene.getDir(),config);
					Document document = new Document();
					document.add(new TextField("filename",file2.getName(),Store.YES));
					document.add(new TextField("content",content,Store.YES));
					document.add(new TextField("path",file2.getPath(),Store.YES));
					indexWriter.addDocument(document);
					indexWriter.commit();
					closeIndexWriter();
				} catch (Exception e) {
					// TODO: handle exception
					throw new RuntimeException(e);
				}
				
			}
		}
		Date date2 = new Date();
		System.out.println("--创建索引时间--"+(date.getTime()-date2.getTime())+"ms\n");
		return true;
	}
	
 	private static void closeIndexWriter() {
		// TODO Auto-generated method stub
		if(indexWriter!=null){
			try{
				indexWriter.close();
				indexWriter = null;
			}catch (Exception e){
				throw new RuntimeException(e);
			}
		}
	}
	
	public static String txt2String(File file){
		StringBuffer result = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s = null;
			while((s=br.readLine())!=null){
				result.append(System.lineSeparator()+s);
			}
			br.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return result.toString();
	}
	
	@SuppressWarnings("deprecation")
	public static void searcherIndex(String text){
		Date date = new Date();
		try {
			DirectoryReader ireader = DirectoryReader.open(ConfigureLucene.getDir());
			IndexSearcher isearcher = new IndexSearcher(ireader);
			
			QueryParser parser = new QueryParser(Version.LUCENE_CURRENT,"content",ConfigureLucene.getAna());
			Query query = parser.parse(text);
			
			ScoreDoc[] hits = isearcher.search(query, null,1000).scoreDocs;
			
			for (int i = 0; i < hits.length; i++) {
				Document hitDoc = isearcher.doc(hits[i].doc);
				System.out.println("----------------");
				System.out.println(hitDoc.get("filename"));
				System.out.println(hitDoc.get("content"));
				System.out.println(hitDoc.get("path"));
				System.out.println("----------------");
			}
			ireader.close();
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new RuntimeException(e);
		}
		Date date2 = new Date();
		System.out.println("--查询索引时间--"+(date.getTime()-date2.getTime())+"ms\n");
	}
	
	
}
