@echo off

call mvnw clean package dockerfile:build -DskipTests

kubectl delete -f kubernetes\subscription.yml
kubectl create -f kubernetes\subscription.yml

pause