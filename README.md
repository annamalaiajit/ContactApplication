**Contact Application::**

Before you start the application you need to setup the postgres and kafka in your local.

create database contactApp;

id  integer
name varchar

Kafka configuration in your local.

start the zookeeper ==> **.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
**
kafka start ==>localtion and run the command **.\bin\windows\kafka-server-start.bat .\config\server.properties**


create topic ==> **.\kafka-topics.bat --create --topic contact --bootstrap-server localhost:9092**

producer start==>
**.\kafka-console-producer.bat --broker-list localhost:9092 --topic contact
Hello World contact created**

Just for checking message are received
**.\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic contact --from-beginning**

Above all the steps are completed you can start simply clone and run your application.
SpringBoot run::

**mvn spring-boot:run**
