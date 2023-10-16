#!/bin/bash
sudo yum install java-1.8.0-openjdk-devel -y 
sudo yum install tomcat -y 
sudo yum install tomcat-webapps tomcat-admin-webapps -y
sudo yum install tomcat-docs-webapp tomcat-javadoc -y 
sudo systemctl start tomcat
sudo systemctl enable tomcat
sudo systemctl status tomcat
sudo chmod 777 /var/lib/tomcat/webapps