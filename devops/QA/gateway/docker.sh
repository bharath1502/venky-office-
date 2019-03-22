docker tag $image:${BUILD_NUMBER} 192.168.0.91:8083/$image:${BUILD_NUMBER}
docker login 192.168.0.91:8083 -u admin -p admin123
docker push 192.168.0.91:8083/$image:${BUILD_NUMBER}
