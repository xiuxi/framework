package com.framework.module.common.designPattern.command;

/**
 * @author qq
 *
 */
public class ProcessArray {
	/**
	 * 定义一个each方法，用于处理数组，但具体如何处理暂时不能确定
	 * 
	 * @param target
	 * @param cmd
	 */
	public void each(int[] target, Command cmd) {
		cmd.process(target);
	}
}
