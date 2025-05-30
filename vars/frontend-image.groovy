def call(string foldername/string filename) {
  sh "docker build -t $FRONTEND_IMAGE:$IMAGE_TAG -f ${foldername}/${filename} ."
}
