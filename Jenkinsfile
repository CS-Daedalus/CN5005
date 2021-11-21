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
        stage ('Run tests') {

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
            steps {
                withSonarQubeEnv('SonarQube-Panosru') {
                  sh "mvn clean verify sonar:sonar -Dsonar.host.url=${SQ_URL} -Dmaven.exec.skip=true -Dmaven.jar.skip=true -Dmaven.dependency.skip=true -Dsonar.login=${SQ_TOKEN}"
                }
            }
        }
    }
}
