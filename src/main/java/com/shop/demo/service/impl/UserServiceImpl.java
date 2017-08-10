package com.shop.demo.service.impl;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shop.demo.dao.UserDao;
import com.shop.demo.entity.User;
import com.shop.demo.service.UserService;
@Service
public class UserServiceImpl implements UserService{
	
	@Autowired(required=false)
    private UserDao userDao; 
    

	@Override
//	@CacheEvict(value="common",key="'id_'+#id")  
	public User getById(Integer id) {
		return userDao.getById(id);
	}

	@Override
//	@Cacheable(value="common",key="'id_'+#id")  
	@Transactional(readOnly=false)
	public void insert(User user) {
		
		userDao.insert(user);
	}

	@Override
//	@CacheEvict(value="common",key="'id_'+#id")  
	@Transactional(readOnly=false)
	public void del(User user) {
		userDao.del(user);
	}  
}
