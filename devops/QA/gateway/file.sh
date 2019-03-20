admin=$(curl -L $URL/repository/maven-snapshots/$groupid/$artifactid1/4.0.0-SNAPSHOT/maven-metadata.xml | grep -m 1 \<value\> | sed -e 's/<value>\(.*\)<\/value>/\1/' | sed -e 's/ //g')
wget --no-check-certificate "$URL/repository/maven-snapshots/$groupid/$artifactid1/4.0.0-SNAPSHOT/$artifactid1-${admin}.war" -O $artifactid1.war

merchant=$(curl -L $URL/repository/maven-snapshots/$groupid/$artifactid2/4.0.0-SNAPSHOT/maven-metadata.xml | grep -m 1 \<value\> | sed -e 's/<value>\(.*\)<\/value>/\1/' | sed -e 's/ //g')
wget --no-check-certificate "$URL/repository/maven-snapshots/$groupid/$artifactid2/4.0.0-SNAPSHOT/$artifactid2-${merchant}.war" -O $artifactid2.war

#paygate=$(curl -L http://192.168.0.91:8081/repository/maven-snapshots/$groupid/$artifactid3/4.0.0-SNAPSHOT/maven-metadata.xml | grep -m 1 \<value\> | sed -e 's/<value>\(.*\)<\/value>/\1/' | sed -e 's/ //g')

#wget --no-check-certificate "http://192.168.0.91:8081/repository/maven-snapshots/$groupid/$artifactid3/4.0.0-SNAPSHOT/$artifactid3-${paygate}.war" -O $artifactid3.war

