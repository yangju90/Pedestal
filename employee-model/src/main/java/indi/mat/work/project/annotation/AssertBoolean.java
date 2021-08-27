package indi.mat.work.project.annotation;

import indi.mat.work.project.annotation.validator.BooleanValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 *
 * @author Mat
 * @since 2021-08-27
 */
@Documented
@Constraint(validatedBy = { BooleanValidator.class })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface AssertBoolean {
    String message() default "{com.newegg.staffing.annotation.AssertBoolean.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
