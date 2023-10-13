pipeline {
    agent any
     parameters {
        string(name: 'serverIPs', defaultValue: '', description: 'provide server ips?')
     }
    
    stages{
        stage("list"){
            steps{
                sh "chmod +x tomcatinstall.sh"
                sh "ls -lat"
            }
        }
        stage("scp"){
            steps {
                sh """
                        IFS=, read -ra values <<< "$serverIPs"
                            for ip in "${values[@]}"
                            do
                            # things with "$v"
                            echo $ip
                            echo "use scp command to copy artifact"
                            scp  -o StrictHostKeyChecking=no -i /tmp/sep2023.pem tomcatinstall.sh ec2-user@$ip:/tmp/
                            ssh -o StrictHostKeyChecking=no -i /tmp/sep2023.pem ec2-user@$ip "bash /tmp/tomcatinstall.sh"
                            done
                    
                    """
            }
        }
    }
}