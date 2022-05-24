# GOOD GOVERNMENT(GG)
>*"Tena tsy mba mijery ny olan'ny vahoaka mihintsy ilay fanjakana"*, Ma mère

## Description
Dans un monde merveilleux, pour mieux être à l’écoute de la population, le gouvernement malgache a décidé de mettre en place une application pour permettre à toutes personnes de signaler les problèmes.
Les problèmes seront ensuite affecté par région et ils auront un statut (nouveau, en cours de traitement, terminé).
## Fonctionnalités

### • Un citoyen a une application mobile pour
    o Gérer son compte
    o Faire un signalement de problème avec coordonées géographique et image à l'appui
    o Suivre le statut de son signalement(nouveau, en cours de traitement, terminé)
    o Reçevoir une notification quand son signalement est marqué comme terminé 


### • Un compte administrateur utilise une interface web pour
    o Voir la liste des signalements pas encore affectés à aucune région
    o Affecter un signalement à une région
    o Voir les statistiques suivants: courbe de nombre des signalements mensuels de l'année dernière, Top 5 des mots-clés dans les signalements,  Histogramme de top 6 des régions qui ont les plus des signalements
    o CRUD des éléments nécessaires


### • Un compte région utilise une interface web pour
    o Voir la liste des signalements qui lui sont attribués
    o Voir les signalements qui lui sont affectés sur une carte avec les infos minimales du signalement quand la souris passe et le détails quand on clique sur un signalement
    o Mettre à jour le status d'un signalement

## Choix du technologie utilisé
### Cet repository contient le backend du projet GG en utilisant:
    • Spring Boot pour les web services RESTFul
    • Spring Data JPA pour la couche d'accès au base des données
    • Spring security pour la gestion des rôles, authorisation et des tokens JWT(client web et mobile alors je prefere ne pas utilisier le form login)
    • Base des données PostgreSQL et MongoDB

## Documentation
Pour voir la documentation, [cliquer ici](documentation.pdf)