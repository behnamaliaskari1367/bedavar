package com.behdavar.backservices.common.util.date;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DateUtil {

    private static final Pattern DATE_PATTERN_1 = Pattern.compile("^(\\d{4})\\D(\\d{2})\\D(\\d{2})$");
    private static final String DATE_PATTERN_2 = "yyyyMMdd";

    public static Date convert(String jalaliDate) {
        if (Objects.isNull(jalaliDate) ||
                jalaliDate.trim().length() != 10) {
            return null;
        }

        Matcher matcher = DATE_PATTERN_1.matcher(jalaliDate);
        boolean matches = matcher.matches();
        if (!matches) {
            return null;
        }

        String year = matcher.group(1);
        String month = matcher.group(2);
        String day = matcher.group(3);

        JalaliCalendar calendar = new JalaliCalendar();
        calendar.set(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day));

        return calendar.toGregorian().getTime();
    }

    public static String format(Date date) {
        if (Objects.isNull(date)) {
            return null;
        }

        SimpleDateFormat format = new SimpleDateFormat(DATE_PATTERN_2);
        return format.format(date);
    }

    public static Date getStartOfDate(Date date) {
        GregorianCalendar start = new GregorianCalendar();
        start.setTime(date);
        start.set(Calendar.HOUR_OF_DAY, 0);
        start.set(Calendar.MINUTE, 0);
        start.set(Calendar.SECOND, 0);

        return start.getTime();
    }

    public static Date getEndOfDate(Date date) {
        GregorianCalendar end = new GregorianCalendar();
        end.setTime(date);
        end.set(Calendar.HOUR_OF_DAY, 23);
        end.set(Calendar.MINUTE, 59);
        end.set(Calendar.SECOND, 59);

        return end.getTime();
    }

    public static boolean isSameDay(Date date1, Date date2) {

        String format1 = format(date1);
        String format2 = format(date2);

        return Objects.equals(format1, format2);
    }

    public static Date addDate(Date date, int amount) {
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, amount);
        return calendar.getTime();
    }

    public static List<Date> getDatesBetweenTwoDates(Date startDate, Date endDate) {
        List<Date> list = new ArrayList<>();
        if (endDate.getTime() < startDate.getTime()) {
            return list;
        }
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);

        Calendar endCalendar = new GregorianCalendar();
        endCalendar.setTime(endDate);

        while (calendar.before(endCalendar)) {
            Date result = calendar.getTime();
            list.add(result);
            calendar.add(Calendar.DATE, 1);
        }
        return list;
    }
}
