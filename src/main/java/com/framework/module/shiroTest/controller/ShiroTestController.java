package com.framework.module.shiroTest.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * shiro测试 Controller
 * 
 * @author qq
 * 
 */
@Controller
@RequestMapping("/ShiroTestController")
public class ShiroTestController {
	private final static Logger logger = LoggerFactory.getLogger(ShiroTestController.class);

	/**
	 * 登录测试
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/loginTest")
	public void loginTest() {
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.isAuthenticated()) {
			try {
				UsernamePasswordToken token = new UsernamePasswordToken("root", "secret");
				currentUser.login(token);

				logger.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");

				// Session session = currentUser.getSession();
				// session.setAttribute("", "");
			} catch (UnknownAccountException e) {
				logger.error("username wasn't in the system");
				// username wasn't in the system, show them an error message?
			} catch (IncorrectCredentialsException e) {
				logger.error("password didn't match, try again?");
				// password didn't match, try again?
			} catch (LockedAccountException e) {
				logger.error("account for that username is locked - can't login");
				// account for that username is locked - can't login. Show them
				// a message?
			} catch (AuthenticationException e) {
				logger.error("unexpected condition");
				// unexpected condition - error?
			}
		}
	}

	/**
	 * 获取当前登录用户测试
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/currentUserTest")
	public void currentUserTest() {
		Subject currentUser = SecurityUtils.getSubject();
		String user = null;
		if (currentUser != null && currentUser.getPrincipal() != null) {
			user = currentUser.getPrincipal().toString();
		}
		logger.info("currentUser is: {{}}", user);
	}

	/**
	 * 退出测试
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/logoutTest")
	public void logoutTest() {
		Subject currentUser = SecurityUtils.getSubject();

		currentUser.logout();
		logger.info("User logout in successfully.");
	}

}