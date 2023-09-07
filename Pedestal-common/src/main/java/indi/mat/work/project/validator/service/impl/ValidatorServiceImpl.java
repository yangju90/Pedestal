import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class ValidatorServiceImpl implements ValidatorService {

    private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    @Override
    public <T> Object valid(T t) {
        HashMap shs = new HashMap();

        Set<ConstraintViolation<T>> violations = validator.validate(t);
        if (!violations.isEmpty()) {
            Map<String, Object> validationFailedData = new HashMap<>();
            for (ConstraintViolation<T> violation : violations) {
                validationFailedData.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            shs.put("code", "1");
            shs.put("msg", "faild");
            shs.put("data", validationFailedData);
        } else {
            shs.put("code", "0");
            shs.put("msg", "success");
        }
        return shs;
    }
}
