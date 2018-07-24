package io.github.rangaofei.libannotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 表示这个类将会是用来做list元素的类
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface TimeLine {
    String name() default "";

    String keyLayoutId() default TimeConfig.ID_NULL;

    String valueLayoutId() default TimeConfig.ID_NULL;

}
