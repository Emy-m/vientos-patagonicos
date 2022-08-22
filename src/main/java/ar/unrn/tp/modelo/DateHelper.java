package ar.unrn.tp.modelo;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateHelper {
    private DateHelper() {
    }

    public static LocalDate convertToLocalDate(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    public static LocalDateTime convertToLocalDateTime(Date dateToConvert) {
        return Instant.ofEpochMilli(dateToConvert.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }

    public static Date convertToDate(LocalDate dateToConvert) {
        return Date.from(dateToConvert.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date convertToDate(LocalDateTime dateToConvert) {
        return Date.from(dateToConvert.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static boolean isDateBetween(Date from, Date to, LocalDate when) {
        boolean result = true;
        LocalDate start = DateHelper.convertToLocalDate(from);
        LocalDate end = DateHelper.convertToLocalDate(to);

        if (!when.isEqual(start) && when.isBefore(start)) {
            result = false;
        }

        if (when.isEqual(end) || when.isAfter(end)) {
            result = false;
        }

        return result;
    }

    public static Date nowWithTime() {
        return DateHelper.convertToDate(LocalDateTime.now(ZoneId.systemDefault()));
    }

    public static Date nowWithTime(int plusDays) {
        return DateHelper.convertToDate(LocalDateTime.now(ZoneId.systemDefault()).plusDays(plusDays));
    }
}
