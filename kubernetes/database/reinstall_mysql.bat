@echo off

kubectl delete -f mysql-service.yml
timeout 5 > NUL
kubectl delete -f mysql-deployment.yml
timeout 5 > NUL
kubectl delete -f mysql-storage.yml
timeout 5 > NUL
kubectl apply -f mysql-storage.yml
timeout 5 > NUL
kubectl apply -f mysql-deployment.yml
timeout 5 > NUL
kubectl apply -f mysql-service.yml
timeout 10 > NUL
