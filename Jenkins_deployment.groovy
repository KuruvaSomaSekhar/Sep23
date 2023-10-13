pipeline {
    agent any
     parameters {
        string(name: 'serverIPs', defaultValue: '', description: 'provide server ips?')
        string(name: 'buildNumber', defaultValue: '', description: 'provide server ips?')
        string(name: 'branchName', defaultValue: '', description: 'provide server ips?')

     }
    
    stages{
        stage("list"){
            steps{
                sh "aws s3 cp s3://sep23artifacts/MyPipelineJobs/buildPipeline/${params.branchName}/${params.buildNumber}/* ."
                sh "ls -lat"
            }
        }
        stage("scp"){
            steps {
                sh '''
                        IFS=, read -ra values <<< "$serverIPs"
                            for ip in "${values[@]}"
                            do
                            # things with "$v"
                            echo $ip
                            echo "use scp command to copy artifact"
                            scp  -o StrictHostKeyChecking=no -i /tmp/sep2023.pem devops-*.war ec2-user@$ip:/var/lib/tomcat/webapps/
                            done
                    
                    '''
            }
        }
    }