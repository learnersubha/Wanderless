pipeline {
    agent any
    environment {
        BACKEND_IMAGE = "learnersubha/w-backend"
        FRONTEND_IMAGE = "learnersubha/w-frontend"
        IMAGE_TAG = "${BUILD_NUMBER}"
    }
    stages {
        stage ("code clone") {
            steps {
                git url: "https://github.com/learnersubha/Wanderless.git", branch: "dev"
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
}

post {
    success {
        script {
            emailtxt from: 'learnersubha0@gmail.com',
            to: 'learnersubha0@gmail.com',
            body: 'your build was successful',
            subject: 'build successful'
        }
    }
}    
