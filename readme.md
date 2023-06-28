# WebProgramming - Shortener

This application has written in Java-17 and using Spring IOC container \
and maven as build tool. 

## Requirements

- Java 17
- Maven 3.6.*
- Oracle-11, Mysql , H2
- Docker

## Installation

To build this project you have to use maven. Thus run this at root directory of project

```
mvn clean package 
```

To make docker file of this module first build the project then change your working directory parent and run

```
docker build --tag <your-docker-image-tag-name> .
```

Then run the container by

```
docker run --name test-container -d -p <port>:8081 <your-docker-image-tag-name>
```

Or run project manually by

```
java -jar target/*.jar
```

```
open http://localhost:8081/shortener
```

## Usage

```
>> To change application setting you can modify application.properties file.
  >>> By default application starts at port 8081 to change this use server.port property.
  >>> By default appliaction uses oracle database and at first you need to migrate your database by sql files included in project.
  >>> To specify database url,password,username check application.properties (use JDBC formate for URI).
  >>> To customize buisiness properties see also application.properties.  
>> Also to make dynamic configuration you can mount your application.properties to /app/config/
```

## Tip

To run oracle database on docker use

```
docker run -d -p 1521:1521 -e ORACLE_ALLOW_REMOTE=true oracleinanutshell/oracle-xe-11g
```

then by using following command to find database oracle bridge-network ip

```
docker inspect <container_id>
```


