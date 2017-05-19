package com.framework.module.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;

/**
 * 注解Controller方法中的参数
 * 
 * 从Request请求的Json参数中获取名=value的对象,并赋值给被注解的Controller参数
 * 
 * @author qq
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RequestJsonParam {
	/**
	 * Json参数对象名(默认: 被修饰参数的参数名)
	 * 
	 */
	@AliasFor("name")
	public String value() default "";

	/**
	 * 与value作用一样
	 */
	@AliasFor("value")
	String name() default "";

	/**
	 * 是否必须(默认: 必须)
	 * 
	 */
	public boolean required() default true;
}
