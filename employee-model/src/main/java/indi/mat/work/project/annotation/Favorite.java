package indi.mat.work.project.annotation;


import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Favorite {
    String method() default "";

    /**
     * FavoritesBusinessType 类型
     */
    String type() default "COMPANY";
}
