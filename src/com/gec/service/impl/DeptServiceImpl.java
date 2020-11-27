package com.gec.service.impl;

import java.util.List;

import com.gec.bean.Dept;
import com.gec.bean.PageBean;
import com.gec.dao.DeptDao;
import com.gec.dao.impl.DeptDaoImpl;
import com.gec.service.DeptService;

public class DeptServiceImpl implements DeptService {
DeptDao dp=new DeptDaoImpl();
	@Override
	public List<Dept> findAll() {
		// TODO Auto-generated method stub
		return dp.findAll();
	}

	@Override
	public Dept findById(int id) {
		// TODO Auto-generated method stub
		return dp.findById(id);
	}

	@Override
	public PageBean<Dept> findPage(int pageNow, Dept entity) {
		// TODO Auto-generated method stub
		return dp.findPage(pageNow, entity);
	}

	@Override
	public boolean save(Dept entity) {
		// TODO Auto-generated method stub
		return dp.save(entity);
	}

	@Override
	public boolean update(Dept entity) {
		// TODO Auto-generated method stub
		return dp.update(entity);
	}

	@Override
	public boolean delete(int[] id) {
		// TODO Auto-generated method stub
		return dp.delete(id);
	}

}
