pipeline {
    agent any
     parameters {
        string(name: 'serverIPs', defaultValue: '172.31.30.245,172.31.22.240,172.31.31.114', description: 'provide server ips?')
        string(name: 'buildNumber', defaultValue: '32', description: 'provide server ips?')
        string(name: 'branchName', defaultValue: 'master', description: 'provide server ips?')

     }
    
    stages{
        stage("list"){
            steps{
                sh "aws s3 sync s3://sep23artifacts/MyPipelineJobs/buildPipeline/${params.branchName}/${params.buildNumber}/ ."
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
}