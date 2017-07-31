package com.shop.demo2.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
public class BaseBean implements Serializable{
	
	private Integer id;
	
	private String remarks;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	

}
