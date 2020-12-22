package net.stealthcat.test.date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class DateCalculateTest {

    public static void main(String[] args) {
        Date now = new Date();
        Date start = DateUtils.truncate(DateUtils.addDays(now, -1), Calendar.DAY_OF_MONTH);
        Date end = DateUtils.ceiling(now, Calendar.DAY_OF_MONTH);

        System.out.println(DateFormatUtils.format(start, "yyyy-MM-dd HH"));
        System.out.println(DateFormatUtils.format(end, "yyyy-MM-dd HH"));
    }



}
