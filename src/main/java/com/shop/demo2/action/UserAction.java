package com.shop.demo2.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.demo2.bean.User;
import com.shop.demo2.service.UserService;

@Controller
@RequestMapping("/user")
public class UserAction {
	
	@Autowired
	private UserService userServiceImpl;

	@RequestMapping("")
	public String Test(){
		
		
		return "/NewJsp";
	}
	
	@RequestMapping("insert")
	public String LuceneTest(){
		
		
		for (Integer i = 0; i < 10; i++) {
			User user = new User();
			user.setId(i+1);
			user.setEmail("123456@"+i.toString());
			user.setIdCard("1593571!"+i.toString());
			user.setTel("11111-"+i.toString());
			user.setRemarks("------");
			userServiceImpl.insert(user);
		}
		
		
		return "/MyJsp";
	}

}
