package org.sathwik.java.weather;

public class climate {

	private String DATE;
	private String TMAX;
	private String TMIN;
	
	public climate() {
		
	}

	public climate( String cdate, String maxTemp, String minTemp){
		this.DATE = cdate;		
		this.TMAX = maxTemp;
		this.TMIN = minTemp;
	}

	public String getDATE() {
		return DATE;
	}

	public void setDATE(String dATE) {
		DATE = dATE;
	}

	public String getTMAX() {
		return TMAX;
	}

	public void setTMAX(String tMAX) {
		TMAX = tMAX;
	}

	public String getTMIN() {
		return TMIN;
	}

	public void setTMIN(String tMIN) {
		TMIN = tMIN;
	}


	
}
