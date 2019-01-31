#/bin/bash
#versionTimestamped=wget -q -O- --no-check-certificate "http://192.168.0.91:8081/repository/maven-snapshots/RKI-TMS/chatak-tms/4.0.0-SNAPSHOT/maven-metadata.xml" | grep -m 1 \<value\> | sed -e 's/<value>\(.*\)<\/value>/\1/' | sed -e 's/ //g'
#echo $test

admin=$(curl -L http://192.168.0.91:8081/repository/maven-snapshots/chatak-acq/gateway-admin/4.0.0-SNAPSHOT/maven-metadata.xml | grep -m 1 \<value\> | sed -e 's/<value>\(.*\)<\/value>/\1/' | sed -e 's/ //g')

merchant=$(curl -L http://192.168.0.91:8081/repository/maven-snapshots/chatak-acq/gateway-merchant/4.0.0-SNAPSHOT/maven-metadata.xml | grep -m 1 \<value\> | sed -e 's/<value>\(.*\)<\/value>/\1/' | sed -e 's/ //g')

paygate=$(curl -L http://192.168.0.91:8081/repository/maven-snapshots/chatak-acq/paygate/4.0.0-SNAPSHOT/maven-metadata.xml | grep -m 1 \<value\> | sed -e 's/<value>\(.*\)<\/value>/\1/' | sed -e 's/ //g')

echo $test

wget --no-check-certificate "http://192.168.0.91:8081/repository/maven-snapshots/chatak-acq/gateway-admin/4.0.0-SNAPSHOT/gateway-admin-${admin}.war" -O gateway-admin.war

wget --no-check-certificate "http://192.168.0.91:8081/repository/maven-snapshots/chatak-acq/gateway-merchant/4.0.0-SNAPSHOT/gateway-merchant-${merchant}.war" -O gateway-merchant.war

wget --no-check-certificate "http://192.168.0.91:8081/repository/maven-snapshots/chatak-acq/paygate/4.0.0-SNAPSHOT/paygate-${paygate}.war" -O paygate.war



