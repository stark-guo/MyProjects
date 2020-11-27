package com.gec.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gec.bean.Document;
import com.gec.bean.Notice;
import com.gec.bean.PageBean;
import com.gec.bean.Type;
import com.gec.bean.User;
import com.gec.dao.NoticeDao;
import com.gec.util.DB;

public class NoticeDapImpl extends DB<Notice> implements NoticeDao {

	@Override
	public List<Notice> findAll() {
		// TODO Auto-generated method stub
		return query("select * from notice_inf");
	}

	@Override
	public Notice findById(int id) {
		// TODO Auto-generated method stub
		return query("select * from notice_inf where id=?", id).get(0);
	}

	@Override
	public PageBean<Notice> findPage(int pageNow, Notice entity) {
		PageBean<Notice> pb = new PageBean<>();
		//创建一个集合，保存需要使用的属性
		List<Object>obj=new ArrayList<Object>();
		pb.setPageNow(pageNow);
		//得到用记录数
		String sql = "select count(id) from notice_inf where 1=1 ";
		String selectSql="select * from notice_inf where 1=1 ";
		
		if(entity.getName()!=null) {
			sql +=" and Name like ? ";
			selectSql+=" and Name like ? ";
			obj.add("%"+entity.getName()+"%");
		}
		
		
		pb.setRowCount(getFunction(sql,obj.toArray()));
		selectSql+=" limit ?,? ";
		obj.add((pageNow-1)*pb.getPageSize());
		obj.add(pb.getPageSize());
		System.out.println(sql);
		System.out.println(selectSql);
		
		List<Notice> list = query(selectSql, obj.toArray());
	
		
		
		
		pb.setList(list);
		return pb;
	}

	@Override
	public boolean save(Notice en) {
		List<Object>list=new ArrayList<Object>();
		list.add(en.getName());
		list.add(en.getCreateDate());
		list.add(en.getType().getId());
		list.add(en.getContent());
		list.add(en.getUser().getId());
		return update("insert into notice_inf values(null,?,?,?,?,?,null)", list.toArray());
	}

	@Override
	public boolean update(Notice en) {
		List<Object>list=new ArrayList<Object>();
		list.add(en.getName());
		list.add(en.getType().getId());
		list.add(en.getContent());
		list.add(en.getModifyDate());
		list.add(en.getId());
		System.out.println(en.getId());
		return update("update notice_inf set name=?,type_id=?,content=?,modify_date=? where id=?", list.toArray());
	}

	@Override
	public boolean delete(int[] id) {
		for (int i = 0; i < id.length; i++) {
			update("delete from notice_inf where id=? ", id[i]);
		}
		return true;
	}

	@Override
	public Notice getEntity(ResultSet rs) throws Exception {
		Notice nt=new Notice();
		nt.setId(rs.getInt(1));
		nt.setName(rs.getString(2));
		nt.setCreateDate(rs.getDate(3));
		nt.setType(new Type(rs.getInt(4)));
		nt.setContent(rs.getString(5));
		nt.setUser(new User(rs.getInt(6)));
		nt.setModifyDate(rs.getDate(7));
		
		return nt;
	}

}
