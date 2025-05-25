package com.eyup.utilities;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class TimestampConverter {
    public static void main(String[] args) {
        System.out.println(convertDateTime("2361757335l"));
//        System.out.println(convertDateTime(convertToUnixTimestamp()));

    }

    public static String convertDateTime(String longNumber) {
        // Convert the string to a long
        longNumber = longNumber.replaceAll("l", "");
        long longValue = Long.parseLong(longNumber);

        // Convert the timestamp to an Instant
        Instant instant = Instant.ofEpochSecond(longValue);

        // Convert Instant to ZonedDateTime in UTC
        ZonedDateTime dateTime = ZonedDateTime.ofInstant(instant, ZoneId.of("UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT);

        // 1616425200l -> 22 Mar 2021, 15:00
        return dateTime.format(formatter);
    }

    public static String convertDateTime(long longNumber) {

        // Convert the timestamp to an Instant
        Instant instant = Instant.ofEpochSecond(longNumber);

        // Convert Instant to ZonedDateTime in UTC
        ZonedDateTime dateTime = ZonedDateTime.ofInstant(instant, ZoneId.of("UTC"));
        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.SHORT);

        // 1616425200 -> 22 Mar 2021, 15:00
        return dateTime.format(formatter);
    }

    public static String convertToUnixTimestamp() {
        // Get the current local date and time
        LocalDateTime localDateTime = LocalDateTime.now();

        // Format the LocalDateTime to a string representation
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm");
        String dateTimeString = localDateTime.format(formatter);
        ZoneId zoneId = ZoneId.systemDefault();

        // Convert to Unix timestamp in seconds
        return localDateTime.atZone(zoneId).toEpochSecond() + "l";
    }
}
