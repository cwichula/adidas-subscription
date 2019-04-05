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
-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.6.43 - MySQL Community Server (GPL)
-- Server OS:                    Linux
-- HeidiSQL Version:             10.1.0.5464
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for subscriptions
DROP DATABASE IF EXISTS `subscriptions`;
CREATE DATABASE IF NOT EXISTS `subscriptions` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `subscriptions`;

-- Dumping structure for table subscriptions.subscriptions
DROP TABLE IF EXISTS `subscriptions`;
CREATE TABLE IF NOT EXISTS `subscriptions` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(50) NOT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `gender` varchar(50) DEFAULT NULL,
  `date_of_bith` date NOT NULL,
  `newsletter_id` varchar(50) NOT NULL,
  `is_consent` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=343 DEFAULT CHARSET=latin1;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;


    
3. kafka should be ready at localhost:9092
4. to work with mysql make a port-forward "kubectl port-forward name-of-the-pod 3306" - naw it available at localhost (root password)
5. build docker image with maven
6. load deployment subscription.yml to kubernetes
7. load subscription-service.yml


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

API: 
POST http://localhost:8081/subscription
on master branch the yml configurations are tuned up for kubernetes internal usage,

example email account from gmail (500 mails/day)

mvn clean install dockerfile:build - to build docker image (needed to upload to kubernetes)


also if you have kubernetes on localhost you can use bat file local_deployment.bat