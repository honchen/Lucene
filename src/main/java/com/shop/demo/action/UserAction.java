package com.shop.demo.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.demo.entity.User;
import com.shop.demo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserAction {
	
	@Autowired
	private UserService userServiceImpl;

	@RequestMapping(value={"view",""})
	public String Test(){
		for (Integer i = 0; i < 5; i++) {
			User user = new User();
			user.setName("MYname"+i.toString());
			user.setEmail("MY123456@"+i.toString());
			user.setIdCard("MY1593571!"+i.toString());
			user.setTel("MY11111-"+i.toString());
			user.setRemarks("---MY---");
			userServiceImpl.insert(user);
		}
		return "/NewJsp";
	}
	
	@RequestMapping("insert")
	public String LuceneTest(){
		
		for (Integer i = 0; i < 10; i++) {
			User user = new User();
			user.setName("name"+i.toString());
			user.setEmail("123456@"+i.toString());
			user.setIdCard("1593571!"+i.toString());
			user.setTel("11111-"+i.toString());
			user.setRemarks("------");
			userServiceImpl.redisinsert(user);
		}
		return "/MyJsp";
	}

}
