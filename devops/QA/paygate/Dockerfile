#need to pull the current version of tomact
FROM tomcat:8-jre8
# Copy files from local Directory  into tomcat path
ADD paygate.war /usr/local/tomcat/webapps/
ADD chatak-host-pg.war /usr/local/tomcat/webapps/ 
ADD server.xml /usr/local/tomcat/conf/
ADD context.xml /usr/local/tomcat/conf/
ADD web.xml /usr/local/tomcat/conf/
ADD chatak-pg-acquirer.properties /home/
EXPOSE 8062
CMD ["catalina.sh","run"]
