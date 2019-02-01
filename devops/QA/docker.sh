echo "Pushing the Docker IMAGE"
#gateway
docker tag $image:${BUILD_NUMBER} $docker-registry/$image:${BUILD_NUMBER}
docker push $docker-registry/$image:${BUILD_NUMBER}
#paygate
#docker tag paygate:$version 192.168.2.171:8083/paygate:$version
#docker push 192.168.2.171:8083/paygate:$version
