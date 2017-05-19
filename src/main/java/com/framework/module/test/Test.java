package com.framework.module.test;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(value = RetentionPolicy.RUNTIME)
@Inherited
public @interface Test {
	String name() default "zhangsan";

	int age();
}
