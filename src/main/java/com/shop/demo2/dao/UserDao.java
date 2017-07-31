package com.shop.demo2.dao;

import com.shop.demo2.bean.User;
import com.shop.demo2.util.MyBatisDao;
@MyBatisDao
public interface UserDao {
	
	public User getById(Integer id);
	
	public void insert(User user);
	
	public void del(User user);

}
