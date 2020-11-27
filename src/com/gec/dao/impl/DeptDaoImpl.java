package com.gec.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gec.bean.Dept;
import com.gec.bean.PageBean;
import com.gec.bean.User;
import com.gec.dao.DeptDao;
import com.gec.util.DB;

public class DeptDaoImpl extends DB<Dept> implements DeptDao {

	@Override
	public List<Dept> findAll() {
		// TODO Auto-generated method stub
		return query("select * from dept_inf");
	}

	@Override
	public Dept findById(int id) {
		List<Dept> query = query("select * from dept_inf where id= ? ", id);
		if(query!=null) {
			return query.get(0);
		}
		return null;
	}

	@Override
	public PageBean<Dept> findPage(int pageNow, Dept dept) {
		PageBean<Dept> pb = new PageBean<>();
		//创建一个集合，保存需要使用的属性
		List<Object>obj=new ArrayList<Object>();
		pb.setPageNow(pageNow);
		//得到用记录数
		String sql = "select count(id) from dept_inf where 1=1 ";
		String selectSql="select * from dept_inf where 1=1 ";
		
		if(dept.getName()!=null) {
			sql +=" and name like ? ";
			selectSql+=" and name like ? ";
			obj.add("%"+dept.getName()+"%");
		}
		
		
		pb.setRowCount(getFunction(sql,obj.toArray()));
		selectSql+=" limit ?,? ";
		obj.add((pageNow-1)*pb.getPageSize());
		obj.add(pb.getPageSize());
		System.out.println(sql);
		System.out.println(selectSql);
		
		List<Dept> list = query(selectSql, obj.toArray());
	
		
		
		
		pb.setList(list);
		return pb;
	}

	@Override
	public boolean save(Dept entity) {
		
		return update("insert into dept_inf values(null,?,?)", entity.getName(),entity.getRemark());
	}

	@Override
	public boolean update(Dept entity) {
List<Object>obj=new ArrayList<Object>();
		
		//得到用记录数
		
		String selectSql="update dept_inf set ";
		if(entity.getName()!=null) {
			
			selectSql+=" name = ? ";
			obj.add(entity.getName());
		}
		if(entity.getRemark()!=null) {
			if(entity.getName()==null) {
				selectSql+="  remark = ? ";
			}else {
				selectSql+=" , remark = ? ";
			}
			obj.add(entity.getRemark());
		}
		
		
		selectSql+=" where id= ?";
		obj.add(entity.getId());

	
		System.out.println(selectSql);
		
	 boolean update = update(selectSql, obj.toArray());
		
		
		
		
		
		return update;
	}

	@Override
	public boolean delete(int[] id) {
//		List<Object>list=new ArrayList<Object>();
//		String sql=" ";
		for(int i=0;i<id.length;i++) {
			update("delete from dept_inf where id=?",id[i]);
//			sql+=" and id=? ";
//			list.add(id[i]);
			
		}
//		System.out.println(sql);
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Dept getEntity(ResultSet rs) throws Exception {
		Dept dt=new Dept();
		dt.setId(rs.getInt(1));
		dt.setName(rs.getString(2));
		dt.setRemark(rs.getString(3));
		
		return dt;
	}

}
