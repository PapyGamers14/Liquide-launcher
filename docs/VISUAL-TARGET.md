# Priorité absolue : fidélité tvOS

Instruction d’Eric : fidélité de l’apparence aux références Apple avant les compromis pour anciennes TV.
La version haut de gamme est la cible initiale ; une Lite ne sera dérivée qu’après finalisation.
Le prototype fonctionnel actuel est un socle technique, **pas la cible graphique finale**.

## Références

- https://www.apple.com/fr/newsroom/2025/06/apple-tv-brings-a-beautiful-redesign-and-enhanced-home-entertainment-experience/
- https://www.apple.com/fr/apple-tv-4k/
- https://developer.apple.com/tvos/
- https://developer.apple.com/design/resources/
- https://www.igen.fr/apple-tv/2026/07/les-petites-nouveautes-de-tvos-27-156907

Ne pas confondre accueil tvOS, app Apple TV et Centre de contrôle : établir une référence distincte pour chaque écran.
Maquette image produite dans la conversation : première étude de composition, pas une preuve runtime.
Les illustrations et apps non installées de cette étude ne deviennent pas de faux contenus personnels dans le produit.

## Critères de ressemblance

Comparer captures à résolution identique : grille six colonnes, dimensions relatives, marges, rayons,
label de sélection, zoom, éclairage, fond, géométrie des panneaux, transparence, profondeur et chronologie des animations.
Comparer à la fois état au repos, focus et transition. Ne pas attribuer un pourcentage de fidélité sans mesure.
La capture de la version Android devra être confrontée à la référence ; une image générée ne prouve pas cette fidélité.

## Niveau haut de gamme

Prévoir matériau verre GPU avec réfraction/éclairage localisés, arrière-plans et aperçus réels, animations synchronisées,
profils familiaux, contrôle rapide et sources selon capacités exposées. La performance reste mesurée et optimisée,
sans retirer automatiquement les effets sur la base du nom du fabricant ou de la RAM. L’accessibilité reste prioritaire.

## Technologies

Compiler avec API 37 ; vérifier changements de comportement et version du firmware TV.
Nouvelles API utilisées seulement après détection de disponibilité. Une API Android générale n’est pas une garantie de disponibilité sur Google TV.
Les annonces futures sont une veille et un backlog, jamais une dépendance promise.
Stack AGP 9.4.0, Gradle 9.6.0, Kotlin 2.4.21 : sélection documentée, compilation à valider après accès réseau et SDK.
Compose BOM initial 2025.04.01 : dépendance encore à actualiser et qualifier avant toute revendication de stack entièrement à jour.

## Sources techniques

- https://developer.android.com/build/releases/agp-9-4-0-release-notes
- https://developer.android.com/build/migrate-to-built-in-kotlin
- https://kotlinlang.org/docs/releases.html
