@echo off

kubectl delete -f mysql-deployment.yml
kubectl delete -f mysql-storage.yml
kubectl delete -f mysql-service.yml
timeout 5 > NUL
kubectl create -f mysql-storage.yml
timeout 5 > NUL
kubectl create -f mysql-deployment.yml
kubectl create -f mysql-service.yml
timeout 30 > NUL
