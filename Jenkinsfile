pipeline {
    agent any
    tools {
        maven 'Maven v3.8.3'
        jdk 'JDK v8u221'
        sonarscanner 'SonarScanner v4.6.2'
    }
    stages {
        stage ('SCM') {
            steps {
                checkout scm
            }
        }

        stage ('Build') {
            steps {
                sh 'mvn clean package'
            }
            post {
                success {
                    junit 'target/surefire-reports/**/*.xml'
                }
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv() {
                  sh "${sonarscanner}/bin/sonar-scanner"
                }

                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}
