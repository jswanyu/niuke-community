package com.wanyu.community.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Classname: LoginRequired
 * @author: wanyu
 * @Date: 2022/5/15 22:35
 */
@Target(ElementType.METHOD)  // 元注解@Target表明自定义注解的作用范围，ElementType.METHOD表示是作用在方法上的
@Retention(RetentionPolicy.RUNTIME) // 元注解@Retention表明自定义注解的有效时间，RetentionPolicy.RUNTIME表示是运行期间有效
public @interface LoginRequired {
}
