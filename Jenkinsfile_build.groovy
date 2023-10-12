pipeline{
    agent any
    
    parameters {
        string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')
        booleanParam(name: 'TOGGLE', defaultValue: true, description: 'Toggle this value')
        choice(name: 'CHOICE', choices: ['One', 'Two', 'Three'], description: 'Pick something')

    }
    
    
    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timeout(time: 1, unit: 'HOURS')
    }
    
    
    stages {
        stage("clone code") {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[ credentialsId: 'git-token' , url: 'https://github.com/KuruvaSomaSekhar/Sep23.git']]])
                sh "ls -lart ./*"
            }
        }
        stage("build code"){
            steps{
                sh "mvn clean package"
            }
        }
        stage("upload sep23artifacts to s3"){
            steps{
                sh "aws s3 cp target/devops-*.war s3://sep23artifacts/$JOB_NAME/"
            }
        }

        stage("Print params") {
            steps{
                sh """
                echo $PERSON 
                echo $TOGGLE
                echo $CHOICE
              """
            }
        }
    }
         
}