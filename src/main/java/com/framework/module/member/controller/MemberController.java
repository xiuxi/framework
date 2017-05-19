package com.framework.module.member.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.framework.module.common.annotation.RequestJsonParam;
import com.framework.module.common.cache.RedisCache;
import com.framework.module.common.page.VPageRequest;
import com.framework.module.common.vo.VJson;
import com.framework.module.member.service.MemberService;
import com.framework.module.member.vo.VMemberCurrent;
import com.framework.module.member.vo.VMemberDetail;
import com.framework.module.member.vo.VMemberItem;
import com.framework.module.member.vo.VMemberQuery;
import com.framework.module.member.vo.VMemberRegist;
import com.framework.module.member.vo.VMemberSave;

/**
 * 用户 Controller
 * 
 * @author qq
 * 
 */
@RestController
@RequestMapping("/memberController")
public class MemberController {
	@Autowired
	private MemberService memberService;

	@Autowired
	private RedisCache redisCache;

	/**
	 * 是否登录
	 * 
	 * 用于检测是否登录
	 */
	@RequestMapping("/isLogin")
	@RequiresAuthentication
	public void isLogin() {
	}

	/**
	 * 获取当前登录用户信息
	 * 
	 * @return
	 */
	public VJson currentMember() {
		VMemberCurrent currentMember = memberService.currentMember();
		return new VJson(currentMember);
	}

	/**
	 * 注册
	 * 
	 * @param vMemberRegist
	 */
	@RequestMapping("/regist")
	public void regist(@RequestJsonParam("vMemberRegist") VMemberRegist vMemberRegist) {
		memberService.regist(vMemberRegist);
	}

	/**
	 * 修改用户
	 * 
	 * @param vMemberSave
	 */
	@RequestMapping("/updateMember")
	@RequiresAuthentication
	public void updateMember(@RequestJsonParam("vMemberSave") VMemberSave vMemberSave) {
		vMemberSave.setId("9c8823f62e5448488f940ff126e028d9");
		vMemberSave.setName("李四");
		memberService.updateMember(vMemberSave);
	}

	/**
	 * 登录
	 * 
	 * @param mobile
	 *            手机号
	 * 
	 * @param password
	 *            密码
	 * 
	 * @return token
	 */
	@RequestMapping("/login")
	public VJson login(@RequestJsonParam("mobile") String mobile, @RequestJsonParam("password") String password) {
		String jwt = memberService.login(mobile, password);
		return new VJson(jwt);
	}

	/**
	 * 退出
	 */
	@RequestMapping("/logout")
	public void logout() {
		memberService.logout();
	}

	/**
	 * 根据手机号查找用户
	 * 
	 * @param mobile
	 *            手机号
	 * @return
	 */
	@RequestMapping("/findByMobile")
	@RequiresAuthentication
	public VJson findByMobile(@RequestJsonParam("mobile") String mobile) {
		VMemberDetail datas = memberService.findByMobile(mobile);
		return new VJson(datas);
	}

	/**
	 * 分页查找用户
	 * 
	 * @param query
	 *            查询条件
	 * @param page
	 *            分页查询请求
	 * @return
	 */
	@RequestMapping("/findMembersPage")
	@RequiresAuthentication
	public VJson findMembersPage(@RequestJsonParam("query") VMemberQuery query,
			@RequestJsonParam("page") VPageRequest page) {
		Page<VMemberItem> datas = memberService.findMembersPage(query, page);
		return new VJson(datas);
	}
}