package com.shop.demo;


import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CreatIndexTest {

	private static CreatIndex creatIndex = new CreatIndex();

	@Test
	public void testNewIndex() {
		creatIndex.newIndex("F:/luceneTest");
	}

	@Test
	public void testSearcherIndex() {
		creatIndex.searcherIndex("lucene");
	}

}
