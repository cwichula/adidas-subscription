@echo off

call mvnw clean package dockerfile:build -DskipTests

kubectl delete -f kubernetes\subscription-service.yml
timeout 5 > NUL
kubectl delete -f kubernetes\subscription.yml
timeout 5 > NUL
kubectl apply -f kubernetes\subscription.yml
timeout 5 > NUL
kubectl apply -f kubernetes\subscription-service.yml
timeout 5 > NUL

