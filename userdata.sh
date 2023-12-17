#!/bin/bash
sudo su
yum update -y
yum install -y git
git clone https://github.com/qmainuddin/cloud-computing-project.git
cd cloud-computing-project
chmod +x EntryPoint.sh
./EntryPoint.sh