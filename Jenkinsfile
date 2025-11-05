pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Compilando proyecto...'
                bat 'mvn -B -DskipTests clean package'
            }
        }

        stage('Test') {
            steps {
                echo 'Ejecutando tests...'
                bat 'mvn test'
            }
        }

        stage('Deploy') {
            steps {
                echo 'Desplegando aplicación...'
                bat 'java -jar target\\playlist-0.0.1-SNAPSHOT.jar'
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
        }
    }
}
