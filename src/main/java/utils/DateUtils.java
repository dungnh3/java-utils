package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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

    public static Date addTime(Date date, int number, int timeType) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        /**
         * Sample
         * calendar.add(Calendar.YEAR, 1);
         * calendar.add(Calendar.MONTh, 1);
         * calendar.add(Calendar.DATE, 1);
         * calendar.add(Calendar.HOUR, 1);
         * calendar.add(Calendar.MINUTE, 1);
         * calendar.add(Calendar.SECOND, 1);
         */
        calendar.add(timeType, number);
        Date result = calendar.getTime();
        return result;
    }

    public static String addTime(String strDate, String formater, int number, int timeType) {
        Date date = convertStringToDate(strDate, formater);
        Date newDate = addTime(date, number, timeType);
        String result = convertDateToString(newDate, formater);
        return result;
    }

    public static String convertStringToString(String strDate, String inputFormat, String outputFormat) {
        Date date = convertStringToDate(strDate, inputFormat);
        return convertDateToString(date, outputFormat);
    }

    public static List<String> getListDate(String fromDate, String toDate, String inputFormat, String outputFormat) {
        Date _fromDate = convertStringToDate(fromDate, inputFormat);
        Date _toDate = convertStringToDate(toDate, inputFormat);

        HashSet<String> hashSet = new HashSet<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(_fromDate);

        DateFormat dateFormat = new SimpleDateFormat(outputFormat);
        while (calendar.getTime().before(_toDate)) {
            Date date = calendar.getTime();
            hashSet.add(dateFormat.format(date));
            calendar.add(Calendar.DATE, 1);
        }
        hashSet.add(dateFormat.format(_toDate));

        List<String> dates = new ArrayList<>(hashSet);
        dates = dates.stream().sorted((r1, r2) -> r1.compareTo(r2)).collect(Collectors.toList());
        return dates;
    }
}