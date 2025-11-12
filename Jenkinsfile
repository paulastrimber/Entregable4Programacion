pipeline {
    agent any

    environment {
        DEV_DIR = "C:\\Users\\Paula\\Desktop\\playlist-dev"
        PROD_DIR = "C:\\Users\\Paula\\Desktop\\playlist-prod"
    }

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

        stage('Deploy Dev') {
            steps {
                echo 'Desplegando en entorno de desarrollo (puerto 8081)...'
                bat """
                    mkdir "${env.DEV_DIR}" 2>nul
                    copy target\\playlist-0.0.1-SNAPSHOT.jar "${env.DEV_DIR}\\"
                    cd "${env.DEV_DIR}"
                    java -jar playlist-0.0.1-SNAPSHOT.jar --server.port=8081
                """
            }
        }

        stage('Deploy Prod') {
            steps {
                echo 'Desplegando en entorno de producción (puerto 9090)...'
                bat """
                    mkdir "${env.PROD_DIR}" 2>nul
                    copy target\\playlist-0.0.1-SNAPSHOT.jar "${env.PROD_DIR}\\"
                    cd "${env.PROD_DIR}"
                    java -jar playlist-0.0.1-SNAPSHOT.jar --server.port=9090
                """
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
