package com.gec.dao.impl;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.gec.bean.Dept;
import com.gec.bean.Employee;
import com.gec.bean.Job;
import com.gec.bean.PageBean;
import com.gec.dao.EmployeeDao;
import com.gec.util.DB;

public class EmployeeDaoImpl extends DB<Employee> implements EmployeeDao {

	@Override
	public List<Employee> findAll() {
		// TODO Auto-generated method stub
		return query("select * from Employee_inf");
	}

	@Override
	public Employee findById(int id) {
		// TODO Auto-generated method stub
		return query("select * from Employee_inf where id =? ", id).get(0);
	}

	@Override
	public PageBean<Employee> findPage(int pageNow, Employee entity) {
		PageBean<Employee> pb = new PageBean<>();
		//创建一个集合，保存需要使用的属性
		List<Object>obj=new ArrayList<Object>();
		pb.setPageNow(pageNow);
		//得到用记录数
		String sql = "select  count(id) from Employee_inf where 1=1 ";
		String selectSql="select * from Employee_inf where 1=1 ";
		if(entity.getJob()!=null&&entity.getJob().getId()>0) {
			sql+=" and job_id = ? ";
			selectSql+=" and job_id = ? ";
			obj.add(entity.getJob().getId());
		}
		if(entity.getSex()!=null) {
			sql+=" and sex = ? ";
			selectSql+=" and sex = ? ";
			obj.add(entity.getSex());
		}
		if(entity.getName()!=null) {
			sql+=" and name like ? ";
			selectSql+=" and name like  ? ";
			obj.add("%"+entity.getName()+"%");
		}
		if(entity.getPhone()!=null) {
			sql+=" and phone like ? ";
			selectSql+=" and phone like ? ";
			obj.add("%"+entity.getPhone()+"%");
		}
		if(entity.getCardId()!=null) {
			sql+=" and card_id like ? ";
			selectSql+=" and card_id like ? ";
			obj.add("%"+entity.getCardId()+"%");
		}if(entity.getDept()!=null) {
			sql+=" and dept_id = ? ";
			selectSql+=" and dept_id = ? ";
			obj.add(entity.getDept().getId());
		}
		
		
		
		pb.setRowCount(getFunction(sql,obj.toArray()));
		selectSql+=" limit ?,? ";
		obj.add((pageNow-1)*pb.getPageSize());
		obj.add(pb.getPageSize());
		System.out.println(sql);
		System.out.println(selectSql);
		
		List<Employee> list = query(selectSql, obj.toArray());	
		
		pb.setList(list);
		return pb;
	}
	@Override
	public boolean save(Employee en) {
		List<Object> list=new ArrayList<Object>();
String sql="insert into Employee_inf values(null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
if(en.getDept().getId()==0) {
	sql="insert into Employee_inf values(null,null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
}else {
		list.add(en.getDept().getId());}
		System.out.println(en.getDept().getId());
		if(en.getJob().getId()==0) {
			sql="insert into Employee_inf values(null,?,null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		}else {
			list.add(en.getJob().getId());
		}
		if(en.getDept().getId()==0&&en.getJob().getId()==0) {
			sql="insert into Employee_inf values(null,null,null,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		}
		
		System.out.println(en.getJob().getId());
		list.add(en.getName());
		System.out.println(en.getName());
		list.add(en.getCardId()); 
		list.add(en.getAddress());
		list.add(en.getPostCode());
		list.add(en.getTel());
		list.add(en.getPhone());
		list.add(en.getQqNum());
		list.add(en.getEmail());
		list.add(en.getSex());
		list.add(en.getParty());
		list.add(en.getBirthday());
		list.add(en.getRace());
		list.add(en.getEducation());
		list.add(en.getSpeciality());
		list.add(en.getHobby());
		list.add(en.getRemark());
		list.add(en.getCreateDate());
//		list.add(en.getId());
		return update(sql, list.toArray());
	}

	@Override
	public boolean update(Employee en) {
		List<Object> list=new ArrayList<Object>();
		list.add(en.getName());
System.out.println(en.getName());
		list.add(en.getAddress());
		list.add(en.getBirthday());
		list.add(en.getCardId());
		list.add(en.getCreateDate());
		list.add(en.getDept().getId());
		list.add(en.getEducation());
		list.add(en.getEmail());
		list.add(en.getHobby());
		list.add(en.getJob().getId());
		list.add(en.getParty());
		list.add(en.getPhone());
		list.add(en.getPostCode());
		list.add(en.getQqNum());
		list.add(en.getRace());
		list.add(en.getRemark());
		list.add(en.getSex());
		list.add(en.getSpeciality());
		list.add(en.getTel());
		list.add(en.getId());
		return update("update Employee_inf set name = ? , address = ? , birthday =? , card_id = ? , create_date = ? , dept_id =? , Education = ? , email = ? , hobby = ? , job_id = ? , party = ? , phone = ? , post_code = ? , qq_num = ? , race = ? , remark = ? , sex = ? , Speciality = ? , tel = ? where id = ?", list.toArray());
	}

	@Override
	public boolean delete(int[] id) {
		for(int i=0;i<id.length;i++) {
		update("delete from Employee_inf where id=? ", id[i]);
		}
		return true;
	}

	@Override
	public Employee getEntity(ResultSet rs) throws Exception {
		Employee em=new Employee();
		em.setId(rs.getInt(1));
		em.setDept(new Dept(rs.getInt(2)));
		em.setJob(new Job(rs.getInt(3)));
		em.setName(rs.getString(4));
		em.setCardId(rs.getString(5));
		em.setAddress(rs.getString(6));
		em.setPostCode(rs.getString(7));
		em.setTel(rs.getString(8));
		em.setPhone(rs.getString(9));
		em.setQqNum(rs.getString(10));
		em.setEmail(rs.getString(11));
		em.setSex(rs.getInt(12));
		em.setParty(rs.getString(13));
		em.setBirthday(rs.getDate(14));
		em.setRace(rs.getString(15));
		em.setEducation(rs.getString(16));
		em.setSpeciality(rs.getString(17));
		em.setHobby(rs.getString(18));
		em.setRemark(rs.getString(19));
		em.setCreateDate(rs.getDate(20));
		return em;
	}

}
