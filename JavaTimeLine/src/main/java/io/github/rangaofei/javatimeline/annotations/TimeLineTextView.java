package io.github.rangaofei.javatimeline.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.github.rangaofei.javatimeline.TimeConfig;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface TimeLineTextView {
    boolean key() default true;

    String value() default "-1";

    String textColor() default TimeConfig.NULL;

    String textSize() default TimeConfig.NULL;

    String backGroundColor() default TimeConfig.NULL;
}
