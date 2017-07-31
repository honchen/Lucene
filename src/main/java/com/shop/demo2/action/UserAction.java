package com.shop.demo2.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("user")
public class UserAction {
	
	@RequestMapping(value="test")
	public String LuceneTest(){
		return "index";
	}

}
