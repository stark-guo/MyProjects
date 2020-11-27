package com.gec.bean;

import java.util.Date;

public class Notice {
	private Integer id;
	private String name;
	private User user;
	private Date createDate;
	private Date modifyDate;
	private Type type;
	private String content;
	

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


	public Notice(String name) {
		this.name = name;
	}


	public Notice() {
		super();
	}


	public Type getType() {
		return type;
	}


	public void setType(Type type) {
		this.type = type;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public Notice(String name, Type type) {
		super();
		this.name = name;
		this.type = type;
	}


	@Override
	public String toString() {
		return "Notice [id=" + id + ", name=" + name + ", user=" + user + ", createDate=" + createDate + ", modifyDate="
				+ modifyDate + ", type=" + type + ", content=" + content + "]";
	}


}
