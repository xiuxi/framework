package com.framework.module.common.page;

import java.io.Serializable;
import java.util.List;

/**
 * 分页对象
 * 
 * 用于封装分页数据
 * 
 * @author qq
 *
 * @param <T>
 */
public class Page<T> implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5708479550427139640L;

	/**
	 * 
	 */
	private List<T> content;

	/**
	 * 页码(0为第一页)
	 */
	private int pageNum;

	/**
	 * 每页显示条数
	 */
	private int pageSize;

	/**
	 * 总条数
	 */
	private long totalElements;

	/**
	 * 总页数
	 */
	private int totalPages;

	/*
	 * constructor
	 */
	private Page() {
	}

	/**
	 * 用于构建返回的分页数据
	 * 
	 * @param content
	 * @param pageNum
	 * @param pageSize
	 * @param totalElemetns
	 */
	public Page(List<T> content, int pageNum, int pageSize, long totalElemetns) {
		this.pageSize = (pageSize <= 0) ? 15 : pageSize;
		this.totalElements = totalElemetns < 0 ? 0 : totalElemetns;
		/*
		 * 总条数(M);每页显示条数(N);显示第几页(P)
		 * 
		 * 总页数 = (M % N) == 0 ? (M / N) : (M / N + 1);
		 * 
		 * 第P页的数据: P * N 到 P * N + N - 1的数据(0为第一页)
		 * 
		 * 第P页的数据: (P-1) * N 到 ((P-1) * N) + N - 1的数据(1为第一页)
		 */
		this.totalPages = (int) ((this.totalElements % this.pageSize) == 0 ? (this.totalElements / this.pageSize)
				: (this.totalElements / this.pageSize + 1));

		this.pageNum = (pageNum < 0) ? 0 : pageNum;

		this.content = content;
	}

	/*
	 * getter
	 */
	public List<T> getContent() {
		return content;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getPageNum() {
		return pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

}
