package cl.bcs.application.file.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class DateUtil {
	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy.MM.dd.HH.mm.ss");

	public static String getTimestamp() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return sdf.format(timestamp);
	}

}
