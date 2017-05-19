package com.framework.module.member.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.jose4j.lang.JoseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.framework.module.common.page.VPageRequest;
import com.framework.module.common.security.ShiroUtils;
import com.framework.module.common.security.jwt.JWTUtils;
import com.framework.module.common.security.swt.SWTAuthenticationToken;
import com.framework.module.common.utils.BeanUtils;
import com.framework.module.common.utils.JacksonUtils;
import com.framework.module.common.utils.PageUtils;
import com.framework.module.common.utils.PasswordUtils;
import com.framework.module.member.dao.member.MemberRepository;
import com.framework.module.member.schema.TMember;
import com.framework.module.member.vo.VMemberCurrent;
import com.framework.module.member.vo.VMemberDetail;
import com.framework.module.member.vo.VMemberItem;
import com.framework.module.member.vo.VMemberQuery;
import com.framework.module.member.vo.VMemberRegist;
import com.framework.module.member.vo.VMemberSave;

/**
 * 用户 Service
 * 
 * @author qq
 * 
 */
@Service
@Transactional
public class MemberService {
	private final static Logger logger = LoggerFactory.getLogger(MemberService.class);

	@Autowired
	private MemberRepository memberRepository;

	/**
	 * 获取当前登录用户手机号
	 * 
	 * @return
	 */
	public String currentMobile() {
		return (ShiroUtils.currentUser() == null) ? null : ShiroUtils.currentUser().getUserName();
	}

	/**
	 * 获取当前登录用户信息
	 * 
	 * @return
	 */
	public VMemberCurrent currentMember() {
		VMemberCurrent response = null;

		String mobile = this.currentMobile();
		if (StringUtils.isNotBlank(mobile)) {
			response = new VMemberCurrent();

			TMember tMember = memberRepository.findByMobile(mobile);
			BeanUtils.from(tMember).to(response);
		}
		return response;
	}

	/**
	 * 注册
	 * 
	 * @param vMemberRegist
	 */
	public void regist(VMemberRegist vMemberRegist) {
		Assert.notNull(vMemberRegist, "参数不能为空");
		Assert.hasText(vMemberRegist.getMobile(), "手机号不能为空");
		Assert.hasText(vMemberRegist.getPassword(), "密码不能为空");

		TMember tMember = memberRepository.findByMobile(vMemberRegist.getMobile());
		Assert.isNull(tMember, "该用户已注册");

		TMember member = new TMember();
		BeanUtils.from(vMemberRegist).excludeNull().to(member);
		member.setPassword(PasswordUtils.md5Encrypt(vMemberRegist.getPassword())); // 加密

		memberRepository.save(member);
		logger.info("注册成功");
	}

	/**
	 * 修改用户
	 * 
	 * @param vMemberSave
	 */
	public void updateMember(VMemberSave vMemberSave) {
		TMember member = memberRepository.findOne(vMemberSave.getId());
		BeanUtils.from(vMemberSave).excludeNull().to(member);
		memberRepository.save(member);
		logger.info("修改成功");
	}

	/**
	 * 登陆
	 * 
	 * @param mobile
	 *            手机号
	 * 
	 * @param password
	 *            密码
	 * 
	 * @return token
	 */
	public String login(String mobile, String password) {
		Assert.hasText(mobile, "手机号不能为空");
		Assert.hasText(password, "密码不能为空");

		TMember member = memberRepository.findByMobile(mobile);
		Assert.notNull(member, String.format("用户: %s未注册", mobile));
		Assert.isTrue(member.getPassword().equals(PasswordUtils.md5Encrypt(password)), "密码错误");

		SWTAuthenticationToken swtAuthenticationToken = new SWTAuthenticationToken(mobile,
				PasswordUtils.md5Encrypt(password));
		try {
			SecurityUtils.getSubject().login(swtAuthenticationToken);
		} catch (AuthenticationException e) {
			logger.info("登录失败", e);
			throw new IllegalArgumentException("登录失败");
		}

		logger.info("登录成功");

		// 回写JWT
		try {
			String jwt = JWTUtils.encryptJWT(JacksonUtils.objToJson(ShiroUtils.currentUser()));
			return jwt;
		} catch (JoseException e) {
			logger.info("登录失败", e);
			throw new IllegalArgumentException("登录失败");
		}
	}

	/**
	 * 退出
	 */
	public void logout() {
		SecurityUtils.getSubject().logout();
		logger.info("退出成功");
	}

	/**
	 * 根据手机号查找用户
	 * 
	 * @param mobile
	 *            手机号
	 * @return
	 */
	public VMemberDetail findByMobile(String mobile) {
		Assert.hasText(mobile, "手机号不能为空");

		VMemberDetail response = null;
		TMember member = memberRepository.findByMobile(mobile);

		if (member != null) {
			response = new VMemberDetail();
			BeanUtils.from(member).to(response);
			// set genderValue
			response.setGenderValue(member.getGender().getValue());
		}

		return response;
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
	public Page<VMemberItem> findMembersPage(VMemberQuery query, VPageRequest page) {
		Page<TMember> pages = memberRepository.findMembersPage(query, page);

		Page<VMemberItem> response = PageUtils.convert(pages, new Converter<TMember, VMemberItem>() {
			@Override
			public VMemberItem convert(TMember source) {
				VMemberItem target = new VMemberItem();
				BeanUtils.from(source).to(target);
				// set genderValue
				target.setGenderValue(source.getGender().getValue());
				return target;
			}
		});

		return response;
	}

}