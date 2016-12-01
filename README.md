# Projet de Service - Master 2 ALMA 2016/2017 [![Build Status](https://travis-ci.org/XyLnEn/ServiceBoutique.svg?branch=master)](https://travis-ci.org/XyLnEn/ServiceBoutique)

Projet de gestion de Boutique, réalisé dans le cadre du module Services, enseigné en Master 2 ALMA.    
Ce projet a été réalisé en suivante le principe de [Domain Driven Development](https://en.wikipedia.org/wiki/Domain-driven_design).   
**Auteurs** : Lenny Lucas et Thomas Minier, groupe 6

# Structure

Le projet est divisé en deux sous projets :
* **com.alma.boutique** : l'application de gestion de boutique, réalisée suivant le principe de DDD.
* **com.alma.creditcard** : un webservice basique de validation de carte bleue, implémenté selon le standard SOAP. Il est déployé sous la forme d'une rchive **war**.

L'application de gestion de boutique est elle-même divisé en deux sous-applications :
* **com.alma.groupe6.boutique.application** : les couches api, domaine, infrastrucutre et application de la boutique, qui expose ses services sous la forme d'une API JSON. Elle est déployée sous la forme d'une archive jar qui fait aussi office de serveur.
* **com.alma.groupe6.boutique.presentation** : la couche présentation de la boutique, qui interagit avec l'API de la couche application. Il s'agit d'une webapp [Vue.js](https://vuejs.org/), déployée sous la forme d'une archive **war**.

# Installation et déploiement

Pour compiler **com.alma.creditcard** :
```bash
cd com.alma.creditcard
mvn package
```

Pour déployer le webservice SOAP dans un container Tomcat sur le port 8080 :
```
java -jar target/dependency/webapp-runner.jar target/com.alma.groupe6.creditcard-1.0-SNAPSHOT.war --port 8080
```

Pour compiler **com.alma.boutique** :
```bash
cd com.alma.boutique
mvn package
```
Pour lancer le serveur de la couche application et deployer l'API JSON sur le port 4567 :
```
cd com.alma.groupe6.boutique.application
java -jar target/com.alma.boutique.application-1.0-SNAPSHOT-jar-with-dependencies.jar
```

Pour déployer la présentation dans un container Tomcat en utilisant web-runner sur le port 8081 :
```
cd com.alma.groupe6.boutique.presentation
java -jar target/dependency/webapp-runner.jar target/com.alma.boutique.presentation-1.0-SNAPSHOT.war --port 8081
```
