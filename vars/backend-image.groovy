def call(string foldername/string filename) {
 sh "docker build -t $BACKEND_IMAGE:$IMAGE_TAG -f ${foldername}/${filename} ."
}
