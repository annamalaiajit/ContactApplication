FROM openjdk:17       
VOLUME /tmp                 
EXPOSE 8080                   
ADD target/ContactApplication-0.0.1.jar ContactApplication-0.0.1.jar
ENTRYPOINT ["java","-jar","/ContactApplication-0.0.1.jar"]              