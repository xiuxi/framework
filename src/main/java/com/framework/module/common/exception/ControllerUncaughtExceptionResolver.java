package com.framework.module.common.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.framework.module.common.utils.JacksonUtils;
import com.framework.module.common.vo.VJson;

/**
 * 
 * Controllr未捕获异常处理器(需在SpringMVC中配置)
 * 
 * 返回错误格式的Json对象,供前端解析
 * 
 * @author qq
 *
 */
public class ControllerUncaughtExceptionResolver implements HandlerExceptionResolver {
	private final static Logger logger = LoggerFactory.getLogger(ControllerUncaughtExceptionResolver.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		logger.debug("resolveException");
		ex.printStackTrace();

		ModelAndView emptyModelAndView = new ModelAndView();

		String errorType = ex.getClass().getName();
		String error = StringUtils.isBlank(ex.getMessage()) ? "系统异常" : ex.getMessage();

		response.setContentType("application:json;charset=UTF-8");

		try {
			PrintWriter pw = response.getWriter();
			pw.write(JacksonUtils.objToJson(new VJson(errorType, error)));
			pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		emptyModelAndView.clear();
		return emptyModelAndView;
	}

}
