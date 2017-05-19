package com.framework.module.chat.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.framework.module.chat.vo.VWebIMToken;
import com.framework.module.common.plugins.emchat.EmChatApi;
import com.framework.module.common.plugins.emchat.constant.ContentType;
import com.framework.module.common.plugins.emchat.exception.UserAlreadyRegistException;
import com.framework.module.common.plugins.emchat.exception.UserUnRegistException;
import com.framework.module.common.plugins.emchat.vo.VExt;
import com.framework.module.member.service.MemberService;

/**
 * 聊天 Service
 * 
 * @author qq
 * 
 */
@Service
@Transactional
public class ChatService {
	private final static Logger logger = LoggerFactory.getLogger(ChatService.class);

	@Autowired
	private MemberService memberService;

	/**
	 * 获取webIM token(用于前端JS使用token登录)
	 * 
	 * @return VWebIMToken
	 * @throws UserUnRegistException
	 *             用户未注册
	 */
	public VWebIMToken getWebIMToken() {
		String userName = memberService.currentMobile();// 当前登录用户
		try {
			boolean isOnLine = EmChatApi.isOnLine(userName); // 环信是否在线(true：在线)
			String token = EmChatApi.getWebIMToken(userName);

			return new VWebIMToken(userName, token, isOnLine);
		} catch (UserUnRegistException e) {
			throw new IllegalArgumentException("该用户尚未在环信注册");
		} catch (Exception e) {
			throw new IllegalArgumentException("环信操作异常");
		}
	}

	/**
	 * 注册环信
	 * 
	 * @param userName
	 *            用户名
	 */
	public void regist(String userName) {
		try {
			EmChatApi.regist(userName);
		} catch (UserAlreadyRegistException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 发送消息
	 * 
	 * @param contentType
	 * @param receivers
	 */
	public void sendMessage(String msg, String receivers, ContentType contentType) {
		VExt ext = new VExt();
		// ext.setFrom("");
		ext.setContentType(contentType == null ? ContentType.TEXT_GENERAL : contentType);
		ext.setMsg(msg);

		EmChatApi.sendMessage(ext, receivers);
	}

}