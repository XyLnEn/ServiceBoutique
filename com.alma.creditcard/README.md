Projet Services : Webservice SOAP pour la validation de cartes bleues
=====================================================================

Webservice SOAP pour la validation de cartes bleues, créée pour le projet de Service - M2 ALMA 2016/2017

Pour compiler et créer l'archive **war** :
```
mvn package
```

Pour déployer le **war** dans un container Tomcat en utilisant web-runner sur le port 8080 :
```
java -jar target/dependency/webapp-runner.jar target/com.alma.groupe6.creditcard-1.0-SNAPSHOT.war --port 8080
```
