package com.framework.module.common.designPattern.proxy.dynamicProxy;

/**
 * 动态代理 测试
 * 
 * 实现AOP
 * 
 * @author qq
 *
 */
public class Test {
	public static void main(String[] args) {
		LogHandler logHandler = new LogHandler();
		UserManager userManager = (UserManager) logHandler.newProxyInstance(new UserManagerImpl());
		userManager.findUser("0001");
	}
}
