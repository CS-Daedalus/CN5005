pipeline {
    agent any
    tools {
        maven 'Maven v3.8.3'
        jdk 'JDK v8u221'
    }
    stages {
        stage ('Initialize') {
            steps {
                //sh '''
                //    echo "PATH = ${PATH}"
                //    echo "M2_HOME = ${M2_HOME}"
                //'''
            }
        }

        stage ('Build') {
            steps {
                //sh 'mvn -Dmaven.test.failure.ignore=true install'
            }
            post {
                success {
                    //junit 'target/surefire-reports/**/*.xml'
                }
            }
        }

        stage('SonarQube Analysis') {
            withSonarQubeEnv() {
              sh "${maven}/bin/mvn clean verify sonar:sonar"
            }
        }
    }
}