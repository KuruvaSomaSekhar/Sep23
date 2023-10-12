pipeline{
    agent any
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
                sh "aws s3 cp target/devops-*.war s3://sep23artifacts"
            }
        }     
    }
}