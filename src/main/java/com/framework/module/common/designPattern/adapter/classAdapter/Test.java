package com.framework.module.common.designPattern.adapter.classAdapter;

/**
 * 适配器模式 测试(类适配器,适配器类继承/实现被适配的类)
 * 
 * @author qq
 *
 */
public class Test {
	public static void main(String[] args) {
		// 我本来就会说中文
		Target target1 = new Target() {
			@Override
			public void speek() {
				System.out.println("说中文");
			}
		};
		target1.speek();

		//
		Target target2 = new Adapter();
		target2.speek();
	}
}
