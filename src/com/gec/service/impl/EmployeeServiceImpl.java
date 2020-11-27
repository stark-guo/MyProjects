package com.gec.service.impl;

import java.util.List;

import com.gec.bean.Employee;
import com.gec.bean.PageBean;
import com.gec.dao.EmployeeDao;
import com.gec.dao.impl.EmployeeDaoImpl;
import com.gec.service.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService {
EmployeeDao ed=new EmployeeDaoImpl();
	@Override
	public List<Employee> findAll() {
		// TODO Auto-generated method stub
		return ed.findAll();
	}

	@Override
	public Employee findById(int id) {
		// TODO Auto-generated method stub
		return ed.findById(id);
	}

	@Override
	public PageBean<Employee> findPage(int pageNow, Employee entity) {
		// TODO Auto-generated method stub
		return ed.findPage(pageNow, entity);
	}

	@Override
	public boolean save(Employee entity) {
		// TODO Auto-generated method stub
		return ed.save(entity);
	}

	@Override
	public boolean update(Employee entity) {
		// TODO Auto-generated method stub
		return ed.update(entity);
	}

	@Override
	public boolean delete(int[] id) {
		// TODO Auto-generated method stub
		return ed.delete(id);
	}

}
