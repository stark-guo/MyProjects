package com.gec.dao.impl;

import java.sql.ResultSet;
import java.util.List;

import com.gec.bean.PageBean;
import com.gec.bean.Type;
import com.gec.bean.User;
import com.gec.dao.TypeDao;
import com.gec.util.DB;

public class TypeDaoImpl extends DB<Type> implements TypeDao {

	@Override
	public List<Type> findAll() {
		// TODO Auto-generated method stub
		return query("select * from type_inf");
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

	@Override
	public Type getEntity(ResultSet rs) throws Exception {
		Type ty=new Type();
		ty.setId(rs.getInt(1));
		ty.setName(rs.getString(2));
		ty.setCreateDate(rs.getDate(3));
		ty.setUser(new User(rs.getInt(5)));
		ty.setModifyDate(rs.getDate(6));
		return ty;
	}

}
