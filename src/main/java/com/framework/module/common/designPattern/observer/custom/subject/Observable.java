package com.framework.module.common.designPattern.observer.custom.subject;

import java.util.ArrayList;
import java.util.List;

import com.framework.module.common.designPattern.observer.custom.observer.Observer;

/**
 * 主题（被观察者）抽象基类
 * 
 * JDK有提供，无须自己实现
 * 
 * @author qq
 *
 */
public abstract class Observable {
	List<Observer> observers = new ArrayList<Observer>();// 保存该对象上所有绑定的事件监听器

	/**
	 * 从该主题上注册观察者
	 * 
	 * @param obs
	 */
	public void registObserver(Observer obs) {
		observers.add(obs);
	}

	/**
	 * 从该主题中删除观察者
	 * 
	 * @param obs
	 */
	public void removeObserver(Observer obs) {
		observers.remove(obs);
	}

	/**
	 * 通知该主题上注册的所有观察者
	 * 
	 * @param value
	 */
	public void notifyObservers(Object value) {
		// 遍历注册到主题上的所有观察者
		for (Observer observer : observers) {
			// 显式每个观察者的update方法
			observer.update(this, value);
		}
	}

}
