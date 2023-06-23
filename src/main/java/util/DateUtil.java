package util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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
    public static boolean compareDurationsMonth(String date,int month) {
        String[] parts = date.split(" ");
        String datePart = parts[0];

        LocalDate dateToCheck = LocalDate.parse(datePart);

        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();
        int currentDay = currentDate.getDayOfMonth();

        int previousDay = currentDay;
        int previousMonth = currentMonth - month;
        int previousYear = currentYear;


        if (previousMonth == 0) { // Handle January
            previousMonth = 12; // Set to December
            previousYear--; // Decrease the year
        }

        YearMonth yearMonthObject = YearMonth.of(previousYear, previousMonth);
        int daysInMonth = yearMonthObject.lengthOfMonth();

        if (!(previousDay <= daysInMonth)) {
            System.out.println("The specified day does not exists in the previous month.");
            previousDay= yearMonthObject.atEndOfMonth().getDayOfMonth();
        }

        LocalDate endDate = LocalDate.of(previousYear, previousMonth, previousDay);
        LocalDate targetDate = LocalDate.of(dateToCheck.getYear(), dateToCheck.getMonth(), dateToCheck.getDayOfMonth());

        return (targetDate.isEqual(currentDate) || targetDate.isEqual(endDate)) ||
               (targetDate.isBefore(currentDate) && targetDate.isAfter(endDate));
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
