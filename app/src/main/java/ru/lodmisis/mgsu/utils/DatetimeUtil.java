package ru.lodmisis.mgsu.utils;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by romanismagilov on 05.08.17.
 */

public class DatetimeUtil {
    public static String getRuMonth(Date date) {
        if (date!=null) {
            Locale russian = new Locale("ru");
            String[] newMonths = {
                    "января", "февраля", "марта", "апреля", "мая", "июня",
                    "июля", "августа", "сентября", "октября", "ноября", "декабря"};
            DateFormatSymbols dfs = DateFormatSymbols.getInstance(russian);
            dfs.setMonths(newMonths);
            DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, russian);
            SimpleDateFormat sdf = (SimpleDateFormat) df;
            sdf.setDateFormatSymbols(dfs);

            String tempDate = sdf.format(date);
            String[] dateSplit = tempDate.split(" ");
            return dateSplit[1];
        } else return "";

    }
}
