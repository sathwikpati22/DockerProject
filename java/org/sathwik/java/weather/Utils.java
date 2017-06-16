package org.sathwik.java.weather;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

	public Boolean validateDate(String date2) {
		
		Boolean error = false; 		
		Integer datelen = date2.trim().length();		
		if ( datelen != 8) {
			
			error = true;
			
		} else {
			
			try {
				
				long stDate = Long.parseLong(date2);
				SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd");
				Date date1 = null;			
				date1 = fmt.parse(String.valueOf(stDate));
				Calendar cal = Calendar.getInstance();
				cal.setLenient(false);
				cal.setTime(date1);
				try {
				    cal.getTime();		    
				}
				catch (Exception e) {
					error = true;
				}

			} catch (Exception e ){
				error = true;
			}

		}
		return error;
	}
	
}
