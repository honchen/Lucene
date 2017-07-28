package com.shop.demo2.dao;

import com.shop.demo2.bean.User;

public interface UserDao {
	
	public User getById(Integer id);
	
	public void insert(User user);
	
	public void del(User user);

}
