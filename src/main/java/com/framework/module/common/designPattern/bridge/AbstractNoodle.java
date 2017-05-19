package com.framework.module.common.designPattern.bridge;

import com.framework.module.common.designPattern.bridge.dimension1.Peppery;

/**
 * 维度管理类
 * 
 * @author qq
 *
 */
public abstract class AbstractNoodle {
	// 组合一个Peppery变量，用于将该维度的变化独立出来
	protected Peppery style;

	/**
	 * 每份Noodle必须组合一个Peppery对象
	 * 
	 * @param style
	 */
	public AbstractNoodle(Peppery style) {
		this.style = style;
	}

	public abstract void eat();

}
