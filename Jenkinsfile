pipeline {
    agent any
    environment {
        BACKEND_IMAGE = "learnersubha/w-backend"
        FRONTEND_IMAGE = "learnersubha/w-frontend"
        IMAGE_TAG = "${BUILD_NUMBER}"
        SONAR_HOME = tool "Sonar"
    }
    stages {
        stage ("code clone") {
                script {
                   clone ("https://github.com/learnersubha/Wanderless.git", "dev")
                }
        }
         stage("sonarQube: code analysis"){
                script {
                   sonar()
                 }
           
        }
        stage("OWASP dependency check") {
                script {
                   owasp()
                }
        }
        stage ("backend image build") {
                script {
                   backend-image(backend/Dockerfile)
                }
        }
        stage ("frontend image build") {
                script {
                    frontend-image (frontend/Dockerfile)
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


