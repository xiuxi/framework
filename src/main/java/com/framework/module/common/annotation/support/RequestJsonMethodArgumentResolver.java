package com.framework.module.common.annotation.support;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.framework.module.common.annotation.RequestJsonParam;
import com.framework.module.common.utils.JacksonUtils;

/**
 * 解析@RequestJsonParam
 * 
 * @author qq
 *
 */
public class RequestJsonMethodArgumentResolver implements HandlerMethodArgumentResolver {
	/**
	 * 将请求参数放到Request中
	 * 
	 */
	private static final String RequestJsonAtrribute = "RequestJsonAtrribute";

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(RequestJsonParam.class);
	}

	/**
	 * 每个@RequestJsonParam执行一次该方法
	 * 
	 * 返回对象名=@RequestJsonParam.value的对象
	 */
	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);

		Object result = null;

		/*
		 * 从Request中读取请求参数
		 */
		String requestJson = (String) webRequest.getAttribute(RequestJsonAtrribute, NativeWebRequest.SCOPE_REQUEST);
		if (StringUtils.isBlank(requestJson)) {
			try {
				requestJson = IOUtils.toString(servletRequest.getInputStream());
				webRequest.setAttribute(RequestJsonAtrribute, requestJson, NativeWebRequest.SCOPE_REQUEST);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		/*
		 * 获取参数指定的对象
		 */
		String parameterName = parameter.getParameterAnnotation(RequestJsonParam.class).value(); // @RequestJsonParam.value
		if (StringUtils.isBlank(parameterName)) {
			parameterName = parameter.getParameterName(); // Controller参数名
		}

		if (StringUtils.isNotBlank(requestJson)) {
			result = JacksonUtils.getSubObj(requestJson, parameterName, parameter.getParameterType());
		}

		/*
		 * required处理
		 */
		boolean required = parameter.getParameterAnnotation(RequestJsonParam.class).required(); // @RequestJsonParam.required
		if (required && result == null) {
			throw new IllegalArgumentException(String.format("parameter %s can not be null!", parameterName));
		}

		return result;
	}

}