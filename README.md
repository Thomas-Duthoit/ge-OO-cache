# ge-OO-cache

## Sommaire

- [ge-OO-cache](#ge-oo-cache)
  - [Sommaire](#sommaire)
  - [I. Introduction](#i-introduction)
  - [II. Description](#ii-description)
    - [1. Idée générale du sujet](#1-idée-générale-du-sujet)
    - [2. Première utilisation de l'application](#2-première-utilisation-de-lapplication)
    - [3. Diagramme de classe](#3-diagramme-de-classe)

## I. Introduction

L'objectif de ce projet est de mettre en pratique les connaissances vues au cours de ces deux dernières années en POO (programmation orientée objet).

Il a pour but dé raliser un micro projet aliant une application de bureau en java avec de la gestion de données et de la persistance dans ces dernières.

Le sujet choisi a pour thème principal la gestion de géocaches grâce à une application développée en Java.


## II. Description
### 1. Idée générale du sujet

Le but est de réaliser une application permettant la gestion de réseaux de géocaches privées, en rendant l'accès aux informations relatives à ces géocaches limité à certains utilisateur par le biais de l'application ainsi que la gestion de ces réseaux de géocache par leurs propriétaires. 
La partie qui nous concerne est la partie de gestion uniquement, et elle concerne les administrateurs des réseaux de géocache.

Elle devra être capable de:

- se connecter
- créer un nouveau réseau
- créer des caches et les affecter à un réseau
- lister tous ses réseaux
- changer les informations d'une cache (par exemple par statut)
- consulter les logs des caches
- créer des utilisateurs simples et les affecter à un ou plusieurs réseaux
- partie statistiques dynamiques par rapport à l'utilisation des réseaux


L'accès à ces fonctionnalités se fait dans des vues distinctes, et sont accessibles depuis un menu à choix multiple

- drop down dans une barre en haut de l'appli pour la sélection de l'interface à afficher

### 2. Première utilisation de l'application

Afin de connecter l'application à votre base de données, il est important de modifier le fichier persistance.xml (developpement/dev_ge_OO_cache/src/main/resources/META-INF/persistence.xml) pour y indiquer les champs suivants:
- l'url pour accéder à votre base de donnée dans le champ `<property name="jakarta.persistence.jdbc.url" value="jdbc:mysql://adresse:port/table"/>`
- l'identifiant pour accéder à votre base de donnée dans le champ `<property name="jakarta.persistence.jdbc.user" value="identifiant"/>`
- Le mot de passe correspondant pour se connecter dans le champ `<property name="jakarta.persistence.jdbc.password" value="mot de passe"/>`

Pour pouvoir effectuer des tests de fonctionnalité sur l'application ainsi qu'accèder aux différentes vues (car il est nécessaire d'avoir un compte), il suffit de lancer en premier lieu la méthode main de la classe "CreerDataApplication" dans le package "data" afin d'avoir des données pour les différents vues. 
Elle créera en base de donnée un ensemble d'utilisateurs, de réseaux et de caches, ainsi que quelques logs pour ces caches.

On notera nottament la création de 3 comptes administrateurs nécessaire pour accéder à l'application:
- **Alice** 
  - identifiant : `Alice`
  - mot de passe : `1234`
- **Bob** 
  - identifiant : `Bob`
  - mot de passe : `5678`
- **Charlie** 
  - identifiant : `Charlie`
  - mot de passe : `mdp`

### 3. Diagramme de classe

Voici un diagramme UML permettant de visualiser les différentes informations sur les classes tel que leurs méthodes regroupées dans l'application : 

![Diagramme de classe partie 1](https://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/Thomas-Duthoit/ge-OO-cache/refs/heads/main/conception/UML/diagramme_classes.puml)
![Diagramme de classe partie 2](https://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/Thomas-Duthoit/ge-OO-cache/refs/heads/main/conception/UML/diagramme_classes_p2.puml)
![Diagramme de classe partie 3](https://www.plantuml.com/plantuml/proxy?cache=no&src=https://raw.githubusercontent.com/Thomas-Duthoit/ge-OO-cache/refs/heads/main/conception/UML/diagramme_classes_p3.puml)

### 4. Trello du projet 

Voici un lien permettant d'accéder au Trello du projet effectué. 
Dans ce Trello, vous retrouverez les différents diagrammes UML effectués, le lien vers les Mockups, les réunions effectuées ou les répartitions au niveau du développement.

Lien Trello : [Trello GéOOCaches](https://trello.com/b/wdWe63Ao/projet-poo-geo-caches)

Pour visualiser les diagrammes UML, il faut passer par le site de [Plant UML](https://www.plantuml.com/plantuml/uml/SyfFKj2rKt3CoKnELR1Io4ZDoSa700001) et coller le contenu du fichier `.puml` à l'intérieur
