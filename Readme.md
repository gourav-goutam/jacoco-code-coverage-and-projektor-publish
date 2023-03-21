# Jacoco code coverage & Projektor publish

> **_Tools version used in my project during creation_**
>__________
> Java version `17` <br/><br/>
> Gradle version `7.6.1` <br/><br/>
> Spring Boot version `3.0.3` <br/><br/>

<br/><br/>

> **_Command to run for projektor publish_**
>____________________
* `./gradlew clean build` 
* `./gradlew publishResults`
* Command ran later will provide the web url to access the published report

<br/><br/>

> **_Steps to setup in local ant test via docker containers_**
>____________________
* Clone this repo in local
* Download the projektor jar from `https://github.com/craigatk/projektor/releases`
* Build docker image using command `docker build -t projektor-server .` otherwise directly run the jar
* Update your project's `build.gradle.kts` file by referring `build.gradle.kts` in this repo
* Then use `gradlew` commands 

Thanks !