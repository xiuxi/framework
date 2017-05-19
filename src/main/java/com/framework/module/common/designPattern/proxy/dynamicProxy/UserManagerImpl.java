package com.framework.module.common.designPattern.proxy.dynamicProxy;

/**
 * 真实主题（用户管理真正的实现类）
 * 
 * @author qq
 *
 */
public class UserManagerImpl implements UserManager {
	@Override
	public void addUser(String userId, String userName) {
		System.out.println(String.format("addUser: userId=%s,userName=%s", userId, userName));
	}

	@Override
	public void delUser(String userId) {
		System.out.println(String.format("delUser: userId=%s", userId));
	}

	@Override
	public void modifyUser(String userId, String userName) {
		System.out.println(String.format("modifyUser: userId=%s,userName=%s", userId, userName));
	}

	@Override
	public String findUser(String userId) {
		System.out.println(String.format("findUser: userId=%s", userId));
		return userId;
	}

}
