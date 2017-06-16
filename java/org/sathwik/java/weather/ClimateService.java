package org.sathwik.java.weather;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ClimateService {

	private Map<String, climate> records = DatabaseClass.getRecords();
	
	public ClimateService() {
			
//			String  csvFile = ClimateService.class.getClassLoader().getResource("daily.csv").getPath();
			String csvFile = sample.class.getClassLoader().getResource("daily.csv").getPath();
			String line = "";
			String seperator = ",";			
			Integer ct = 0;
			// Add all the data as records in hashMap
			if (records.isEmpty()) {
				try(BufferedReader br = new BufferedReader(new FileReader(csvFile))){
					
					if ((line = br.readLine()) != null) {
					
						String[] entry = line.split(seperator);
					
					}
					
					while((line = br.readLine()) != null){
				
						ct++;
						String[] entry = line.split(seperator);
						records.put(entry[0], new climate(entry[0], entry[1], entry[2]));
					}
					
					System.out.println(ct);
				
				} catch(Exception e) {
					
					e.printStackTrace();
					
				}
			}
	}
	
	public List<climate> getAllRecordDates(){
		
		List<climate> climateList = new ArrayList<climate>(records.values());		
		Iterator<climate> climateIterator = climateList.iterator();
		List<climate> dateLt = new ArrayList<climate>();
		
		while (climateIterator.hasNext()) {
			climate cl = new climate();
			cl = climateIterator.next();
			dateLt.add(new climate(cl.getDATE(),null,null));
		}
		return dateLt;
		
	}
	
	public climate getDateRecord(String date){

		climate cdata = records.get(date);
		return cdata;
		
	}
	
	public climate addRecord(climate record) {
		
		if (records.containsKey(record.getDATE())) {
			
			return null; // return null if the recource already exists
			
		}
		else{
					
			records.put(record.getDATE(), record);
			climate rd = new climate();
			rd.setDATE(record.getDATE());
			return rd;
			
		}
		
	}
	
	
	public climate removeRecord(String date) {
		return records.remove(date);
		
	}
	
	
	
}
