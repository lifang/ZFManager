/**
 * 
 */
package com.comdosoft.financial.manage.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gookin.wu
 *
 * Email: gookin.wu@gmail.com
 * Date: 2015年4月20日 下午8:25:57
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HasRole {
	String[] value() default {};
}
