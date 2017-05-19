package com.framework.module.common.designPattern.bridge.dimension2;

import com.framework.module.common.designPattern.bridge.AbstractNoodle;
import com.framework.module.common.designPattern.bridge.dimension1.Peppery;

/**
 * 材料维度(猪肉)
 * 
 * @author qq
 *
 */
public class PorkyNoodle extends AbstractNoodle {
	public PorkyNoodle(Peppery style) {
		super(style);
	}

	@Override
	public void eat() {
		System.out.println("这是一碗猪肉面条: " + super.style.style());
	}
}
