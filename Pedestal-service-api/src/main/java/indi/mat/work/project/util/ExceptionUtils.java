package indi.mat.work.project.util;

import indi.mat.work.project.exception.BadResponse;
import indi.mat.work.project.exception.BaseException;
import org.springframework.context.MessageSource;

import java.util.LinkedList;
import java.util.Locale;

/**
 * @author Mat
 * @version : ExceptionUtils, v 0.1 2022-01-22 20:00 Yang
 */
public class ExceptionUtils {
    public static  BadResponse createBadResponse(BaseException baseException){
        String errorMessage = "";

        try {
            errorMessage = SpringContextUtils.getBean(MessageSource.class).getMessage(baseException.getMessage(), baseException.getArgs(), Locale.ENGLISH);
        }catch (Exception ex){
            errorMessage = baseException.getMessage();
        }

        errorMessage = errorMessage.replaceAll("\\s+$", "");
        if(errorMessage.contains("|")){
            String[] error = errorMessage.split("[|]");
            return new BadResponse(error[0], error[1]);
        }else {
            return new BadResponse("99999", errorMessage);
        }

    }
}
