package com.framework.module.common.designPattern.proxy.staticProxy;

/**
 * 真实主题(使用该BigImage模拟一个很大图片)
 * 
 * @author qq
 *
 */
public class BigImage implements Image {
	public BigImage() {
		try {
			// 程序暂停3s模式模拟系统开销
			Thread.sleep(3000);
			/*
			 * 如上程序暂停了3s，这表明创建一个BigImage对象需3s的时间开销---使用这种延迟来模拟装载此图片所导致的系统开销。
			 * 若不采用代理模式 ，当程序创建BigImage时，系统将会产生3s的延迟
			 */
			System.out.println("图片装载成功...");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void show() {
		System.out.println("绘制实际的大图片");
	}

}
