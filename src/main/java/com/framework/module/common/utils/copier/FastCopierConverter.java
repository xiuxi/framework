package com.framework.module.common.utils.copier;

/**
 * @author qq
 *
 */
public interface FastCopierConverter {
	/**
	 * 基于目标对象的属性出发，如果源对象有相同名称的属性，则调一次convert方法
	 * 
	 * @param sourcePropertyValue
	 *            源对象属性值
	 * @param targetPropertyValue
	 *            目标对象属性值
	 * @param sourcePropertyClass
	 *            源对象属性类
	 * @param targetPropertyClass
	 *            目标对象属性类
	 * @param copyPoropertyName
	 *            要copy的属性名
	 * @return 返回对象用于设置目标对象的属性值
	 */
	Object convert(Object sourcePropertyValue, Object targetPropertyValue, Class sourcePropertyClass,
			Class targetPropertyClass, Object copyPoropertyName);
}
