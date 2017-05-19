package com.framework.module.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 测试 Filter
 * 
 * @author qq
 *
 */
public class TestFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("请求前处理动作");
		chain.doFilter(request, response);
		System.out.println("响应后处理动作");
	}

	@Override
	public void destroy() {

	}
}
