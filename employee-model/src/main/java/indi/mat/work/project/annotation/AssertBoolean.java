import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * <p>
 * 公司财务信息表 前端控制器
 * </p>
 *
 * @author Marvin
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
