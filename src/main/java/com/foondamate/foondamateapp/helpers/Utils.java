package com.foondamate.foondamateapp.helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;

public final class Utils {

    private Utils() {
    }

    public static LocalDate toDate(String input) {
        return LocalDate.parse(                                        // Represent a date-only value, without time-of-day and without time zone.
                input,                                           // Input string.
                DateTimeFormatter                               // Define a formatting pattern to match your input string.
                        .ofPattern("dd-MM-uuuu")
                        .withResolverStyle(ResolverStyle.STRICT)  // Specify leniency in tolerating questionable inputs.
        );
    }
}
