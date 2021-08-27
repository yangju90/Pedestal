package indi.mat.work.project.annotation.validator;

import indi.mat.work.project.annotation.AssertBoolean;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Mat
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
