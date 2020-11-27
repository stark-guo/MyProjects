package com.gec.service.impl;

import java.util.List;

import com.gec.bean.Notice;
import com.gec.bean.PageBean;
import com.gec.dao.NoticeDao;
import com.gec.dao.impl.NoticeDapImpl;
import com.gec.service.NoticeService;

public class NoticeServiceImpl implements NoticeService {
	NoticeDao nd=new NoticeDapImpl();

	@Override
	public List<Notice> findAll() {
		// TODO Auto-generated method stub
		return nd.findAll();
	}

	@Override
	public Notice findById(int id) {
		// TODO Auto-generated method stub
		return nd.findById(id);
	}

	@Override
	public PageBean<Notice> findPage(int pageNow, Notice entity) {
		// TODO Auto-generated method stub
		return nd.findPage(pageNow, entity);
	}

	@Override
	public boolean save(Notice entity) {
		// TODO Auto-generated method stub
		return nd.save(entity);
	}

	@Override
	public boolean update(Notice entity) {
		// TODO Auto-generated method stub
		return nd.update(entity);
	}

	@Override
	public boolean delete(int[] id) {
		// TODO Auto-generated method stub
		return nd.delete(id);
	}

}
