package net.home.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import net.home.exception.ErrCode;
import net.home.exception.GeneralException;

public class Validater {

    public static void validateDate(String date) throws GeneralException {
        try {
            LocalDate.parse(date, DateTimeFormatter.BASIC_ISO_DATE);
        } catch (DateTimeParseException e) {
            throw new GeneralException(ErrCode.DATE_FORMAT_ERROR, date);
        }
    }

    public static void validateMoney(String input) throws GeneralException {
        String datePattern = "[\\+-](([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$";
        if (!input.matches(datePattern)) {
            throw new GeneralException(ErrCode.MONEY_FORMAT_ERROR, input);
        }
    }

}
