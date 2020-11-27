package com.gec.dao;

import com.gec.bean.User;

public interface UserDao extends BaseDao<User> {

	User login(String loginname,String password);
}
