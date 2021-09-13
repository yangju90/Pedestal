package indi.mat.work.project.annotation;

import indi.mat.work.project.annotation.validator.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = { EmailValidator.class })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface Email {
    String message() default "{com.newegg.staffing.annotation.AssertBoolean.message}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
