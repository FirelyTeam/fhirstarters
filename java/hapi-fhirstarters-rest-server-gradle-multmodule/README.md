######   QuickExampleServerApplicationEntry

Java IDE Setup.

Open/Import as a gradle/spring-boot application.

Set the start up class to 

    package ca.uhn.example.gradleandspringbootexample.toplayers.springboottoplayers.quickexampleserver;
        public class QuickExampleServerApplicationEntry

Run without a Java IDE (aka, withut IntelliJ, Eclipse, NetBeans, etc)

    ./gradlew clean build

    ./gradlew :source:javalayers:toplayers:springboottoplayers:quickexampleserver:bootRun
        
        
