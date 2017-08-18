package com.shop.demo.dao;


import com.shop.demo.entity.User;
public interface UserDao {
	
	public User getById(Integer id);
	
	public void insert(User user);
	
	public void del(User user);

}
