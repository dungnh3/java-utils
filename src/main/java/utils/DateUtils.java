package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author dungnh3
 * @since Aug 14th 2019
 */
public class DateUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String convertToString(Date date, String ouputFormat) {
        try {
            DateFormat dateFormat = (ouputFormat == null || ouputFormat.isEmpty()) ? new SimpleDateFormat(DATE_FORMAT) : new SimpleDateFormat(ouputFormat);
            return dateFormat.format(date);
        } catch (Exception exception) {
            System.err.println("Error! Can't convert Date to String: " + exception.getMessage());
        }
        return null;
    }

    public static String getDate(Date date, String ouputFormat) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        date = calendar.getTime();
        return convertToString(date, ouputFormat);
    }
}
