package com.framework.module.common.designPattern.observer.jdk.subject;

import java.util.Observable;

/**
 * 主题(被观察者)
 * 
 * @author qq
 *
 */
public class Product extends Observable {
	private String name;
	private double price;

	/**
	 * contructor
	 */
	public Product() {

	}

	public Product(String name, double price) {
		this.name = name;
		this.price = price;
	}

	/*
	 * getter、setter
	 */
	public String getName() {
		return name;
	}

	/**
	 * 当程序调用name的setter方法来修改Product的name属性时
	 * 
	 * 程序自然触发该对象上注册的所有观察者
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
		// 使用Java自带的观察者模式，当需要通知观察者时，需先调用setChanged();，然后再调用notifyObservers();
		super.setChanged();
		notifyObservers(name);
	}

	public double getPrice() {
		return price;
	}

	/**
	 * 当程序调用price的setter方法来修改Product的price属性时
	 * 
	 * 程序自然触发该对象上注册的所有观察者
	 * 
	 * @param price
	 */
	public void setPrice(double price) {
		this.price = price;
		// 使用Java自带的观察者模式，当需要通知观察者时，需先调用setChanged();，然后再调用notifyObservers();
		super.setChanged();
		notifyObservers(price);
	}
}
