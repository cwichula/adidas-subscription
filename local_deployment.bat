@echo off

call mvnw clean package dockerfile:build -DskipTests

kubectl delete -f kubernetes\ceh.yml
kubectl create -f kubernetes\ceh.yml

pause