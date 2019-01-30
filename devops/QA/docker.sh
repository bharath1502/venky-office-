echo "Building the Docker IMAGE"
cd ../../build
version=`cat pom.xml | grep -m 1 \<version\> | sed -e 's/<version>\(.*\)<\/version>/\1/'`
echo $version
cd ../devops/QA
#gateway
docker tag gateway gateway:$version
docker tag gateway:$version 192.168.2.171:8083/gateway:$version
docker push 192.168.2.171:8083/gateway:$version
#paygate
#docker tag paygate:$version 192.168.2.171:8083/paygate:$version
#docker push 192.168.2.171:8083/paygate:$version
