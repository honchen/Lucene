package com.shop.demo2.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.concurrent.Callable;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

public class SystemRedisCache implements Cache{
	
	private RedisTemplate<String,Object> redisTemplate;
	
	private String name;
	
	private long timeout;
	
    public RedisTemplate<String, Object> getRedisTemplate() {  
        return redisTemplate;    
    }  
      
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {  
        this.redisTemplate = redisTemplate;    
    }  
    
    public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public void setName(String name) {  
        this.name = name;    
    }  
      
    @Override    
    public String getName() {  
       // TODO Auto-generated method stub    
        return this.name;    
    }  
  
    @Override    
    public Object getNativeCache() {  
      // TODO Auto-generated method stub    
        return this.redisTemplate;    
    }   

	@Override
	public ValueWrapper get(Object key) {
		// TODO Auto-generated method stub
	    System.out.println("读取缓存");  
	       final String keyf =  key.toString();  
	       Object object = null;  
	       object = redisTemplate.execute(new RedisCallback<Object>() {  
	       public Object doInRedis(RedisConnection connection)    
	                   throws DataAccessException {  
	           byte[] key = keyf.getBytes();  
	           byte[] value = connection.get(key);  
	           if (value == null) {  
	              return null;  
	             }  
	           return toObject(value);  
	           }  
	        });  
	         return (object != null ? new SimpleValueWrapper(object) : null);   
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Object key, Class<T> type) {
		// TODO Auto-generated method stub
		if(StringUtils.isNullOrEmpty(key)||null==type){
			return null;
		}else{
			final String finalKey;
			final Class<T> finalType = type;
			if(key instanceof String){
				finalKey =(String)key;
			}else{
				finalKey = key.toString();
			}
            final Object object = redisTemplate.execute(new RedisCallback<Object>() {  
                public Object doInRedis(RedisConnection connection) throws DataAccessException {  
                    byte[] key = finalKey.getBytes();  
                    byte[] value = connection.get(key);  
                    if (value == null) {  
                        return null;  
                    }  
                    return toObject(value);  
                }  
            });  
            if(finalType!=null&&finalType.isInstance(object)&&null!=object){
            	return (T)object;
            }else{
            	return null;
            }
		}
	}

	@Override
	public void put(Object key, Object value) {
        System.out.println("插入缓存");  
        final String keyf = key.toString();    
        final Object valuef = value;    
        final long liveTime = timeout;    
        redisTemplate.execute(new RedisCallback<Long>() {    
            public Long doInRedis(RedisConnection connection)    
                    throws DataAccessException {    
                 byte[] keyb = keyf.getBytes();    
                 byte[] valueb = toByteArray(valuef);    
                 connection.set(keyb, valueb);    
                 if (liveTime > 0) {    
                     connection.expire(keyb, liveTime);    
                  }    
                 return 1L;    
              }    
          });     
		
	}
    private Object toObject(byte[] bytes) {
        Object obj = null;
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInputStream ois = new ObjectInputStream(bis);
            obj = ois.readObject();
            ois.close();
            bis.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return obj;
    }
    
    private byte[] toByteArray(Object obj) {    
        byte[] bytes = null;    
        ByteArrayOutputStream bos = new ByteArrayOutputStream();    
        try {    
          ObjectOutputStream oos = new ObjectOutputStream(bos);    
          oos.writeObject(obj);    
          oos.flush();    
          bytes = bos.toByteArray();    
          oos.close();    
          bos.close();    
         }catch (IOException ex) {    
              ex.printStackTrace();    
         }    
         return bytes;    
       }  

	@Override
	public <T> T get(Object key, Callable<T> valueLoader) {
		return null;
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void evict(Object key) {
		// TODO Auto-generated method stub
		   System.out.println("del key");  
	          final String keyf = key.toString();    
	          redisTemplate.execute(new RedisCallback<Long>() {    
	          public Long doInRedis(RedisConnection connection)    
	                    throws DataAccessException {    
	              return connection.del(keyf.getBytes());    
	             }    
	           });     
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
        System.out.println("clear key");  
       redisTemplate.execute(new RedisCallback<String>() {    
            public String doInRedis(RedisConnection connection)    
                    throws DataAccessException {    
              connection.flushDb();    
                return "ok";    
           }    
       });         
	}
}
