package com.framework.module.common.designPattern.bridge;

import com.framework.module.common.designPattern.bridge.dimension1.impl.PepperyStyle;
import com.framework.module.common.designPattern.bridge.dimension1.impl.PlainStyle;
import com.framework.module.common.designPattern.bridge.dimension2.BeefNoodle;
import com.framework.module.common.designPattern.bridge.dimension2.PorkyNoodle;

/**
 * 桥接模式 测试 
 * 
 * @author qq
 *
 */
public class Test {
	public static void main(String[] args) {
		// 得到“辣味”的牛肉面
		AbstractNoodle noodle1 = new BeefNoodle(new PepperyStyle());
		noodle1.eat();

		// 得到“不辣”的牛肉面
		AbstractNoodle noodle2 = new BeefNoodle(new PlainStyle());
		noodle2.eat();

		// 得到“辣味”的猪肉面
		AbstractNoodle noodle3 = new PorkyNoodle(new PepperyStyle());
		noodle3.eat();

		// 得到“不辣”的猪肉面
		AbstractNoodle noodle4 = new PorkyNoodle(new PlainStyle());
		noodle4.eat();
	}

}
