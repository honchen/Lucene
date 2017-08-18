package com.shop.demo2.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shop.demo2.bean.User;
import com.shop.demo2.dao.RedisDao;
import com.shop.demo2.dao.UserDao;
import com.shop.demo2.service.UserService;
@Repository
public class UserServiceImpl implements UserService{
    @Autowired  
    private UserDao userDao; 
    
    @Autowired
    private RedisDao redisDao;
    
	public RedisDao getRedisDao() {
		return redisDao;
	}

	public void setRedisDao(RedisDao redisDao) {
		this.redisDao = redisDao;
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	@CacheEvict(value="common",key="'id_'+#id")  
	public User getById(Integer id) {
		return userDao.getById(id);
	}

	@Override
	@Cacheable(value="common",key="'name_'+#name")  
	@Transactional(readOnly=false)
	public void insert(User user) {
		userDao.insert(user);
	}

	@Override
	@Transactional(readOnly=false)
	public void redisinsert(User user) {
		redisDao.saveUser(user);
	}

	@Override
	@CacheEvict(value="common",key="'id_'+#id")  
	@Transactional(readOnly=false)
	public void del(User user) {
		userDao.del(user);
	}  
}
