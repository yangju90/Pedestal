package indi.mat.work.project.annotation;


import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Favorite {
    String method() default "";

    Class<?> type() default java.lang.Object.class;
}
