package com.framework.module.test;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

public class TestMain {
	public static void main(String[] args) throws NoSuchMethodException, SecurityException, ClassNotFoundException, IOException {
		ClassLoader xx = ClassLoader.getSystemClassLoader();
		System.out.println(xx);

		Enumeration<URL> em = xx.getResources("");
		while (em.hasMoreElements()) {
			System.out.println(em.nextElement());
		}

	}

}
