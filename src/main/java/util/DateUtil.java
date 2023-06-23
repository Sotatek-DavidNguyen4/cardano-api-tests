package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    public static boolean compareDurations(String date, String startDate, String endDate, String format) {
        SimpleDateFormat dateFmt = new SimpleDateFormat(format);
        try {
            Date fromDate = dateFmt.parse(startDate);
            Date toDate = dateFmt.parse(endDate);
            Date actualDate = dateFmt.parse(date);
            if (actualDate.compareTo(fromDate) >= 0 || actualDate.compareTo(toDate) <= 0) {
                return true;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    public static boolean compareDurationsMonth(String date,int month,String dateFormat) {
        DateFormat formatter = new SimpleDateFormat(dateFormat);

        try {
            Date utcDate = formatter.parse(getCurrentUTCDate(dateFormat));
            Date dateToCheck = formatter.parse(date);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(utcDate);

            int currentYear = calendar.get(Calendar.YEAR);
            int currentMonth = calendar.get(Calendar.MONTH) + 1; // January is represented by 0
            int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

            int previousDay = currentDay;
            int previousMonth = currentMonth - month;
            int previousYear = currentYear;

            if (previousMonth <= 0) {
                previousMonth += 12;
                previousYear--;
            }

            calendar.set(previousYear, previousMonth - 1, previousDay);
            int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

            if (previousDay > daysInMonth) {
                System.out.println("The specified day does not exist in the previous month.");
                calendar.set(Calendar.DAY_OF_MONTH, daysInMonth);
                previousDay = calendar.get(Calendar.DAY_OF_MONTH);
            }

            Date endDate = calendar.getTime();
            calendar.setTime(dateToCheck);

            int targetYear = calendar.get(Calendar.YEAR);
            int targetMonth = calendar.get(Calendar.MONTH) + 1;
            int targetDay = calendar.get(Calendar.DAY_OF_MONTH);

            calendar.set(targetYear, targetMonth - 1, targetDay);
            Date targetDate = calendar.getTime();

            return (targetDate.equals(utcDate) || targetDate.equals(endDate)) ||
                    (targetDate.before(utcDate) && targetDate.after(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static String getCurrentUTCDate(String dateFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat).withZone(ZoneId.of("UTC"));
        String currentUTCDate = formatter.format(Instant.now());
        return currentUTCDate;
    }

    public static String getCurrentUTCSubDays(int days, String dateFormat) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat).withZone(ZoneId.of("UTC"));
        Instant instant = Instant.now().minus((long)days, ChronoUnit.DAYS);
        String currentUTCSubDays = formatter.format(instant);
        return currentUTCSubDays;
    }
}
