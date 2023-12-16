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