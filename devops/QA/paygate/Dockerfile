#need to pull the current version of tomact
FROM tomcat:8-jre8
# Copy files from local Directory  into tomcat path
RUN mkdir -p /usr/local/tomcat/resources
ADD chatak-pg-acquirer.properties /usr/local/tomcat/resources
ADD paygate.war /usr/local/tomcat/webapps/
ADD chatak-host-pg.war /usr/local/tomcat/webapps/ 
ADD server.xml /usr/local/tomcat/conf/
ADD context.xml /usr/local/tomcat/conf/
EXPOSE 8062
CMD ["catalina.sh","run"]
