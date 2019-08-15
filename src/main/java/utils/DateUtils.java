package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author dungnh3
 * @since Aug 14th 2019
 */
public class DateUtils {

    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static String convertDateToString(Date date, String ouputFormat) {
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
        return convertDateToString(date, ouputFormat);
    }

    public static Date convertStringToDate(String strDate, String format) {
        try {
            DateFormat dateFormat = (format != null || format.isEmpty()) ? new SimpleDateFormat(format) : new SimpleDateFormat(DATE_FORMAT);
            return dateFormat.parse(strDate);
        } catch (Exception exception) {
            System.err.println("Error! Can't convert String to Date: " + exception.getMessage());
            return null;
        }
    }

    public static String convertStringToString(String strDate, String inputFormat, String outputFormat) {
        Date date = convertStringToDate(strDate, inputFormat);
        return convertDateToString(date, outputFormat);
    }
}