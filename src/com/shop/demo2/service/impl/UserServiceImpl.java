package com.shop.demo2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

import com.shop.demo2.bean.User;
import com.shop.demo2.dao.UserDao;
import com.shop.demo2.service.UserService;

public class UserServiceImpl implements UserService{
    @Autowired  
    private UserDao userDao;  
  
	@Override
	@CacheEvict(value="common",key="'id_'+#id")  
	public User getById(Integer id) {
		// TODO Auto-generated method stub
		return userDao.getById(id);
	}

	@Override
	@Cacheable(value="common",key="'id_'+#id")  
	public void insert(User user) {
		// TODO Auto-generated method stub
		userDao.insert(user);
		
	}

	@Override
	@CacheEvict(value="common",key="'id_'+#id")  
	public void del(User user) {
		// TODO Auto-generated method stub
		userDao.del(user);
	}  
}
