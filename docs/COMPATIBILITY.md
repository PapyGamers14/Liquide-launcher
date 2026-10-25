# Matrice de qualification — aucun appareil validé à cette livraison

| Famille | Configuration à couvrir | Vérifications |
|---|---|---|
| Sony réelle | Modèle et API à identifier | D-pad, reprise apps, veille, mémoire, HOME, sources |
| Émulateur API 26 | 720p/1080p, mémoire limitée | Démarrage, grille, DataStore, absence API récentes |
| Émulateur API 30/31 | Android TV/Google TV | Focus, lifecycle, fenêtres système |
| Émulateur API 35+ | Firmware actuel | Retour prédictif si applicable, sécurité, affichage |
| Autre marque réelle | TCL/Philips/Hisense ou boîtier certifié | Absence dépendance Sony, HOME, changements apps |
| Accessibilité | Texte agrandi, animations système désactivées | Lisibilité, focus, noms accessibles, grille |
| Longue session | Beaucoup d’applications, sorties/retours répétés | Mémoire, cache, absence duplication préférences |

Toutes ces lignes sont **à tester**. Le simple démarrage sur Sony ne valide pas les autres modèles.

Scénarios : lancement à froid, D-pad seul, appui long, retrait/ajout favoris, redémarrage,
installation/désinstallation pendant que le launcher est en arrière-plan, application non exportée,
aucune application visible, favoris vides, stockage en erreur, changement langue, texte agrandi,
mode low-RAM, bouton Retour dans chaque dialogue, retour à l’app précédemment sélectionnée,
veille/réveil et interruption de chargement.

Un HOME intent rend l’application candidate ; il ne garantit pas que chaque constructeur permette sa sélection.
Ne pas désactiver le launcher d’origine pour masquer cette limitation.
