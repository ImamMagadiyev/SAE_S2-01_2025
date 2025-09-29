# SAE S2-01 - Projet Java (IUT 2025)

Projet réalisé dans le cadre de la **SAE S2-01 - Programmation orientée objet** à l'IUT de Toulouse.  
Ce projet combine une **logique métier claire** avec une **interface graphique Java Swing** construite grâce à **WindowBuilder**.  
Il a été réalisé en **groupe de 4 étudiants**.

---

## 📂 Structure du projet

- `src/main/java/modele/` → contient le **modèle** (gestion des données et logique métier).
- `src/main/java/ihm/` → contient l’**interface graphique (IHM)** réalisée avec **WindowBuilder**.
- `src/main/resources/` → **ressources** utilisées (JSON, images).
- `src/test/java/modele/` → **tests unitaires** avec **JUnit**.
- `pom.xml` → configuration **Maven**.
- `.gitignore` → exclusion des fichiers générés (`/target`).

---

## 🖥️ Fonctionnalités principales

- **Ajout et gestion** de clients, paniers et tomates.
- **Sauvegarde et chargement** des données au format **JSON**.
- **Interface graphique** avec dialogues (facture, informations client, panier, conseils de culture…).
- Organisation du code en **architecture MVC** (Modèle - Vue - Contrôleur).
- **Tests unitaires** pour valider la partie **modèle**.

---

## ⚙️ Technologies utilisées

- **Java 8**
- **Swing / WindowBuilder** (interface graphique)
- **Maven** (gestion de projet)
- **JUnit 4** (tests unitaires)
- **JSON** pour la persistance des données

---

## 🚀 Pour exécuter le projet

1. **Cloner le dépôt** :
   ```bash
   git clone https://github.com/ImamMagadiyev/SAE_S2-01_2025.git
   cd SAE_S2-01_2025
