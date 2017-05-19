package com.framework.module.common.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 
 * 序列化、反序列化 工具类
 * 
 * 序列化、反序列化的对象必须实现Serializable接口
 * 
 * @author qq
 * 
 */
public class SerializeUtils {
	/**
	 * 序列化(对象=>字节数组)
	 * 
	 * @param object
	 * @return
	 */
	public static <T extends Serializable> byte[] serialize(T object) {
		byte[] result = null;

		ByteArrayOutputStream byteArrayOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(object);
			result = byteArrayOutputStream.toByteArray();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		} finally {
			try {
				byteArrayOutputStream.close();
				objectOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * 反序列化(字节数组=>对象)
	 * 
	 * @param bytes
	 * @param type
	 * @return
	 */
	public static <T extends Serializable> T deSerialize(byte[] bytes) {
		T result = null;

		ByteArrayInputStream byteArrayInputStream = null;
		ObjectInputStream objectInputStream = null;
		try {
			byteArrayInputStream = new ByteArrayInputStream(bytes);
			objectInputStream = new ObjectInputStream(byteArrayInputStream);
			result = (T) objectInputStream.readObject();
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		} finally {
			try {
				byteArrayInputStream.close();
				objectInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

}
