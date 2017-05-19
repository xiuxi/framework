package com.framework.module.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

/**
 * 分页 工具类
 * 
 * @author qq
 *
 */
public class PageUtils {
	/**
	 * 页面转换
	 * 
	 * @param sourcePage
	 * @param converter
	 * @return
	 */
	public static <S, T> Page<T> convert(final Page<S> sourcePage, final Converter<S, T> converter) {
		List<S> sources = sourcePage.getContent();
		List<T> targets = new ArrayList<T>();

		if (CollectionUtils.isNotEmpty(sources)) {
			for (S source : sources) {
				T target = converter.convert(source);

				targets.add(target);
			}
		}

		return new PageImpl(targets, new PageRequest(sourcePage.getNumber(), sourcePage.getSize()), sourcePage.getTotalElements());
	}

}
