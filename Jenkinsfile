pipeline {
    agent any
    tools {
        maven 'Maven v3.8.3'
        jdk 'JDK v8u221'
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
                withSonarQubeEnv('SonarScanner v4.6.2') {
                  sh "${sonarScanner}/bin/sonar-scanner"
                }

                timeout(time: 10, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }
}
