pipeline {
    agent {label  "prod"}
    environment {
        BACKEND_IMAGE = "learnersubha/w-backend"
        FRONTEND_IMAGE = "learnersubha/w-frontend"
        IMAGE_TAG = "${BUILD_NUMBER}"
        SONAR_HOME = tool "Sonar"
    }
    stages {
        stage ("code clone") {
            steps {
                git url: "https://github.com/learnersubha/Wanderless.git", branch: "dev"
            }
        }
         stage("sonarQube: code analysis"){
            steps {
                 withSonarQubeEnv("Sonar") {
                     sh "$SONAR_HOME/bin/sonar-scanner -Dsonar.projectName=easyapp -Dsonar.projectKey=easyapp -X"
                 }
            }
           
        }
        stage("OWASP dependency check") {
            steps {
                 dependencyCheck additionalArguments: '--scan ./', odcInstallation: 'OWASP'
                 dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
            }
        }
        stage ("backend image build") {
            steps {
                sh "docker build -t $BACKEND_IMAGE:$IMAGE_TAG -f backend/Dockerfile ."
            }
        }
        stage ("frontend image build") {
            steps {
                sh "docker build -t $FRONTEND_IMAGE:$IMAGE_TAG -f frontend/Dockerfile ."
            }
        }
        stage ("image push") {
            steps {
                withCredentials([usernamePassword(
                    credentialsId:"Dockercredentials",
                    passwordVariable: "dockerhubpass",
                    usernameVariable: "dockerhubuser"
                )]){
                sh "docker login -u ${dockerhubuser} -p ${dockerhubpass}"
                sh "docker push $BACKEND_IMAGE:$IMAGE_TAG"
                sh "docker push $FRONTEND_IMAGE:$IMAGE_TAG"
                }
            }
        }
        stage ("code deploy") {
            steps {
                sh "docker-compose up -d"
            }
        }
    }
    post {
        success {
            emailext (
                from: 'subha.devops4084@gmail.com',
                to: 'subha.devops4084@gmail.com',
                subject: 'Build Successful',
                body: 'Your build was successful.'
            )
        }
        failure {
            emailext (
                from: 'subha.devops4084@gmail.com',
                to: 'subha.devops4084@gmail.com',
                subject: 'Build Failure',
                body: 'Your build was Failure.'
            )
        }
    }
}
