package com.gec.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

public abstract class DB<T> {
	private Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	private static DataSource ds;
	static {
		Properties pro=new Properties();
		
		try(InputStream in = DB.class.getClassLoader().getResourceAsStream("/db.properties")) {
			pro.load(in);
			ds=DruidDataSourceFactory.createDataSource(pro);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private Connection getConn() throws Exception{
		conn=ds.getConnection();
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
