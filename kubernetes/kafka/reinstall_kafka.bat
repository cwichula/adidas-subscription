@echo off


kubectl delete -f kafka-service.yml
timeout 5 > NUL
kubectl delete -f kafka-cluster.yml
timeout 5 > NUL
kubectl delete -f zookeeper-service.yml
timeout 5 > NUL
kubectl delete -f zookeeper.yml
timeout 5 > NUL
kubectl apply -f zookeeper.yml
timeout 5 > NUL
kubectl apply -f zookeeper-service.yml
timeout 5 > NUL
kubectl apply -f kafka-service.yml
timeout 5 > NUL
kubectl apply -f kafka-cluster.yml
timeout 5 > NUL



