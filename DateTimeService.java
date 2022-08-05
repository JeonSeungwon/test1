package day1test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateTimeService {

	public String getNow() {

		// 경우에 따라 날짜, 시간, 날짜+시간 정보를 제공한다.
		String now = null;
		String pattern = "yyyyMMdd";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		now = sdf.format(new Date());

		return now;
	}
	
	public String calDate(String now, int day) {
		String result = null;
		Date date = null;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		try {
			date = sdf.parse(now);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cal.setTime(date);
		cal.add(Calendar.DATE, day);
		result = sdf.format(cal.getTime());
		return result;
	}
	

}
