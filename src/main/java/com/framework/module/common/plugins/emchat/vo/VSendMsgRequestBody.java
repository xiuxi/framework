package com.framework.module.common.plugins.emchat.vo;

/**
 * 环信发送消息请求实体 VO
 * 
 * @author qq
 *
 */
public class VSendMsgRequestBody {
	/*
	 * **************************************************
	 * 
	 * **************************************************
	 */
	/**
	 * 消息接收者
	 * 
	 * 给用户发送时数组元素是用户名
	 * 
	 * 给群组发送时数组元素是groupid(数组长度建议不大于20)
	 */
	private String[] target;

	/**
	 * 扩展字段(可发送自己专属的消息结构)
	 */
	private VExt ext;

	/*
	 * **************************************************
	 * 
	 * 以下属性写屎,对使用层关闭
	 * 
	 * **************************************************
	 */
	/**
	 * 聊天对象类型
	 * 
	 * 聊天对象类型全部为users,真正的聊天对象类型定义在扩展字段(ext)中
	 */
	private String target_type;

	/**
	 * 消息对象
	 * 
	 * 消息类型全部为txt,消息内容全部为"txt",真正的消息类型、消息内容定义在扩展字段(ext)中
	 */
	private VMsg msg;

	/*
	 * constructor
	 */
	public VSendMsgRequestBody(String[] target, VExt ext) {
		super();
		this.target_type = "users";
		this.msg = new VMsg("txt", "txt");

		this.target = target;
		this.ext = ext;
	}

	/**
	 * 消息对象
	 */
	class VMsg {
		/**
		 * 消息类型
		 */
		private String type;

		/**
		 * 消息内容
		 */
		private String msg;

		/*
		 * constructor
		 */
		public VMsg(String type, String msg) {
			super();
			this.type = type;
			this.msg = msg;
		}

		/*
		 * getter、setter
		 */
		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getMsg() {
			return msg;
		}

		public void setMsg(String msg) {
			this.msg = msg;
		}
	}

	/*
	 * getter、setter
	 */
	public String[] getTarget() {
		return target;
	}

	public void setTarget(String[] target) {
		this.target = target;
	}

	public VExt getExt() {
		return ext;
	}

	public void setExt(VExt ext) {
		this.ext = ext;
	}

	public String getTarget_type() {
		return target_type;
	}

	public void setTarget_type(String target_type) {
		this.target_type = target_type;
	}

	public VMsg getMsg() {
		return msg;
	}

	public void setMsg(VMsg msg) {
		this.msg = msg;
	}

}
