def call() {
    withSonarQubeEnv("Sonar") {
                     sh "$SONAR_HOME/bin/sonar-scanner -Dsonar.projectName=easyapp -Dsonar.projectKey=easyapp -X"
}
