package com.framework.module.common.utils;

import java.io.Serializable;

/**
 * 
 * 克隆对象 工具类
 * 
 * @author qq
 * 
 */
public class CloneUtils {
	/**
	 * 深度克隆对象
	 * 
	 * @param object
	 *            要克隆的对象
	 * @return 克隆对象
	 */
	public static <T extends Serializable> T deepClone(T object) {
		T result = null;

		byte[] bytes = SerializeUtils.serialize(object);
		if (bytes != null && bytes.length > 0) {
			result = SerializeUtils.deSerialize(bytes);
		}

		return result;
	}

}
