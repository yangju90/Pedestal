import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <p>
 * 公司财务信息表 前端控制器
 * </p>
 *
 * @author Marvin
 * @since 2021-08-27
 */
public class BooleanValidator implements ConstraintValidator<AssertBoolean, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if(value instanceof Boolean){
            return true;
        }
        return false;
    }

    @Override
    public void initialize(AssertBoolean constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
}
