def call() {
 sh "docker build -t $BACKEND_IMAGE:$IMAGE_TAG -f backend/Dockerfile ."
}
