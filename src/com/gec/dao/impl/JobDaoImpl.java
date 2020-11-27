package com.gec.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gec.bean.Job;
import com.gec.bean.PageBean;
import com.gec.bean.User;
import com.gec.dao.JobDao;
import com.gec.util.DB;

public class JobDaoImpl extends DB<Job> implements JobDao {

	@Override
	public List<Job> findAll() {
		// TODO Auto-generated method stub
		return query("select * from job_inf");
	}

	@Override
	public Job findById(int id) {
		// TODO Auto-generated method stub
		return query("select * from job_inf where id=? ", id).get(0);
	}

	@Override
	public PageBean<Job> findPage(int pageNow, Job entity) {
		PageBean<Job> pb = new PageBean<>();
		//创建一个集合，保存需要使用的属性
		List<Object>obj=new ArrayList<Object>();
		pb.setPageNow(pageNow);
		//得到用记录数
		String sql = "select count(id) from job_inf where 1=1 ";
		String selectSql="select * from job_inf where 1=1 ";
		if(entity.getName()!=null) {
			sql+=" and name like ? ";
			selectSql+=" and name like ? ";
			obj.add("%"+entity.getName()+"%");
		}
		
		
		
		pb.setRowCount(getFunction(sql,obj.toArray()));
		selectSql+=" limit ?,? ";
		obj.add((pageNow-1)*pb.getPageSize());
		obj.add(pb.getPageSize());
		System.out.println(sql);
		System.out.println(selectSql);
		
		List<Job> list = query(selectSql, obj.toArray());	
		
		pb.setList(list);
		return pb;
	}

	@Override
	public boolean save(Job entity) {
		// TODO Auto-generated method stub
		return update("insert into job_inf values (null,?,?)", entity.getName(),entity.getRemark());
	}

	@Override
	public boolean update(Job entity) {
List<Object>obj=new ArrayList<Object>();
		
		//得到用记录数
		
		String Sql="update job_inf set ";
		if(entity.getName()!=null) {
			
			Sql+=" name = ? ";
			obj.add(entity.getName());
		}
		if(entity.getRemark()!=null) {
			if(entity.getName()==null) {
				Sql+="  remark = ? ";
			}else {
				Sql+=" , remark = ? ";
			}
			obj.add(entity.getRemark());
		}
		
		
		Sql+=" where id= ?";
		obj.add(entity.getId());

//	System.out.println(selectSql);
		System.out.println(Sql);
		
	 boolean update = update(Sql, obj.toArray());
	 return update;
	}


	@Override
	public boolean delete(int[] id) {
//		List<Object>list=new ArrayList<Object>();
//		String sql=" 1=1 ";
		for(int i=0;i<id.length;i++) {
//			sql+=" and id=? ";
//			list.add(id[i]);
			update("delete from job_inf where id=?",id[i]);
			
		}
//		System.out.println(sql);
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Job getEntity(ResultSet rs) throws Exception {
		Job jb=new Job();
		jb.setId(rs.getInt(1));
		jb.setName(rs.getString(2));
		jb.setRemark(rs.getString(3));
		
		return jb;
	}

}
