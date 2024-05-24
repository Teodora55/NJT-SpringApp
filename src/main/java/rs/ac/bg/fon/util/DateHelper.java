package rs.ac.bg.fon.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateHelper {
 	public static LocalDate formatDate(LocalDate date) {
		DateTimeFormatter.ofPattern("dd-MM-YYYY").format(date);
		return date;
	}

	public static LocalDate getTodayDate() {
		return formatDate(LocalDate.now());
	}   
}
