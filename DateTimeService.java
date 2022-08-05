package Day1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateTimeService {

	public String getNow() {
		//현재시간을 yyyyMMdd 형태의 String으로 반환
		String now = null;
		String pattern = "yyyyMMdd";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		now = sdf.format(new Date());

		return now;
	}
	
	public String calDate(String now, int day) {
		// yyyyMMdd 형태의 String 날짜와 int dat를 입력받아 날짜에 day를 더해서 string반환
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
