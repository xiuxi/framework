package com.framework.module.common.designPattern.proxy.staticProxy;

/**
 * 代理类(为避免这种延迟，程序为BigImage对象提供一个代理对象)
 * 
 * @author qq
 *
 */
public class ImageProxy implements Image {
	// 组合一个image实例，作为被代理的对象
	private Image image;

	// 使用抽象实体来初始化代理对象
	public ImageProxy(Image image) {
		this.image = image;
	}

	@Override
	public void show() {
		// 只有当真正需要调用image的show方法时才创建被代理对象
		if (image == null) {
			image = new BigImage();
		}
		image.show();
	}
}
