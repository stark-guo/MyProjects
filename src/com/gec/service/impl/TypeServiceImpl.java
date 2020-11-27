package com.gec.service.impl;

import java.util.List;

import com.gec.bean.PageBean;
import com.gec.bean.Type;
import com.gec.dao.TypeDao;
import com.gec.dao.impl.TypeDaoImpl;
import com.gec.service.TypeService;

public class TypeServiceImpl implements TypeService {
TypeDao td=new TypeDaoImpl();
	@Override
	public List<Type> findAll() {
		// TODO Auto-generated method stub
		return td.findAll();
	}

	@Override
	public Type findById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PageBean<Type> findPage(int pageNow, Type entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean save(Type entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Type entity) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(int[] id) {
		// TODO Auto-generated method stub
		return false;
	}

}
