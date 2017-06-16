package org.sathwik.java.weather;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
public class ForecastService {
	
	
	public List<climate> getForecast2(String date) {
		
		long stDate = Long.parseLong(date);
		List<climate> dataLt = new ArrayList<climate>();
		long[] days = new long[7]; // Array to store next 7 consecutive days forecast
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMdd"); //date format
		Date date1 = null;
		try {
		date1 = fmt.parse(String.valueOf(stDate));
		} catch(Exception e){
			e.printStackTrace();
		}
		
		days[0] = date1.getTime()/1000; // Convert to epoch timestamp in seconds
		Calendar cd = Calendar.getInstance();
		cd.setTime(date1);
		// Calculating the next 6 dates epochs
		for(int i =1; i <= 6;i++){
			cd.add(Calendar.DATE  , 1);
		    days[i] = cd.getTimeInMillis()/1000;			
		}
		
		// Iterating the next 7 days and calling the api
		for(int i =0; i <= 6; i++){
			
			String idate = String.valueOf(days[i]);
			//darksky api for forecasting the future date weather
			String uri = "https://api.darksky.net/forecast/3f2dc33b4ccece95c336fcbecbc32227/39.1031,-84.5120,"+idate+"?exclude=currently,hourly,minutely,flags";
			//Reading the api data
			URL url = null;
			try {
				url = new URL(uri);
			} catch (Exception e) {
				e.printStackTrace();
			}
			HttpURLConnection connection;
			
			try {
				connection = (HttpURLConnection) url.openConnection();
			
				try {
					connection.setRequestMethod("GET");
				} catch (ProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				connection.setRequestProperty("Accept", "application/json"); // considering the api returns json data
				BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
				String output;
				
				while ((output = br.readLine()) != null) {
						
					//Parsing the JSON data
					JSONParser parser = new JSONParser();					
					JSONObject json = null;
					try {
						json = (JSONObject) parser.parse(output);
					} catch (Exception e) {
						e.printStackTrace();
					}
					JSONObject dailyData = (JSONObject) json.get("daily");
					List dailyList1 = (List) dailyData.get("data");
					JSONObject dailyData1 = (JSONObject) dailyList1.get(0);
					//Reading the max and min Temp from JSON response
					String maxTemp = dailyData1.get("temperatureMax").toString();
					String minTemp = dailyData1.get("temperatureMin").toString();
					//Converting the date(epoch) back into the general format 
					Date datei = new Date ();
					datei.setTime((long)days[i]*1000);
					String dataStr = fmt.format(datei);
					climate datact = new climate(dataStr, maxTemp, minTemp);
					//adding the records to arraylist of type climate
					dataLt.add(datact);
					
				}
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		return dataLt;

	}
}
