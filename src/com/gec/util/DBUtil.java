package com.gec.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class DBUtil<T> {

	private Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	
	private Connection getConn() throws Exception{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/hrm?characterEncoding=utf-8", "root","");
		return conn;
	}
	//统一更新方法
	public boolean update(String sql,Object...obj){
		try {
			pst = getConn().prepareStatement(sql);
			for (int i = 0; i < obj.length; i++) {
				pst.setObject(i+1, obj[i]);				
			}
			int num = pst.executeUpdate();
			if(num>0)
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			getClose(conn, pst, rs);
		}
		return false;
	}
	//统一查询方法
	public List<T> query(String sql,Object...obj){
		List<T> list = new ArrayList<>();
		try {
			pst = getConn().prepareStatement(sql);
			for (int i = 0; i < obj.length; i++) {
				pst.setObject(i+1, obj[i]);				
			}
			rs = pst.executeQuery();
			while(rs.next()){
				T t = getEntity(rs);
				list.add(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			getClose(conn, pst, rs);
		}
		return list;
	}
	//抽象方法,一定让子类重写,规定其rs获取的具体类型
	public abstract T getEntity(ResultSet rs) throws Exception;
	
	//统一组函数查询方法
	public int getFunction(String sql,Object...obj){
		int num = 0;
		try {
			pst = getConn().prepareStatement(sql);
			for (int i = 0; i < obj.length; i++) {
				pst.setObject(i+1, obj[i]);				
			}
			rs = pst.executeQuery();
			if(rs.next()){
				num = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			getClose(conn, pst, rs);
		}
		return num;
	}
	//统一关闭操作
	private void getClose(Connection conn,PreparedStatement pst,ResultSet rs){
		try {
			if(rs!=null)
				rs.close();
			if(pst!=null)
				pst.close();
			if(conn!=null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
