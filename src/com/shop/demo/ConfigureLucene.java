package com.shop.demo;

import java.io.File;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

public class ConfigureLucene {
	private ConfigureLucene(){
		
	}
	//创建索引库
	private static Directory dir = null;
	//创建分词器
	private static Analyzer ana = null;
	
	static{
		//根据指定的路径创建索引库，如果路径不存在就会创建
		try{
			dir = FSDirectory.open(new File("F:/Lucene"));
            //不同的分词器的版本不同，分词的算法不同，StandardAnalyzer只适用于英文  
            //ana=new StandardAnalyzer(Version.LUCENE_30); 
			ana = new IKAnalyzer(true);
		}catch (Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public static Directory getDir(){
		return dir;
	}
	public static Analyzer getAna(){
		return ana;
	}

}
