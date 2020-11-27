package com.gec.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gec.bean.PageBean;
import com.gec.bean.User;
import com.gec.dao.UserDao;
import com.gec.util.DB;
import com.gec.util.DBUtil;

public class UserDaoImpl extends DB<User> implements UserDao {

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return query("select * from user_inf");
	}

	@Override
	public User findById(int id) {
		List<User> query = query("select * from user_inf where id= ? ", id);
		if(query!=null) {
			return query.get(0);
		}
		return null;
	}

	@Override
	public PageBean<User> findPage(int pageNow, User entity) {
		PageBean<User> pb = new PageBean<>();
		//创建一个集合，保存需要使用的属性
		List<Object>obj=new ArrayList<Object>();
		pb.setPageNow(pageNow);
		//得到用记录数
		String sql = "select count(id) from user_inf where 1=1 ";
		String selectSql="select * from user_inf where 1=1 ";
		if(entity.getLoginname()!=null) {
			sql+=" and loginname like ? ";
			selectSql+=" and loginname like ? ";
			obj.add("%"+entity.getLoginname()+"%");
		}
		if(entity.getUsername()!=null) {
			sql+=" and username like ? ";
			selectSql+=" and username like ? ";
			obj.add("%"+entity.getUsername()+"%");
		}
		if(entity.getStatus()!=null&&entity.getStatus()>0) {
			sql +=" and status like ? ";
			selectSql+=" and status like ? ";
			obj.add("%"+entity.getStatus()+"%");
		}
		
		
		pb.setRowCount(getFunction(sql,obj.toArray()));
		selectSql+=" limit ?,?";
		obj.add((pageNow-1)*pb.getPageSize());
		obj.add(pb.getPageSize());
		System.out.println(sql);
		System.out.println(selectSql);
		
		List<User> list = query(selectSql, obj.toArray());
	
		
		
		
		pb.setList(list);
		return pb;
	}


	@Override
	public boolean save(User entity) {	
		
		return update("insert into user_inf values (null,?,?,?,?,?)", entity.getLoginname(),entity.getPassword(),entity.getStatus(),entity.getCreatedate(),entity.getUsername());
	}

	@Override
	public boolean update(User entity) {
		List<Object>obj=new ArrayList<Object>();
		
		//得到用记录数
		
		String selectSql="update user_inf set ";
		if(entity.getLoginname()!=null) {
			
			selectSql+=" loginname = ? ";
			obj.add(entity.getLoginname());
		}
		if(entity.getUsername()!=null) {
			if(entity.getLoginname()==null) {
				selectSql+="  username = ? ";
			}else {
				selectSql+=" , username = ? ";
			}
		
			
			obj.add(entity.getUsername());
		}
		if(entity.getStatus()!=null&&entity.getStatus()>0) {
			if(entity.getLoginname()==null&&entity.getUsername()==null) {
				selectSql+="  status = ? ";
			}else {
				selectSql+=" , status = ? ";
			}
			
			obj.add(entity.getStatus());
		}if(entity.getPassword()!=null) {
			if(entity.getLoginname()==null&&entity.getUsername()==null&&entity.getStatus()==null) {
				selectSql+="  password = ? ";
			}else {
				selectSql+=" , password = ? ";
			}
			obj.add(entity.getPassword());
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
//		String sql=" 1=1 ";
		for(int i=0;i<id.length;i++) {
//			sql+=" and id=? ";
//			list.add(id[i]);
			update("delete from user_inf where id=?",id[i]);
		}
//		System.out.println(sql);
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public User login(String loginname, String password) {
		List<User> list = query("select * from user_inf where loginname=? and password=?", loginname,password);
		if(list.size()>0)
			return list.get(0);
		return null;
	}

	@Override
	public User getEntity(ResultSet rs) throws Exception {
		User user = new User();
		user.setId(rs.getInt(1));
		user.setLoginname(rs.getString(2));
		user.setPassword(rs.getString(3));
		user.setStatus(rs.getInt(4));
		user.setCreatedate(rs.getDate(5));
		user.setUsername(rs.getString(6));
		return user;
	}

}
