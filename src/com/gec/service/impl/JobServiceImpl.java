package com.gec.service.impl;

import java.util.List;

import com.gec.bean.Job;
import com.gec.bean.PageBean;
import com.gec.dao.JobDao;
import com.gec.dao.impl.JobDaoImpl;
import com.gec.service.JobService;

public class JobServiceImpl implements JobService {
JobDao jd=new JobDaoImpl();
	@Override
	public List<Job> findAll() {
		// TODO Auto-generated method stub
		return jd.findAll();
	}

	@Override
	public Job findById(int id) {
		// TODO Auto-generated method stub
		return jd.findById(id);
	}

	@Override
	public PageBean<Job> findPage(int pageNow, Job entity) {
		// TODO Auto-generated method stub
		return jd.findPage(pageNow, entity);
	}

	@Override
	public boolean save(Job entity) {
		// TODO Auto-generated method stub
		return jd.save(entity);
	}

	@Override
	public boolean update(Job entity) {
		// TODO Auto-generated method stub
		return jd.update(entity);
	}

	@Override
	public boolean delete(int[] id) {
		// TODO Auto-generated method stub
		return jd.delete(id);
	}

}
