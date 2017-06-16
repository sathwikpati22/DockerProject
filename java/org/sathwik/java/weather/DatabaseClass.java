package org.sathwik.java.weather;

import java.util.HashMap;
import java.util.Map;

public class DatabaseClass {

		private static Map<String, climate> records = new HashMap<>();
		
		public static Map<String, climate> getRecords() {
			return records;
		}
		
}
