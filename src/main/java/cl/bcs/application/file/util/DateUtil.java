package cl.bcs.application.file.util;

import java.time.LocalDate;

public class DateUtil {
	
	public static String fecha() {
		LocalDate localDate = LocalDate.now();
		return localDate.toString();
	}
}
