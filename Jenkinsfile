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
                  sh "mvn clean verify ${SONAR_MAVEN_GOAL} -Dsonar.host.url=${SONAR_HOST_URL} -Dmaven.exec.skip=true -Dmaven.jar.skip=true -Dmaven.dependency.skip=true -Dsonar.login=${SONAR_AUTH_TOKEN}"
                }
            }
        }
    }
}
