# Spécifications fonctionnelles



## Sommaire
- [Spécifications fonctionnelles](#spécifications-fonctionnelles)
  - [Sommaire](#sommaire)
  - [I. Interface graphique](#i-interface-graphique)
    - [1. Vue de connexion](#1-vue-de-connexion)
    - [2. Vue d'accueil](#2-vue-daccueil)
    - [3. Liste des vues accessibles depuis le menu déroulant](#3-liste-des-vues-accessibles-depuis-le-menu-déroulant)
      - [3.a. Affichage réseaux propriétaire](#3a-affichage-réseaux-propriétaire)
      - [3.b. Affichage caches de réseau](#3b-affichage-caches-de-réseau)
      - [3.c. Création de réseau](#3c-création-de-réseau)
      - [3.d. Création de cache](#3d-création-de-cache)
      - [3.e. Modification de statut de cache](#3e-modification-de-statut-de-cache)
      - [3.f. Création d'un utilisateur](#3f-création-dun-utilisateur)
      - [3.g. Association utilisateur à réseau](#3g-association-utilisateur-à-réseau)
      - [3.h. Logging](#3h-logging)
      - [3.i. Statistiques](#3i-statistiques)




## I. Interface graphique
L'interface graphique sera découpée en plusieurs vues qui sont les suivantes:
### 1. Vue de connexion
Affichage d'un formulaire de connexion demandant la saisir du login et du mot de passe. 

Si la connexion est réussie, afficher la [vue d'accueil](#2-vue-daccueil), sinon mettre fin à l'application.

### 2. Vue d'accueil

Vue vide dans laquelle l'utilisateur arrive une  fois connecté pourra choisir l'interface de son choix à l'aide d'un menu déroulant sur la barre d'outils de l'application

### 3. Liste des vues accessibles depuis le menu déroulant

Les vues suivantes seront accessibles depuis le menu déroulant présent dans la barre d'outils de l'application

#### 3.a. Affichage réseaux propriétaire

Listage dess réseaux de géocaches qui appartiennent à l'administrateur connecté

#### 3.b. Affichage caches de réseau

Listage des cache d'un réseau appartenant à l'utilisateur

#### 3.c. Création de réseau

L'administrateur peut créer un réseau de caches vide

#### 3.d. Création de cache

L'administrateur peut créer un cache dans un réseau de caches qui lui appartient

#### 3.e. Modification de statut de cache

Possibilité pour un administrateur de modifier le statut des caches qui lui appartienne (aussi appelé état) : `activée`, `en cours d'activation`, `fermée`, `suspendue`.

#### 3.f. Création d'un utilisateur

Possibilité pour un administrateur de créer un compte utilisateur de base.

#### 3.g. Association utilisateur à réseau

Attribuer à un utilistateur l'accès à un réseau de géocache.

#### 3.h. Logging

Logging d'une viste de cache

#### 3.i. Statistiques

Interface qui permet à l'administrateur de visualiser ldes statistiques d'utilisation de ses réseaux de façon dynamique.