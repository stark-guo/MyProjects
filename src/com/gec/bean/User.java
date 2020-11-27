package com.gec.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * #创建表user_inf（用户表）STATUS=1，用户是管理员，否则是普通用户
CREATE TABLE user_inf (
  ID INT(11) NOT NULL AUTO_INCREMENT,
  loginname VARCHAR(20) NOT NULL,
  PASSWORD VARCHAR(16) NOT NULL,
  STATUS INT(11) NOT NULL DEFAULT '1',
  createdate TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  username VARCHAR(20) DEFAULT NULL,
  PRIMARY KEY (ID)
)
 */
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	//ctrl+shift+y  小写   
	//ctrl+shift+x  大写   
	private Integer id;
	private String loginname;
	private String password;
	private String username;
	private Integer status;
	public User(Integer id) {
		super();
		this.id = id;
	}
	private Date createdate;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}
	public User(Integer id, String loginname, String password, String username, Integer status, Date createdate) {
		super();
		this.id = id;
		this.loginname = loginname;
		this.password = password;
		this.username = username;
		this.status = status;
		this.createdate = createdate;
	}
	public User() {
		super();
	}
	public User(String username, Integer status) {
		this.username = username;
		this.status = status;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", loginname=" + loginname + ", password=" + password + ", username=" + username
				+ ", status=" + status + ", createdate=" + createdate + "]";
	}
	
}
