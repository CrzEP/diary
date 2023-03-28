package com.dlg.diary.common.annotation;

import java.lang.annotation.*;

/**
 * 操作日志注解
 *
 * @author lingui
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiLog {

	String value() default "";
}
