package net.stealthcat.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateFormatTest {
	public static void main(String[] args) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		System.out.println(format.parse("20171132"));
	}
}
