package com.gec.bean;

import java.util.Date;

public class Type {
	private Integer id;
	private String name;
	private User user;
	private Date createDate;
	private Date modifyDate;
	

	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Date getCreateDate() {
		return createDate;
	}


	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


	public Date getModifyDate() {
		return modifyDate;
	}


	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}


	public Type(String name) {
		this.name = name;
	}


	public Type() {
		super();
	}


	public Type(int id) {
		this.id = id;
	}


	@Override
	public String toString() {
		return "Notice [id=" + id + ", name=" + name + ", user=" + user + ", createDate=" + createDate + ", modifyDate="
				+ modifyDate + "]";
	}
	
	

}
