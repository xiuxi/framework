package com.framework.module.common.designPattern.proxy.dynamicProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理类(用于生成代理类)
 * 
 * @author qq
 *
 */
public class LogHandler implements InvocationHandler {
	/**
	 * 需要被代理的对象(非接口)
	 */
	private Object target;

	public Object newProxyInstance(Object target) {
		this.target = target;
		// 创建、并返回一个动态代理
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
	}

	/**
	 * 执行动态代理对象的所有方法，都会替换成执行如下的invoke方法
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Object returnValue = null; // 方法返回值

		try {
			System.out.println("方法执行前执行.");
			// 调用目标方法,以target作为主调来执行method方法
			returnValue = method.invoke(target, args);
			System.out.println("方法执行后执行.");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error-->>" + method.getName());
			throw e;
		}
		return returnValue;
	}
}
