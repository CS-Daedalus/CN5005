pipeline {
    agent any
    tools {
        maven 'Maven v3.8.3'
        jdk 'JDK v17'
    }
    stages {
        stage ('SCM') {
            steps {
                checkout scm
            }
        }

        stage ('Build') {
            steps {
                //sh 'mvn clean package'
                sh 'mvn clean test'
            }
            post {
                success {
                    junit 'target/surefire-reports/**/*.xml'
                }
            }
        }

        stage('SonarQube Analysis') {
            environment {
                def sonarScanner = tool 'SonarScanner v4.6.2'
            }

            steps {
                withSonarQubeEnv('SonarQube-Panosru') {
                  sh "${sonarScanner}/bin/sonar-scanner"
                }

                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}
