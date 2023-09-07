import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BirthdayValidator extends CommonValidator implements ConstraintValidator<BirthdayInspect, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext context) {
        String res = evaluation(s);
        if(res.length() != 0) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(res).addConstraintViolation();
            return false;
        }
        return true;
    }

    public boolean customEvaluation(StringBuilder sb, String s){
        if(StringUtils.isBlank(s)) {
            if(SpringContextUtils.getBean(InspectionConfig.class).inspectNull) ValidatorUtils.addQir(sb, "生日为空");
            return false;
        }else {
            if(!s.matches("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}")) ValidatorUtils.addQir(sb, "生日格式错误");
        }
        return true;
    }

    private Date format(String s){
        String pattern = "yyyy-MM-dd HH:mm:ss";

        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        try {
            Date date = sdf.parse(s);
            return date;
        } catch (ParseException e) {
        }
        return null;
    }
}
