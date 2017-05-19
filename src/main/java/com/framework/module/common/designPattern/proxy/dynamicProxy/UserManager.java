package com.framework.module.common.designPattern.proxy.dynamicProxy;

/**
 * 主题接口（用户控制接口)
 * 
 * @author qq
 *
 */
public interface UserManager {
	public void addUser(String userId, String userName);

	public void delUser(String userId);

	public void modifyUser(String userId, String userName);

	public String findUser(String userId);
}
