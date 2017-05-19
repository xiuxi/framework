package com.framework.module.common.designPattern.adapter.classAdapter;

/**
 * 适配器
 * 
 * @author qq
 *
 */
public class Adapter extends Adaptee implements Target {
	@Override
	public void speek() {
		super.speekEnglish();
	}
}
