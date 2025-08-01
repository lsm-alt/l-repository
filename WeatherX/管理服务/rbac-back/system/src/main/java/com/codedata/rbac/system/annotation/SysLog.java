package com.codedata.rbac.system.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface SysLog {
    /**
     *存储该接口排除的记录字段
     */
    public String[] excludeField() default {};
    /**
     * 日志类型
     */
    public String type();
}
