package com.jing.web.util.database;

import java.util.List;

public class Page<T> {

	public static final String ASC = "asc";
	public static final String DESC = "desc";

	private long page = 1;
	private long pageSize = 10;
	private long totalPage;
	private long totalCount;
	private String orderBy;
	private String sort = ASC;
	private List<T> list;

	public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(long totalPage) {
		this.totalPage = totalPage;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		long tp = totalCount / pageSize;
		long y = totalCount % pageSize;
		if (0 != y) {
			tp++;
		}
		setTotalPage(tp);
		this.totalCount = totalCount;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
