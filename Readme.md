local development setup:

kubernetes minikube
kafka on kubernetes
mysql on kubernetes

1. setup kafka:
    load zookeeper deployment and service
    load kafka deployment and service
2. setup mysql
    load mysql storage config (1GB disc space)
    load mysql server
    
3. kafka should be ready at localhost:9092
4. to work with mysql make a port-forward "kubectl port-forward name-of-the-pod 3306" - naw it available at localhost (root password)

Example env variables are in env file or env-mail

KAFKA_ADDRESS=localhost:9092
KAFKA_TOPIC_NAME=mail-topic
MYSQL_ADDRESS=jdbc:mysql://localhost:3306/subscriptions
MYSQL_ACCOUNT_USERNAME=root
MYSQL_ACCOUNT_PASSWORD=password

now you this microservice will use mysql and kafka thru localhost address,
and you can run, this locally by starting the main method or by maven
mvnw spring-boot:run should start the service

it expects REST at:

POST http://localhost:8080/subscription
{
	"email":"cwichula@gmail.com",
	"dateOfBith":"30/08/2018",
	"newsletterId":"someKampaign",
	"isConsent":"true"
}

Security:

only this endpoint will be visible on the kubernetes, res will be behind internal network so it should be secure enought.

Toimprove security this should by https, and kafka, mysql also should use ssl

Kubernetes general test:

on master branch the yml configurations are tuned up for kubernetes internal usage,

example email account from gmail (500 mails/day)