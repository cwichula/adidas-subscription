This is example software made in microservices architecture. 

Used techs:

Kubernetes - to connect and maintaind different software's, for ensuring 99.99% uptime, load balancing and whole cloud ecosystem. 

Docker - to create OS independent environment for software to work.

Java8 - current enterprise standard.

Apache Kafka - is very scalable queue/streaming platform, using kafka for only 2 microservices maybe overkill byt taking look at feature a small project can become a monster... and kafka will handle that beast.

Spring Boot - its lightweight version of popular spring framework, with some auto-configuration utilities. Its easy to bootstrap a fresh micro-app with this.MySQL - responsible for storing incoming data, generating unique id for each entry. Could scale in cloud, I choose this because i dont know other databases

Spring Security Module - easy to integrate module for simple and more advanced authentication mechanism

Spring Data JPA - easy to start with basic database query's, pre-made CRUD and other useful methods

Lombok - very useful 3rd party java plugin for reducing Java boilerplate code, make source code more readable

Swagger - automatic documentation for the REST API

The communication flow:

1. 

Request HTTP POST for http://localhost:8081/subscription
Body:

{
	"email":"your@email.address",
	"dateOfBith":"30/08/2018",
	"newsletterId":"someKampaign",
	"isConsent":"true"
}

Response: 
{
    "newsletterId": "123"
}

or empty if authentication credential are invalid

2. Microservice that handle this Api will save this to database returns generated id and send it to the apache kafka topic.
3. Mail microservice listen to the events on Apache Kafka topic and consume them by sending an email message to given email address.
4. User receive on his mail the confirmation of the subscription to the newsletter

How to run this?

1. pre-requirements:
- Kubernetes is installed locally
- Docker is installed locally (localhost:2375 is exposed, see docker settings)
- kubectl is connected to the cluster
- %JAVA_HOME% environment variable is pointing to java installation
- connection with insternet (to the docker registry)
- git installed

2. Automatic installation on windows:
cmd:
mkdir subscription
git clone https://github.com/cwichula/adidas-subscription-challange.git
git clone https://github.com/cwichula/adidas-subscription
cd adidas-subscription
reinstall_all.bat

or Manual installation:

Run maven in each git clone repository, it will build the project and create docker image locally:

mvnw clean package dockerfile:build -DskipTests

kubectl apply -f kubernetes\kafka\zookeeper.yml
kubectl apply -f kubernetes\kafka\zookeeper-service.yml
kubectl apply -f kubernetes\kafka\kafka-service.yml
kubectl apply -f kubernetes\kafka\kafka-cluster.yml

kubectl apply -f kubernetes\database\mysql-storage.yml
kubectl apply -f kubernetes\database\mysql-deployment.yml
kubectl apply -f kubernetes\database\mysql-service.yml

kubectl apply -f kubernetes\subscription.yml
kubectl apply -f kubernetes\subscription-service.yml


kubectl apply -f ..\..\adidas-subscription-challange\kubernetes\mail-service.yml
kubectl apply -f ..\..\adidas-subscription-challange\kubernetes\mail.yml

Enviroment variables used by subscription-deployment:

KAFKA_ADDRESS=localhost:9092
KAFKA_TOPIC_NAME=mail-topic
MYSQL_ADDRESS=jdbc:mysql://localhost:3306/subscriptions
MYSQL_ACCOUNT_USERNAME=root
MYSQL_ACCOUNT_PASSWORD=password

Enviroment variables used by subscription-deployment:

MAIL_SMTP_PORT=587
MAIL_SMTP_HOST=smtp.gmail.com
MAIL_ACCOUNT_USERNAME=no.replay.subscription@gmail.com
MAIL_ACCOUNT_PASSWORD=pvhbnkdyhowfmiks
MAIL_TRANSPORT_PROTOCOL=smtp
MAIL_SMTP_AUTH_ENABLED=true
MAIL_SMTP_STARTTLS_ENABLED=true
MAIL_DEBUG_ENABLED=true
MAIL_MESSAGE_TOPIC=New Newsletter!
MAIL_MESSAGE_DESCRIPTION=Congratulations! You just subscribed to Adidas newsletter!
KAFKA_ADDRESS=localhost:9092
KAFKA_TOPIC_NAME=mail-topic
KAFKA_LISTENER_GROUP_ID=mail-group-id

Security:
Only this endpoint (/subscription) will be visible on the public network, rest will be behind internal kubernetes network so it is considered to be safe.

To improve security this endpoint should be https, and kafka, mysql also should use ssl encryption if possible

Kubernetes general end-to-end test:

1. send POST HTTP http://localhost:8081/subscription
with body: 

{
	"email":"your@email.address",
	"dateOfBith":"30/08/2018",
	"newsletterId":"someKampaign",
	"isConsent":"true"
}

Expected response:
{
    "newsletterId":<NUMBER HERE>
}

Now check your mailbox, (or spam directory): there should be email 
from no.replay.subscription@gmail.com
title: New Newsletter!
message: Congratulations! You just subscribed to Adidas newsletter!

The massage is example, it could be full HTML website.

LIMITATIONS: 

Google email server can send only 500 mails/day