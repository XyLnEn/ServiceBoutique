#!/usr/bin/env bash

# deploy credit card validation service
cd com.alma.creditcard/
java -jar target/dependency/webapp-runner.jar target/com.alma.groupe6.creditcard-1.0-SNAPSHOT.war --port 8080

# deploy shop application
cd ../com.alma.boutique/
cd com.alma.groupe6.boutique.application/
java -jar target/com.alma.boutique.application-1.0-SNAPSHOT-jar-with-dependencies.jar 

# deploy shop presentation
cd ../com.alma.groupe6.boutique.presentation/
java -jar target/dependency/webapp-runner.jar target/com.alma.boutique.presentation-1.0-SNAPSHOT.war --port 8081

