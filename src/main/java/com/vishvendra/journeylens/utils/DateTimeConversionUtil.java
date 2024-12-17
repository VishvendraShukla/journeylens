package com.vishvendra.journeylens.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class DateTimeConversionUtil {

  /**
   * Converts a date string to the start of the day (00:00:00.00000).
   *
   * @param dateString the date string in the format "dd-MM-yyyy"
   * @return LocalDateTime representing the start of the day
   */
  public static LocalDateTime convertToStartOfDay(String dateString) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    LocalDate localDate = LocalDate.parse(dateString, formatter);
    return localDate.atStartOfDay();
  }

  /**
   * Converts a date string to the end of the day (23:59:59.99999).
   *
   * @param dateString the date string in the format "dd-MM-yyyy"
   * @return LocalDateTime representing the end of the day
   */
  public static LocalDateTime convertToEndOfDay(String dateString) {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    LocalDate localDate = LocalDate.parse(dateString, formatter);

    // Return LocalDateTime at the end of the day
    return localDate.atTime(23, 59, 59, 999_999_999).truncatedTo(ChronoUnit.MILLIS);
  }

}
