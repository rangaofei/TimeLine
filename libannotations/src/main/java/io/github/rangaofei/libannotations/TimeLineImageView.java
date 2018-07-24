package io.github.rangaofei.libannotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface TimeLineImageView {
    boolean key() default true;

    String id() default TimeConfig.ID_NULL;
}
