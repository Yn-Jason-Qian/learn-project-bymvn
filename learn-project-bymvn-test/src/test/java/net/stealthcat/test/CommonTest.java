package net.stealthcat.test;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;

public class CommonTest {

    public static void main(String[] args) {
        Date now = new Date();
        Date start = DateUtils.truncate(DateUtils.addDays(now, -1), Calendar.DAY_OF_MONTH);
        Date end = DateUtils.ceiling(now, Calendar.DAY_OF_MONTH);
    }

}
