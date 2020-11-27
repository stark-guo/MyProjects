package com.gec.service.impl;

import java.util.List;

import com.gec.bean.PageBean;
import com.gec.bean.User;
import com.gec.dao.UserDao;
import com.gec.dao.impl.UserDaoImpl;
import com.gec.service.UserService;

public class UserServiceImpl implements UserService {

	UserDao ud = new UserDaoImpl();
	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return ud.findAll();
	}

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		return ud.findById(id);
	}

	@Override
	public PageBean<User> findPage(int pageNow, User entity) {
		// TODO Auto-generated method stub
		return ud.findPage(pageNow, entity);
	}

	@Override
	public boolean save(User entity) {
		// TODO Auto-generated method stub
		return ud.save(entity);
	}

	@Override
	public boolean update(User entity) {
		// TODO Auto-generated method stub
		return ud.update(entity);
	}

	@Override
	public boolean delete(int []id) {
		// TODO Auto-generated method stub
		return ud.delete(id);
	}

	@Override
	public User login(String loginname, String password) {
		// TODO Auto-generated method stub
		return ud.login(loginname, password);
	}

}
