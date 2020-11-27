package com.gec.service.impl;

import java.util.List;

import com.gec.bean.Document;
import com.gec.bean.PageBean;
import com.gec.dao.DocumentDao;
import com.gec.dao.impl.DocumentDaoImpl;
import com.gec.service.DocumentService;

public class DocumentServiceImpl implements DocumentService {
	DocumentDao dt=new DocumentDaoImpl();

	@Override
	public List<Document> findAll() {
		// TODO Auto-generated method stub
		return dt.findAll();
	}

	@Override
	public Document findById(int id) {
		// TODO Auto-generated method stub
		return dt.findById(id);
	}

	@Override
	public PageBean<Document> findPage(int pageNow, Document entity) {
		// TODO Auto-generated method stub
		return dt.findPage(pageNow, entity);
	}

	@Override
	public boolean save(Document entity) {
		// TODO Auto-generated method stub
		return dt.save(entity);
	}

	@Override
	public boolean update(Document entity) {
		// TODO Auto-generated method stub
		return dt.update(entity);
	}

	@Override
	public boolean delete(int[] id) {
		// TODO Auto-generated method stub
		return dt.delete(id);
	}

}
