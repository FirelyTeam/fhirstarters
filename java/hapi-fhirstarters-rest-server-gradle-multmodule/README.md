######   QuickExampleServerApplicationEntry


This is a 

Spring-Boot
and
Gradle (multi module ("layers" in software development general terms))

Starter-Kit.

..........

Java IDE Setup.

Open/Import as a gradle/spring-boot application.

Set the start up class to 

    package ca.uhn.example.gradleandspringbootexample.toplayers.springboottoplayers.quickexampleserver;
        public class QuickExampleServerApplicationEntry

..........

Run without a Java IDE (aka, without IntelliJ, Eclipse, NetBeans, etc)

    ./gradlew clean build

    ./gradlew :source:javalayers:toplayers:springboottoplayers:quickexampleserver:bootRun
        
        
Example Http-Requests.

(All are GET unless otherwise noted)

    http://127.0.0.1:8083/actuator/health
    
    http://127.0.0.1:8083/myr4fhirserver/metadata
    
    http://127.0.0.1:8083/myr4fhirserver/Patient?_count=13
    
    http://127.0.0.1:8083/myr4fhirserver/Patient?family=Smith&_count=30
    
    http://127.0.0.1:8083/myr4fhirserver/Patient?given=Mary&_count=30
    
    http://127.0.0.1:8083/myr4fhirserver/Patient?birthdate=ge2022-03-22&_count=30
