package com.framework.module.common.designPattern.adapter.objectAdapter;

/**
 * 适配器类(Adapter)XiaoMingAdapter
 * 
 * 把XiaoMing适配过来
 * 
 * @author qq
 *
 */
public class MySon extends My {
	private XiaoMing xiaoming = new XiaoMing();

	@Override
	public void speekChinese() {
		xiaoming.speekEnglish();
	}
}
