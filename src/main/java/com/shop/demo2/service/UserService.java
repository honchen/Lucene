package com.shop.demo2.service;


import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.shop.demo2.bean.User;

public interface UserService {
	@CacheEvict(value="common",key="'id_'+#id")  
	public User getById(Integer id);
	@Cacheable(value="common",key="'name_'+#name")  
	public void insert(User user);
	
	public void redisinsert(User user);
	@CacheEvict(value="common",key="'id_'+#id")  
	public void del(User user);

}
