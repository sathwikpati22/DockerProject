# DockerProject

Project: Docker Image for the WebApplication created 
This project allows you to migrate the WebApplication into a docker container instance, that acts as a more portable framework for hosting services.

STEPS TO RUN THE IMAGE

Download weatherapp.tar from the below link
https://drive.google.com/open?id=0B68l2r45QsT-b0l1VlJHYWtpN0U

1. Install the docker in the system. In windows download the docker software and install it.
	In Linux run the command yum install docker 

2. Start the docker service. In windows start from services and in linux command service docker start

3. Load the tar file using the below command 
	docker load -i weatherapp.tar
	The docker will laod the image and generate an imageid 
	
4. Run the docker with the generated imageid 
	docker run -d -p 8081:80 <imageid>
	
5. Then run the application by accessing the link
	http://<localhost>:8081/WeatherUI

Steps Followed to create a docker container:

1. Launced a linux instance and logged into the EC2 linux instance.
 
2. Installed docker in the instance: yum install docker

3. Create a Dockerfile with the following content

  Dockerfile:
	FROM tomcat:8-jre8
	MAINTAINER "sathwik <sathwikpati22@gmail.com>"
	ADD WeatherUI.war /usr/local/tomcat/webapps/
	
4. Drop a WeatherUI.war file in the current folder location 

5. Use the following command to build docker image docker build -t webserver .
	It should build the image and show a success message.
	
6. Run the image and check docker run -p 80:80 webserver

7. To create container for the image by running it: docker run -d -p 8080:8080 <image name>

8. To check the processess running in the instance: docker ps -a

9. To execute the server in 80 port i modified the server.xml file in the docker container: docker exec -it <containerid> bash
	It will navigate you to the docker container 
	Modify the server.xml and change the port as 80 instead of 8080
	and exit from the container

10. Commit the changes in the docker with a new name and version 

	docker commit <containerid> weatherapp:latest
	
11. Save the docker image as a tar docker save <image id> > out.tar


//Description about the Part-I Developing REST API and responses 

API related code exists in java folder

**API Documentation **
**1. Description: List of all dates for which weather information is available as a JSON array.**
**URI:** http://sample-env-1.xfv2hfrwma.us-east-2.elasticbeanstalk.com/historical
**Method:** GET
**Input parameters:** None
**Response Type:** application/json
**Sample Response:**
[{"DATE": "20130101"},
{"DATE": "20130102"}, ......]

**2. Description: Weather information for a particular date. if no information is available - 404 error**
**URI:** http://sample-env-1.xfv2hfrwma.us-east-2.elasticbeanstalk.com/historical/<date>
**Method:** GET
**<date>** String: YYYYMMDD
**Response Type:** application/json
**Sample**
URI: http://sample-env-1.xfv2hfrwma.us-east-2.elasticbeanstalk.com/historical/20151202
Response: {
 "DATE": "20151202", "TMAX": "49", "TMIN": "34"
}
Status code: 200 if data found/ 404 Not Found for an invalid date

**3. Description: Add weather information for a particular day**
**URI:** http://sample-env-1.xfv2hfrwma.us-east-2.elasticbeanstalk.com/historical
**Method:** POST
**Input Type:** (application/json)
**Sample**
Input
 {
  "DATE": "20170227",
  "TMAX": "73.5",
  "TMIN": "57"
}
Status code: 201 if added successfully/ 409 if creating a duplicate/ 406 if invalid date format/ 415 if Unsupprted Input Type

**4. Description: Delete weather info of a particular day**
**URI:** http://sample-env-1.xfv2hfrwma.us-east-2.elasticbeanstalk.com/historical/<date>
**Method:** DELETE
**<date>** String:YYYYMMDD
Status code: 200 Record Delete / 404 Not Found if trying to delete an unexisting record
**Sample**
URI: http://sample-env-1.xfv2hfrwma.us-east-2.elasticbeanstalk.com/historical/20150112
Response: Record Deleted

**5. Description: Weather forecast for next 7 days - the date could be an existing date or future date**
**URI:** http://sample-env-1.xfv2hfrwma.us-east-2.elasticbeanstalk.com/forecast/<date>
**<date>** String: YYYYMMDD
**Response:** Returns the next 7 days Maximum Temperature 
**Response Type:** application/json
**Sample:**
URI: http://sample-env-1.xfv2hfrwma.us-east-2.elasticbeanstalk.com/forecast/20170305
Response:
[
  {
    "DATE": "20170305",
    "TMAX": "49.54",
    "TMIN": "28.87"
  },
  {
    "DATE": "20170306",
    "TMAX": "60.79",
    "TMIN": "30.28"
  },
  {
    "DATE": "20170307",
    "TMAX": "61",
    "TMIN": "46.98"
  },
  {
    "DATE": "20170308",
    "TMAX": "63.7",
    "TMIN": "44.98"
  },
  {
    "DATE": "20170309",
    "TMAX": "50.02",
    "TMIN": "36.2"
  },
  {
    "DATE": "20170310",
    "TMAX": "53.3",
    "TMIN": "29.5"
  },
  {
    "DATE": "20170311",
    "TMAX": "48.62",
    "TMIN": "29.54"
  }
]


// Description about the webpage to use the weather App

	http://sample-env.xfv2hfrwma.us-east-2.elasticbeanstalk.com/

	Use the above link to get the page
	
	In the webpage select a date in the Date textfield, hit on the forecast button

	The left one gives the forecast from rest api which i developed and the right one gives the forecast from external api.

	The external api i used is from darksky.