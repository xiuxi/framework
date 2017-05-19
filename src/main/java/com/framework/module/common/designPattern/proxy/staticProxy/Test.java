package com.framework.module.common.designPattern.proxy.staticProxy;

/**
 * 静态代理 测试
 * 
 * 实现延迟加载
 * 
 * @author qq
 *
 */
public class Test {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		// 程序返回一个Image对象，该对象只是BigImage的代理对象
		Image image = new ImageProxy(null);
		System.out.println("系统得到Image对象的时间开销：" + (System.currentTimeMillis() - start));
		// 只有当实际调用image代理的show()方法时，程序才会真正创建被代理对象
		image.show();
	}
}
