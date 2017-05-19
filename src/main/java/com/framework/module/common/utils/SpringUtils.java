package com.framework.module.common.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.util.Assert;

/**
 * Spring 工具类
 * 
 * 要使用该工具类,得先将该对象注入Spring Ioc容器
 * 
 * @author qq
 * 
 */
public class SpringUtils implements ApplicationListener<ContextRefreshedEvent> {
	private static ApplicationContext context;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		this.context = event.getApplicationContext();
	}

	/**
	 * 根据Bean的Class获取实例对象
	 * 
	 * @param beanClass
	 *            实现类的Class
	 * 
	 * @return
	 */
	public static <T> T getBean(Class<T> beanClass) {
		Assert.notNull(context, "The spring applicationContext was not initialized");

		T result = null;
		if (beanClass != null) {
			result = context.getBean(beanClass);
		}
		return result;
	}

	/**
	 * 根据Bean的ID获取实例对象
	 * 
	 * @param beanId
	 * 
	 * @return bean实例对象
	 */
	public static Object getBean(String beanId) {
		Assert.notNull(context, "The spring applicationContext was not initialized");
		Assert.hasText(beanId, "The parameter beanId can't be null!");

		return context.getBean(beanId);
	}

	/**
	 * 根据Bean的Class和Bean的ID获取实例对象
	 * 
	 * @param beanClass
	 *            实现类的Class
	 * 
	 * @param beanId
	 * 
	 * @return
	 */
	public static <T> T getBean(Class<T> beanClass, String beanId) {
		Assert.notNull(context, "The spring applicationContext was not initialized");
		Assert.hasText(beanId, "The parameter beanId can't be null!");

		T result = null;
		if (beanClass != null) {
			result = context.getBean(beanId, beanClass);
		}
		return result;
	}

}
