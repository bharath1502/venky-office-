# Pull base image
From tomcat:8-jre8
# Copy to images tomcat path
RUN mkdir -p /usr/local/tomcat/resources
ADD chatak-pg-acquirer.properties /usr/local/tomcat/resources
ADD server.xml /usr/local/tomcat/conf/
ADD context.xml /usr/local/tomcat/conf/
ADD gateway-admin.war /usr/local/tomcat/webapps/
ADD gateway-merchant.war /usr/local/tomcat/webapps/
EXPOSE 8061
CMD ["catalina.sh","run"]
