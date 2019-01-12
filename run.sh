#! /bin/sh

if [ "$1" == "compile" ];then
mvn clean package
fi
java -jar target/nukkit-1.0-SNAPSHOT.jar