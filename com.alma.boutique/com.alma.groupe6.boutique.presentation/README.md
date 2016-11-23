Projet Services : Couche de présentation
========================================

Couche de présentation pour le projet de Service - M2 ALMA 2016/2017

Pour compiler et créer l'archive **war** :
```
mvn package
```

Pour déployer le **war** dans un container Tomcat en utilisant web-runner :
```
java -jar target/dependency/webapp-runner.jar target/com.alma.boutique.presentation-1.0-SNAPSHOT.war
```