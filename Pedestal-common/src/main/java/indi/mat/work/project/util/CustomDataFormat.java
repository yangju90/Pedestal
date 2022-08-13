package indi.mat.work.project.util;

import com.fasterxml.jackson.databind.util.StdDateFormat;

import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Mat
 * @version : CustomDataFormat, v 0.1 2022-05-18 23:07 Yang
 */
public class CustomDataFormat extends StdDateFormat {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DateStringConstant.fulltime);

    @Override
    public Date parse(String dateStr) throws ParseException {
        return simpleDateFormat.parse(dateStr);
    }


    @Override
    public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
        String t = simpleDateFormat.format(date);
        toAppendTo.append(t);
        return toAppendTo;
    }
}
