package com.foondamate.foondamateapp.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

import static com.foondamate.foondamateapp.helpers.Utils.toDate;

public class DateValidatorImpl implements ConstraintValidator<DateValidator, String> {

    public boolean isValid(String date, ConstraintValidatorContext cxt) {
        if(date == null) return false;

        try{
            toDate(date);
            return true;
        }catch (DateTimeParseException ex){ //do nothing
        }
        return false;
    }
}
