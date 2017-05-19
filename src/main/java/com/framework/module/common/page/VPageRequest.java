package com.framework.module.common.page;

import java.io.Serializable;

/**
 * 分页请求 VO
 * 
 * @author qq
 *
 */
public class VPageRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3170209081848955287L;

	/**
	 * 页码(0为第一页)
	 */
	private int pageNum = 0;

	/**
	 * 每页显示条数
	 */
	private int pageSize = 15;

	/*
	 * constructor
	 */
	public VPageRequest() {
		super();
	}

	public VPageRequest(int pageNum, int pageSize) {
		super();
		this.pageNum = pageNum;
		this.pageSize = pageSize;
	}

	/*
	 * getter、setter
	 */
	public int getPageSize() {
		return pageSize <= 0 ? 15 : pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum < 0 ? 0 : pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

}
