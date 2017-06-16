package org.sathwik.java.weather;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/historical")
public class ClimateResource {

		ClimateService records = new ClimateService();
		Utils util = new Utils();
	
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		public List<climate> getHistorical(){

			return records.getAllRecordDates();
		}

		@GET
		@Path("/{date}")
		@Produces(MediaType.APPLICATION_JSON)
		public climate getDate(@PathParam("date") String date){
			//Validating whether that particular date exits
			climate cdata = records.getDateRecord(date);
			if ( cdata != null ) {
				return cdata;
			} else {
				throw new NotFoundException();
			}
		}

		@POST 
		@Produces(MediaType.APPLICATION_JSON)
		@Consumes(MediaType.APPLICATION_JSON)
		public Response addRecord(climate clim){
			
			//validating date format
			Boolean err = util.validateDate(clim.getDATE());
			if ( err ) {
				return Response.status(Status.NOT_ACCEPTABLE).entity(clim).build();//Returns 406 if not acceptable format
			} else {
				climate ctD = records.addRecord(clim);
				if ( ctD != null) { // Returns 201 when successfully created 
					return Response.status(Status.CREATED).entity(ctD).build();
				} else { // Returns 409 when the resource already exists
					return Response.status(Status.CONFLICT).entity(ctD).build();
				}
			}
		}

		@DELETE
		@Path("/{date}")
		@Produces(MediaType.TEXT_PLAIN)
		public String removeRecord(@PathParam("date") String date){
			//Validating whether the record is existing or not
			climate cdata = records.getDateRecord(date);
			if ( cdata != null ) {
				records.removeRecord(date);
				return "Record Deleted";
			} else {
				throw new NotFoundException();
			}
			
		}
	
}


