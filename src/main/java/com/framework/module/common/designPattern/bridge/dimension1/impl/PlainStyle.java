package com.framework.module.common.designPattern.bridge.dimension1.impl;

import com.framework.module.common.designPattern.bridge.dimension1.Peppery;

/**
 * 不添加辣的风格
 * 
 * @author qq
 *
 */
public class PlainStyle implements Peppery {
	@Override
	public String style() {
		return "味道清淡，很养胃。。。";
	}
}
