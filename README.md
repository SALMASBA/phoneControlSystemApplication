📱 PhoneControlSystem
📌 Description
PhoneControlSystem est une application basée sur Spring Boot et GraphQL qui permet de gérer un catalogue de téléphones.
Elle offre une API flexible pour effectuer des opérations CRUD : ajouter, consulter, modifier et supprimer des téléphones.

🚀 Fonctionnalités
Ajouter un téléphone
Modifier un téléphone
Supprimer un téléphone
Rechercher par :
ID
IMEI
Modèle
Modèle + prix
Plage de prix
Récupérer la liste complète des téléphones
Système de Subscriptions GraphQL (ajout, mise à jour, suppression)

🛠️ Technologies utilisées
Java 17+
Spring Boot
GraphQL Java / Spring GraphQL
Maven
H2 / MySQL
Lombok

📡 Exemple de requêtes GraphQL
▶️ Obtenir tous les téléphones
{
  getPhones {
    idPhone
    model
    color
    imei
    price
  }
}

▶️ Ajouter un téléphone
mutation {
  savePhone(phone: {
    model: "Samsung Galaxy S21",
    color: "Black",
    imei: "123456789",
    price: 7890.0
  }) {
    idPhone
    model
  }
}

▶️ Lancer l'application
Cloner le projet
git clone https://github.com/ton-compte/PhoneControlSystem.git
Ouvrir dans IntelliJ / Eclipse
Lancer avec Maven
mvn spring-boot:run

Accéder à GraphiQL
👉 http://localhost:8090/graphiql
📁 Structure du projet
src/
 ├── main/
 │   ├── java/
 │   │   └── com.project.phone
 │   │        ├── entity
 │   │        ├── dto
 │   │        ├── repository
 │   │        ├── service
 │   │        ├── graphql
 │   │        └── PhoneControlSystemApplication.java
 │   └── resources/
 │        ├── application.properties
 │        └── schema.graphqls

✨ Auteur

Développé par SBA SALMA.
Projet académique – Gestion des téléphones 📱
