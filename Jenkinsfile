node {
    docker.withRegistry('http://192.168.0.91:8082') {
      	docker.image('maven3').inside {
          stage('test') {
            sh 'mvn --version'
            }
          stage('SCM') {
            sh 'rm -rf *'
            sh 'git clone http://prithvi.prasad:girmiti01@192.168.0.116:7990/scm/chat-clp/chatakpg.git -b develop'
            sh 'ls -lrt'
           }
          stage('Quality Analysis') {
            script {
              sh '''
              	cd chatakpg
                git branch
                cat /usr/share/maven/conf/settings.xml | grep admin
                cat /usr/share/maven/conf/settings.xml | grep url
                cd build
                mvn clean
				mvn clean install -Dmaven.test.skip=true
                mvn sonar:sonar -Dsonar.host.url=http://admin:admin@192.168.0.82:9000 -Dsonar.projectKey="cl-acquirer" -Dsonar.projectName="Closed_loop-Acquiring"
              '''
            }
          }
      	  stage('test') {
            script {
              sh '''
              	cd chatakpg/build
                mvn clean install -Dmaven.test.skip=true
                '''
            }
          }
          stage('Build') {
            script {
              sh '''
              	echo "Entered the build phases"
				echo "Build started on `date`, `pwd`, `echo $PATH`, `ls /tmp`"
			 	cd chatakpg/build
                mvn clean
                mvn clean install -Dmaven.test.skip=true
              '''
            }
          }
          stage('package') {
            script {
              sh '''
              	cd chatakpg/build
                mvn package -Dmaven.test.skip=true
                '''
            }
          }
		  stage ('Archive artifact') {
            sh 'pwd'
			echo 'I am successfull completed :)'
          	archiveArtifacts '**/*.war'
          }
          stage('Deploy-to-Nexus') {
            script {
              sh '''
              cd chatakpg
              cat /usr/share/maven/conf/settings.xml | grep admin
			  mvn deploy:deploy-file -DgroupId=chatak-acq -DartifactId=gateway-admin -Dversion=4.0.0-SNAPSHOT -DgeneratePom=true -Dpackaging=war -DrepositoryId=nexus -Durl=http://192.168.0.91:8081/repository/maven-snapshots/ -Dfile=chatak-acquirer-admin/target/gateway-admin.war
          	  mvn deploy:deploy-file -DgroupId=chatak-acq -DartifactId=gateway-merchant -Dversion=4.0.0-SNAPSHOT -DgeneratePom=true -Dpackaging=war -DrepositoryId=nexus -Durl=http://192.168.0.91:8081/repository/maven-snapshots/ -Dfile=chatak-merchant/target/gateway-merchant.war
          	  mvn deploy:deploy-file -DgroupId=chatak-acq -DartifactId=paygate -Dversion=4.0.0-SNAPSHOT -DgeneratePom=true -Dpackaging=war -DrepositoryId=nexus -Durl=http://192.168.0.91:8081/repository/maven-snapshots/ -Dfile=chatak-pay/target/paygate.war
              '''
            }
          }
        }
    }
}
