package com.aurelia.loaning.db.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
	String type();

	String name();

	boolean nullable() default false;

	boolean primaryKeyAutoIncrement() default false;
}
