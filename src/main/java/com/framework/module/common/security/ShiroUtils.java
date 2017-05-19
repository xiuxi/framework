package com.framework.module.common.security;

import java.util.Set;

import org.apache.shiro.SecurityUtils;

/**
 * Shiro 工具类
 * 
 * @author qq
 * 
 */
public class ShiroUtils {
	/**
	 * 获取当前登陆用户
	 * 
	 * @return
	 */
	public static ShiroUser currentUser() {
		try {
			ShiroUser shiroUser = (ShiroUser) SecurityUtils.getSubject().getPrincipals().getPrimaryPrincipal();
			return shiroUser;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 检查是否拥有某些角色
	 * 
	 * @param roles
	 * @return
	 */
	public static boolean hasRoles(Set<String> roles) {
		try {
			return SecurityUtils.getSubject().hasAllRoles(roles);
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 检查是否拥有某些权限
	 * 
	 * @param permissions
	 * @return
	 */
	public static boolean hasPermissions(String... permissions) {
		try {
			return SecurityUtils.getSubject().isPermittedAll(permissions);
		} catch (Exception e) {
			return false;
		}
	}
}
