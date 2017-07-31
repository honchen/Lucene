package com.shop.demo;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.util.Version;

@SuppressWarnings("deprecation")
public class LuceneUtil {
	private static IndexWriter indexWriter = null;
	
	private static IndexSearcher indexSearcher = null;
	
	private static  DirectoryReader ireader =null;
	
	static{
		try{
			IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_CURRENT, ConfigureLucene.getAna());
			indexWriter = new IndexWriter(ConfigureLucene.getDir(), config);
			Runtime.getRuntime().addShutdownHook(new Thread(){
				@Override
				public void run(){
					try{
						System.out.println("--J2SE 资源销毁的代码执行--");
						closeIndexWriter();
					}catch (Exception e){
						throw new RuntimeException(e);
					}
				}
			});
		}catch (Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public static IndexWriter getIndexWriter(){
		closeIreader();
		return indexWriter;
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

	public static IndexSearcher getIndexSearcher(){
		
		if(indexSearcher == null){
			synchronized(LuceneUtil.class){
				if(indexSearcher == null){
					try{
					     ireader = DirectoryReader.open(ConfigureLucene.getDir());
					     indexSearcher = new IndexSearcher(ireader);
					     
					}catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
		}
		
		return indexSearcher;
	}
	
	public static void closeIreader(){
		if(ireader!=null){
			try{
				ireader.close();
				ireader = null;
			}catch (Exception e){
				throw new RuntimeException(e);
			}
		}
	}

}
