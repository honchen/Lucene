package com.shop.demo.service;

<<<<<<< HEAD:src/main/java/com/shop/demo2/service/UserService.java

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.shop.demo2.bean.User;
=======
import com.shop.demo.entity.User;
>>>>>>> d67b7aec6f965887328cbec908b67c485dc5e0d5:src/main/java/com/shop/demo/service/UserService.java

public interface UserService {
	@CacheEvict(value="common",key="'id_'+#id")  
	public User getById(Integer id);
	@Cacheable(value="common",key="'name_'+#name")  
	public void insert(User user);
	
	public void redisinsert(User user);
	@CacheEvict(value="common",key="'id_'+#id")  
	public void del(User user);

}
