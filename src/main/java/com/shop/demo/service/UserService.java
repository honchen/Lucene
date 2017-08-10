package com.shop.demo.service;

import com.shop.demo.entity.User;

public interface UserService {
	
	public User getById(Integer id);
	
	public void insert(User user);
	
	public void del(User user);

}
