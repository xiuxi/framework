package com.framework.module.common.designPattern.command;

/**
 * 命令接口 
 * 
 * @author qq
 *
 */
public interface Command {
	/**
	 * 接口里定义process方法用于封装“处理行为”
	 * 
	 * @param target
	 */
	void process(int[] target);
}
