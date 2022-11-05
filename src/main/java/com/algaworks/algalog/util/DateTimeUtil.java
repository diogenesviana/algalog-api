package com.algaworks.algalog.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateTimeUtil {
	
	public static String converterDateTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		return formatter.format(calendar.getTime());
	}

}
