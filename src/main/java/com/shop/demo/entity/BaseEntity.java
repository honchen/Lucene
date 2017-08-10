package com.shop.demo.entity;

import java.io.Serializable;


@SuppressWarnings("serial")
public class BaseEntity implements Serializable{
	
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
