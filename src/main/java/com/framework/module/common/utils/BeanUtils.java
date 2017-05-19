package com.framework.module.common.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.framework.module.common.utils.copier.FastCopier;
import com.framework.module.common.utils.copier.FastCopierConverter;

/**
 * Bean 工具类
 * 
 * @author qq
 * 
 */
public class BeanUtils {
	/**
	 * 拷贝器
	 * 
	 * @author qq
	 * 
	 */
	public static class Copier {
		/**
		 * BeanCopier拷贝速度快，性能瓶颈出现在创建BeanCopier实例的过程中,
		 * 所以，把创建过的BeanCopier实例放到缓存中，下次可以直接获取，提升性能
		 */
		private static Map<String, FastCopier> beanCopierMap = new HashMap<String, FastCopier>();

		/**
		 * 排除字段
		 */
		private Set<String> excludes;

		/**
		 * 是否包含null值属性
		 * 
		 * @return
		 */
		private boolean includeNull = true;

		/**
		 * 数据来源
		 */
		private Object from;

		/**
		 * 数据目标
		 */
		private List<Object> toList;

		/**
		 * 指定排除的字段名称
		 * 
		 * @param names
		 *            要排除的字段名称
		 * 
		 * @return
		 */
		public Copier excludes(String... names) {
			for (String name : names) {
				if (StringUtils.isNotBlank(name)) {
					if (this.excludes == null) {
						excludes = new HashSet<String>();
					}
					this.excludes.add(name.trim());
				}
			}
			return this;
		}

		/**
		 * 排除null字段
		 * 
		 * @return
		 */
		public Copier excludeNull() {
			this.includeNull = false;
			return this;
		}

		/**
		 * 设置数据来源
		 * 
		 * @param from
		 *            数据来源
		 * @return
		 */
		public Copier from(Object from) {
			this.from = from;
			beginCopy();
			return this;
		}

		/**
		 * 设置数据目标
		 * 
		 * @param tos
		 *            数据目标
		 * @return
		 */
		public Copier to(Object... tos) {
			toList = new ArrayList<Object>(tos.length);
			for (Object to : tos) {
				if (to != null) {
					toList.add(to);
				}
			}
			beginCopy();
			return this;
		}

		/**
		 * 复制属性
		 * 
		 * @param source
		 *            数据来源
		 * 
		 * @param target
		 *            数据目标
		 * 
		 * @param converter
		 *            转换器(null则使用默认转换器)
		 */
		private static void copyProperties(Object source, Object target, FastCopierConverter converter) {
			String beanKey = generateKey(source.getClass(), target.getClass());

			FastCopier copier = null;
			if (!beanCopierMap.containsKey(beanKey)) {
				copier = FastCopier.create(source.getClass(), target.getClass(), converter == null ? false : true);
				beanCopierMap.put(beanKey, copier);
			} else {
				copier = beanCopierMap.get(beanKey);
			}

			copier.copy(source, target, converter);
		}

		/**
		 * @param sourceClazz
		 * @param targetClazz
		 * @return
		 */
		private static String generateKey(Class<?> sourceClazz, Class<?> targetClazz) {
			return sourceClazz.toString() + targetClazz.toString();
		}

		/**
		 * 开始copy
		 */
		private void beginCopy() {
			if (from == null || toList == null || toList.isEmpty()) {
				return;
			}

			for (Object to : toList) {
				copyProperties(from, to, new FastCopierConverter() {
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
					@Override
					public Object convert(Object sourcePropertyValue, Object targetPropertyValue,
							Class sourcePropertyClass, Class targetPropertyClazz, Object copyPoropertyName) {
						if (excludes != null && excludes.contains(copyPoropertyName)) { // 排除字段,直接返回目标属性值
							return targetPropertyValue;
						}

						if (!includeNull && sourcePropertyValue == null) { // 不包含null值属性时,null值属性直接返回目标属性值
							return targetPropertyValue;
						}

						return sourcePropertyValue;
					}

				});

			}
		}
	}

	/**
	 * 从数据来源对象创建一个Copier
	 * 
	 * @param from
	 * @return
	 */
	public static Copier from(Object from) {
		return new Copier().from(from);
	}

	/**
	 * 从数据目标对象创建一个Copier
	 * 
	 * @param tos
	 * @return
	 */
	public static Copier to(Object... tos) {
		return new Copier().to(tos);
	}

}
