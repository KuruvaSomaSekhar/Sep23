//declarative pipeline
//delcarative piplien and scripted pipeline
pipeline {
    agent any 
    stages {
        stage("A") {
            steps {
                println("We are in stage A")
            }
        }

        stage("B") {
            steps {
                println("We are in stage B")
            }

        }
        stage("C") {
        steps {
                println("We are in stage C")
            }

        }
    }
}