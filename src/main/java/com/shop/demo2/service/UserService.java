package com.shop.demo2.service;


import com.shop.demo2.bean.User;

public interface UserService {
	
	public User getById(Integer id);
	
	public void insert(User user);
	
	public void del(User user);

}
