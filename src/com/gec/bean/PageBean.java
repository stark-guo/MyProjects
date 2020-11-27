package com.gec.bean;

import java.util.List;

public class PageBean<T> {

	private int pageSize = 3; // 页面大小
	private int pageNow; // 当前页
	private int rowCount; // 数据总记录数
	private int pageCount; // 总页数
	private List<T> list; // 存储当前页所需数据

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		setPageCount(); // 当页面大小和总记录数有变化时,都会影响总页数
	}

	public int getPageNow() {
		return pageNow;
	}

	public void setPageNow(int pageNow) {
		this.pageNow = pageNow;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
		setPageCount();
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount() {
		this.pageCount = rowCount % pageSize == 0 ? rowCount / pageSize : rowCount / pageSize + 1;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
}
