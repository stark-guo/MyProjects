package com.gec.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gec.bean.Dept;
import com.gec.bean.Document;
import com.gec.bean.PageBean;
import com.gec.bean.User;
import com.gec.dao.DocumentDao;
import com.gec.util.DB;

public class DocumentDaoImpl extends DB<Document> implements DocumentDao {

	@Override
	public List<Document> findAll() {
		// TODO Auto-generated method stub
		return query("select * from document_inf");
	}

	@Override
	public Document findById(int id) {
		// TODO Auto-generated method stub
		return query("select * from document_inf where id=?", id).get(0);
	}

	@Override
	public PageBean<Document> findPage(int pageNow, Document entity) {
		PageBean<Document> pb = new PageBean<>();
		//创建一个集合，保存需要使用的属性
		List<Object>obj=new ArrayList<Object>();
		pb.setPageNow(pageNow);
		//得到用记录数
		String sql = "select count(id) from Document_inf where 1=1 ";
		String selectSql="select * from Document_inf where 1=1 ";
		
		if(entity.getTitle()!=null) {
			sql +=" and title like ? ";
			selectSql+=" and title like ? ";
			obj.add("%"+entity.getTitle()+"%");
		}
		
		
		pb.setRowCount(getFunction(sql,obj.toArray()));
		selectSql+=" limit ?,? ";
		obj.add((pageNow-1)*pb.getPageSize());
		obj.add(pb.getPageSize());
		System.out.println(sql);
		System.out.println(selectSql);
		
		List<Document> list = query(selectSql, obj.toArray());
	
		
		
		
		pb.setList(list);
		return pb;
	}

	@Override
	public boolean save(Document en) {
		List<Object>list=new ArrayList<Object>();
		list.add(en.getTitle());
		list.add(en.getFileName());
		list.add(en.getFiletype());
		list.add(en.getFileBytes());
		list.add(en.getRemark());
		list.add(en.getCreateDate());
		list.add(en.getUser().getId());
		return update("insert into Document_inf values(null,?,?,?,?,?,?,?)", list.toArray());
	}

	@Override
	public boolean update(Document en) {
		List <Object>list=new ArrayList<Object>();
		String sql="update document_inf set title =?, filename=?,filetype=?,filebytes=?,remark=? where id=?";
		list.add(en.getTitle());
		if(en.getFileName()==null) {
			sql="update document_inf set title =?,remark=? where id=?";
		}else {
			list.add(en.getFileName());
			list.add(en.getFiletype());
			list.add(en.getFileBytes());
		}
	
		list.add(en.getRemark());
		list.add(en.getId());
		return update(sql, list.toArray());
	}

	@Override
	public boolean delete(int[] id) {
		
		for (int i = 0; i < id.length; i++) {
			update("delete from Document_inf where id= ?", id[i]);
		}
		return true;
	}

	@Override
	public Document getEntity(ResultSet rs) throws Exception {
		Document dt=new Document();
		dt.setId(rs.getInt(1));
		dt.setTitle(rs.getString(2));
		dt.setFileName(rs.getString(3));
		dt.setFiletype(rs.getString(4));
		dt.setFileBytes(rs.getString(5));
		dt.setRemark(rs.getString(6));
		dt.setCreateDate(rs.getDate(7));
		dt.setUser(new User(rs.getInt(8)));
		return dt;
	}

}
