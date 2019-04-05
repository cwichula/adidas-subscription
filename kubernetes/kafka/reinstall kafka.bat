@echo off


kubectl delete -f kafka-service.yml
kubectl delete -f kafka-cluster.yml
kubectl delete -f zookeeper.yml
timeout 20 > NUL
kubectl create -f zookeeper.yml
timeout 10 > NUL
kubectl create -f kafka-cluster.yml
kubectl create -f kafka-service.yml


pause
