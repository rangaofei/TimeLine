package io.github.rangaofei.javatimeline.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface TimeLineImageView {
    boolean key() default true;

    String value() default "-1";
}
