package org.sathwik.java.weather;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/forecast")
public class ForecastResource {
	
	ForecastService fs = new ForecastService();
	
	@GET
	@Path("/{date}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<climate> getForecast(@PathParam("date") String date){
		return fs.getForecast2(date);
	}

}
