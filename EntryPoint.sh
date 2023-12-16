#!/bin/bash
exec 3>&1 4>&2
trap 'exec 2>&4 1>&3' 0 1 2 3
exec 1>log.out 2>&1

#installing dependencies..
sudo su
yum install docker
yum install java-17-openjdk
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.3/install.sh | bash
. ~/.nvm/nvm.sh
nvm install 16
npm install
yum install -y nginx

#running backend server
cd backend
sudo docker compose up
java -jar larget/auction-app-0.0.1-SNAPSHOT.jar

#running fronend server
cd ../frontend
npm run build
sudo cp -f ../nginx.conf /etc/nginx/
service nginx start
chkconfig nginx on