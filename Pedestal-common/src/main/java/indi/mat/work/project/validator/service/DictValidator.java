import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.qggsl.common.validator.inspection.DictInspect;
import com.qggsl.modules.sys.service.impl.SysDicCodeInfoServiceImpl;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.metadata.ConstraintDescriptor;
import java.util.HashSet;

public class DictValidator implements ConstraintValidator<DictInspect, String> {
    @Autowired
    SysDicCodeInfoServiceImpl service;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        ConstraintDescriptor<DictInspect> descriptor = (ConstraintDescriptor<DictInspect>) ((ConstraintValidatorContextImpl) context).getConstraintDescriptor();
        DictInspect dictInspect = descriptor.getAnnotation();
        if (StringUtils.isBlank(s)) {
            if (dictInspect.needValidationBlank()) {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate("属性不能为空").addConstraintViolation();
                return false;
            }
        } else {
            String i = dictInspect.itemName();
            if (ObjectUtils.isNotEmpty(i)) {
                String[] ins = i.split(",");
                boolean flag = false;
                for(String itemName : ins) {
                    HashSet<String> sets = service.getCodesByItemName(itemName);
                    if (sets.contains(s)) {
                        flag = true;
                    }
                }
                return flag;
            }
        }
        return true;
    }

}
