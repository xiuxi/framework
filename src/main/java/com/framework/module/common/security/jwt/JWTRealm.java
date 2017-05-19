package com.framework.module.common.security.jwt;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.framework.module.common.security.ShiroUser;
import com.framework.module.common.utils.JacksonUtils;

/**
 * JWT(JSON Web Token)Realm
 * 
 * @author qq
 * 
 */
public class JWTRealm extends AuthorizingRealm {
	private final static Logger logger = LoggerFactory.getLogger(JWTRealm.class);

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
		return token instanceof JWTAuthenticationToken;
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
		JWTAuthenticationToken authenticationToken = (JWTAuthenticationToken) token;

		if (StringUtils.isNotBlank(authenticationToken.getUserName()) && StringUtils.isNotBlank(authenticationToken.getToken())) {
			try {
				JwtClaims jwtClaims = JWTUtils.decryptJWT(authenticationToken.getToken());
				ShiroUser shiroUser = JacksonUtils.jsonToObj(jwtClaims.getSubject(), ShiroUser.class);

				// principal:当前登录用户,
				// credentials:当前登录用户真正的凭证,与前端提交的认证token中的credentials比较,不相等则抛出IncorrectCredentialsException凭证不正确异常
				// realmName
				return new SimpleAuthenticationInfo(shiroUser, token.getCredentials(), shiroUser.getUserName());
			} catch (InvalidJwtException | MalformedClaimException e) {
				logger.error("The token is invalid", e);
				return null;
			}
		}
		return null;
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

}
