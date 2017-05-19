package com.framework.module.common.security.swt;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.framework.module.common.security.ShiroService;
import com.framework.module.common.security.ShiroUser;

/**
 * SWT(Simple Web Token)Realm
 * 
 * SWTRealm不验证凭证是否正确,让业务代码去认证,只提供保存ShiroUser信息
 * 
 * @author qq
 *
 */
public class SWTRealm extends AuthorizingRealm {
	/**
	 * ShiroService
	 */
	private ShiroService shiroService;

	/**
	 * 是否支持认证
	 * 
	 * @param token
	 *            前端提交的认证token
	 * 
	 * @return true:调用this.doGetAuthenticationInfo方法
	 */
	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof SWTAuthenticationToken;
	}

	/**
	 * 认证(登录的时候调用)
	 * 
	 * @param token
	 *            前端提交的认证token
	 * 
	 * @return
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		ShiroUser shiroUser = shiroService.buildShiroUser(token);
		if (shiroUser != null) {
			// principal:当前登录用户,
			// credentials:当前登录用户真正的凭证,与前端提交的认证token中的credentials比较,不相等则抛出IncorrectCredentialsException凭证不正确异常(这里不作验证,验证在业务代码中处理,否则当出现多个Realm,抛出的异常是最后一个Realm的异常信息)
			// realmName
			return new SimpleAuthenticationInfo(shiroUser, token.getCredentials(), shiroUser.getUserName());
		} else {
			throw new UnknownAccountException("Could not authenticate with given credentials");
		}
	}

	/**
	 * 授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		ShiroUser shiroUser = (ShiroUser) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// info.addStringPermissions(shiroService.findPermissions(shiroUser));
		// info.addRoles(shiroService.findRoles(shiroUser));
		return info;
	}

	/*
	 * getter、setter
	 */
	public ShiroService getShiroService() {
		return shiroService;
	}

	public void setShiroService(ShiroService shiroService) {
		this.shiroService = shiroService;
	}

}
