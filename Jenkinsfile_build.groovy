pipeline{
    agent any
    
    parameters {
        string(name: 'PERSON', defaultValue: 'Mr Jenkins', description: 'Who should I say hello to?')
        booleanParam(name: 'TOGGLE', defaultValue: true, description: 'Toggle this value')
        choice(name: 'CHOICE', choices: ['One', 'Two', 'Three'], description: 'Pick something')

        string(name: 'source_Branch', defaultValue: 'master', description: 'Who should I say hello to?')

    }
    
    
    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        timeout(time: 1, unit: 'HOURS')
    }
    
    
    stages {
        stage("clone code") {
            steps {
                checkout([$class: 'GitSCM', branches: [[name: "${env.source_Branch}"]], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[ credentialsId: 'git-token' , url: 'https://github.com/KuruvaSomaSekhar/Sep23.git']]])
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
                sh "aws s3 cp target/devops-*.war s3://sep23artifacts/$JOB_NAME/${params.source_Branch}/${BUILD_NUMBER}/"
            }
        }

        stage("Print params") {
            steps{
                sh """
                echo "Hello ${params.PERSON}"
                echo "Toggle: ${params.TOGGLE}"
                echo "Choice: ${params.CHOICE}"

              """
            }
        }
    }
         
}