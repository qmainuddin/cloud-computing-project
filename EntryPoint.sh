#!/bin/bash
exec 3>&1 4>&2
trap 'exec 2>&4 1>&3' 0 1 2 3
exec 1>log.out 2>&1

#installing dependencies..
sudo su
yum install -y java

#running backend server
cd backend
# nano /etc/profile
#Add two following line in /etc/profile
echo -e 'export JAVA_HOME=/usr/lib/jvm/jre-21\nexport PATH=$JAVA_HOME/bin:$PATH' | sudo tee -a /etc/profile
source /etc/profile
#export JAVA_HOME=/usr/lib/jvm/jre-21
#export PATH=$JAVA_HOME/bin:$PATH
# sudo cp -f ../profile /etc/

./mvnw clean install
java -jar target/auction-app-0.0.1-SNAPSHOT.jar > java-spring.log 2>&1 &




#running fronend server
# curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.39.3/install.sh | bash
# export NVM_DIR="$HOME/.nvm"
# [ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"  # This loads nvm
# [ -s "$NVM_DIR/bash_completion" ] && \. "$NVM_DIR/bash_completion"  # This loads nvm bash_completion
# . ~/.nvm/nvm.sh
# nvm install 16
# yum install -y nginx

# cd ../frontend
# npm install
# npm run build
# sudo cp -f ../nginx.conf /etc/nginx/
# service nginx start
# chkconfig nginx on