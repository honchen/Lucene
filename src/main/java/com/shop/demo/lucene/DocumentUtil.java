package com.shop.demo.lucene;


import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;

public class DocumentUtil {
	private DocumentUtil(){}
	
	public static Document goodsToDocument(Goods goods){
		Document doc = new Document();
		doc.add(new Field("id",goods.getId().toString(),TextField.TYPE_STORED));
		doc.add(new Field("name", goods.getName(), TextField.TYPE_STORED));
		doc.add(new Field("price",goods.getPrice().toString(),TextField.TYPE_STORED));
		doc.add(new Field("remark",goods.getRemark(),TextField.TYPE_STORED));
		return doc;
	} 
	
	public static Goods documentToGoods(Document doc){
		Goods goods = new Goods();
		goods.setId(Integer.parseInt(doc.get("id")));
		goods.setName(doc.get("name"));
		goods.setPrice(Double.parseDouble(doc.get("price")));
		goods.setRemark(doc.get("remark"));
		return goods;
	}

}
