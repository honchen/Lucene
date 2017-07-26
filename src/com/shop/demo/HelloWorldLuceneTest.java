package com.shop.demo;


import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HelloWorldLuceneTest {
	
	private static HelloWorldLucene helloWorld = new HelloWorldLucene();



	@Test
	public void testQueryGoods() {
		List<Goods> goodsList = helloWorld.queryGoods("电脑");
		for (Goods good : goodsList) {
			System.out.println("商品编号：" + good.getId() + ",商品名称："
					+ good.getName() + ",商品价格：" + good.getPrice() + ",商品的详细信息："
					+ good.getRemark());
		}
	}
	@Test
	public void testAddDocument() {
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
			goods.setId(10+i);
			goods.setName("电脑商品"+String.valueOf(i));
			goods.setPrice(Double.valueOf(i*i*i*100));
			goods.setRemark("电脑备注"+String.valueOf(i));
			helloWorld.addDocument(goods);
		}
	}
	
}
