---
version: alpha
name: "Liquide Launcher"
description: "Accueil familial de télévision à panneaux de verre et sélection lumineuse"
colors:
  background: "#101925"
  atmosphere: "#2D4654"
  surface: "#253544"
  foreground: "#F5F7FA"
  secondary: "#BCCAD5"
  focus: "#D8EDF9"
typography:
  sans:
    fontFamily: "Android system sans-serif"
  mono:
    fontFamily: "Android system monospace"
rounded:
  DEFAULT: "22dp"
spacing:
  page-inset: "40dp"
  gap: "20dp"
components:
  focus-card: {}
  toolbar: {}
  dialog: {}
---

# Liquide Launcher Design System

## Overview

### Creative North Star

Une interface familiale de salon inspirée de la profondeur de tvOS Liquid Glass,
avec un cadre lumineux autour de la sélection. Fidélité visuelle à affiner avec captures réelles,
sans redistribuer de ressources Apple. Le matériau verre final n’est pas encore implémenté.

### Product context and register

Audience : famille, télévision à 3 mètres, télécommande uniquement. Registre produit.
Locale française et anglaise, repli anglais. Source : demande utilisateur du 25 octobre 2026.
Pas de thème racing, pas de panneau de statistiques, pas de publicité factice.
Signature : sélection lumineuse et légère élévation sur fond ardoise atmosphérique.
Propriétaire des valeurs exécutables : `LiquideTokens` dans `feature/home/.../LiquideTheme.kt`.
Ce document décrit leur intention ; les valeurs de palette sont vérifiées par `tools/verify_sources.py`.
Nouveau projet : aucun écran frère préexistant à réutiliser ; `FocusCard` devient le composant commun.

## Colors

Background/Atmosphere structurent le fond. Surface porte les cartes. Foreground et Secondary
portent le texte. Focus sert à une bordure de 3dp perceptible même sans animation.
Les transparences sont dérivées de ces tokens, sans palette par écran.

## Typography

Police système Android sans-serif, titres medium, marque semibold, texte normal.
Titre principal 38sp, marque 24sp ; corps Material3. Les caractères ne sont pas transformés en capitales.
La police système évite d’embarquer une police Apple ; respecter l’échelle de texte de l’utilisateur.

## Layout

Grille lazy à cellules adaptatives d’au moins 150dp, marges de 40dp et espaces de 20dp.
Navigation et titre occupent une ligne complète. Dimensions en dp/sp, pas en pixels physiques.
Les états vide/chargement gardent une zone de contenu identifiable. La grille possède le défilement.
Tester les longues traductions et les faibles hauteurs ; la barre de navigation doit rester exploitable.

## Elevation & Depth

Fond dégradé statique, surface teintée, bordure et échelle 1.035 au focus.
Pas de flou en continu ni de réfraction vidéo dans le socle. Le faible coût de rendu prime au démarrage.

## Shapes

Rayon partagé 22dp pour les cartes et actions de barre. Dialogues Material3 provisoires,
à remplacer par un propriétaire partagé si le design final exige une géométrie différente.

## Components

### Foundational visual states

FocusCard gère sémantique bouton, activation, appui long, focus bordé, animation.
Loading : indicateur Material3. Empty : explication localisée. Error : dialogue avec retour possible.
Aucune commande inactive à l’apparence fonctionnelle.

### Buttons and actions

Une action garde son libellé partout. Le bouton principal d’un dialogue annonce l’action réelle.
Les boutons Material3 des dialogues restent des composants natifs Compose accessibles.

### Navigation and data display

Applications/Favoris filtrent le même catalogue. Les favoris apparaissent d’abord dans le catalogue complet.
La barre est dans la grille et défile avec elle ; la qualification D-pad doit confirmer son accessibilité.

### Forms and overlays

Pas de connexion ni de formulaire de données personnelles. Les dialogues sont possédés par l’application,
fermables avec Retour ; ne pas modifier l’apparence des autorisations système.

### Iconography

Icônes des applications installées via PackageManager. Marque Liquide originale.
Noms visibles obligatoires, icônes décoratives non répétées au lecteur d’écran.

### Motion

Transition focus 150ms ; pas de mouvement d’ambiance. Annulation immédiate par retour focus.
Réduction utilisateur et réglage système désactivent le zoom. La version principale ne réduit pas automatiquement les effets selon la RAM.

### Content and data visualization

Phrases courtes en français naturel et anglais, actions concrètes.
Aucun faux film ou recommandation personnelle sans source réelle.

## Do's and Don'ts

- Do: mettre le contenu et la sélection au premier plan.
- Do: centraliser chaque composant réutilisable et les tokens.
- Don't: appliquer une esthétique racing au launcher familial.
- Don't: masquer une limite constructeur derrière un bouton qui ne fonctionne pas.
