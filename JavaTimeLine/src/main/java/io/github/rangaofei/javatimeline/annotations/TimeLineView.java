package io.github.rangaofei.javatimeline.annotations;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})

public @interface TimeLineView {

    enum ViewType {
        VIEW, TEXT_VIEW, IMAGE_VIEW,
    }

    ViewType value() default ViewType.VIEW;

    int backGroundColor() default 0xffffffff;
}
