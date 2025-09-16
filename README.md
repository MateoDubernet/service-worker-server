# Application Serveur – Notifications Push

Ce serveur Spring Boot permet d’envoyer des notifications push aux clients via un service worker. Il gère les abonnements et envoie les payloads JSON encodés en UTF-8 aux navigateurs compatibles.

---

## Prérequis

- **Java 11** (Amazon Corretto 11 recommandé)
- **IntelliJ IDEA** (ou tout autre IDE compatible Spring Boot)

---

## Configuration du projet

1. **Configurer Java 11 (Corretto) dans IntelliJ**
    - Aller dans **File → Project Structure → Project**
    - Choisir **Project SDK → Corretto 11**
    - S'assurer que le **Project language level** est au moins `11`.

2. **Configurer la connexion à PostgreSQL**
    - Dans `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/tondb
spring.datasource.username=tonuser
spring.datasource.password=tonpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
---

## Installation et lancement

Ouvrir le projet dans IntelliJ

Vérifier que Java 11 est sélectionné comme SDK.

Compiler et exécuter

**Avec IntelliJ :**\
Clic droit sur la classe principale @SpringBootApplication → Run

**Avec la ligne de commande :**\
mvn clean install\
mvn spring-boot:run

---

## Fonctionnement du serveur

**Gestion des abonnements:**\
L’utilisateur envoie sa Push Subscription (endpoint + clés p256dh et auth)\
Le serveur stocke cette subscription en base de données PostgreSQL.

**Envoi de notifications:**\
Le serveur reçoit une requête pour envoyer une notification.\
Le payload est converti en JSON et encodé en UTF-8.

La notification est envoyée au navigateur via le service worker et le Push API.
