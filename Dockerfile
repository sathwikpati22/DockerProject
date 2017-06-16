FROM tomcat:8-jre8
EXPOSE 8080 80
MAINTAINER "sathwik <sathwikpati22@gmail.com>"
ADD WeatherUI.war /usr/local/tomcat/webapps/