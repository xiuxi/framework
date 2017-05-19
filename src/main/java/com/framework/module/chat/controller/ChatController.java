package com.framework.module.chat.controller;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.framework.module.chat.service.ChatService;
import com.framework.module.chat.vo.VWebIMToken;
import com.framework.module.common.plugins.emchat.constant.ContentType;
import com.framework.module.common.plugins.emchat.exception.UserUnRegistException;

/**
 * 聊天 Controller
 * 
 * @author qq
 * 
 */
@Controller
@RequestMapping("/ChatController")
public class ChatController {
	@Autowired
	private ChatService chatService;

	/**
	 * 获取webIM token(用于前端JS使用token登录)
	 * 
	 * @return VWebIMToken
	 * @throws UserUnRegistException
	 *             用户未注册
	 */
	@ResponseBody
	@RequestMapping("/getWebIMToken")
	@RequiresAuthentication
	public VWebIMToken getWebIMToken() {
		return chatService.getWebIMToken();
	}

	/**
	 * 注册环信
	 * 
	 * @param userName
	 *            用户名
	 */
	@ResponseBody
	@RequestMapping("/regist")
	public void regist(String userName) {
		chatService.regist(userName);
	}

	/**
	 * @param msg
	 * @param receivers
	 */
	@ResponseBody
	@RequestMapping("/sendMessage")
	@RequiresAuthentication
	public void sendMessage(String msg, String receivers, ContentType contentType) {
		chatService.sendMessage(msg, receivers, contentType);
	}

}