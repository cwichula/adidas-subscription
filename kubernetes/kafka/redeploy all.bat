@echo off


kubectl delete -f kafka-cluster.yml
kubectl delete -f zookeeper.yml
timeout 30 > NUL
kubectl create -f zookeeper.yml
kubectl create -f kafka-cluster.yml


pause
