#!/bin/bash
exec 3>&1 4>&2
trap 'exec 2>&4 1>&3' 0 1 2 3
exec 1>log.out 2>&1

#installing dependencies..
sudo su
yum install -y docker
curl -SL https://github.com/docker/compose/releases/download/v2.23.3/docker-compose-linux-x86_64 -o /usr/local/bin/docker-compose
ln -s /usr/local/bin/docker-compose /usr/bin/docker-compose
chmod +x /usr/bin/docker-compose 
service docker start
yum install -y java
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.3/install.sh | bash
. ~/.nvm/nvm.sh
nvm install 16
yum install -y nginx

#running backend server
cd backend
sudo docker-compose up -d
nohup java -jar target/auction-app-0.0.1-SNAPSHOT.jar > java-spring.log 2>&1 &

#running fronend server
cd ../frontend
npm install
npm run build
sudo cp -f ../nginx.conf /etc/nginx/
service nginx start
chkconfig nginx on