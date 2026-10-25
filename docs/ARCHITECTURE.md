# Architecture et décisions

## Dépendances

`app → feature:home → core:platform → core:model` ; app assemble aussi platform/model.
Les modèles Kotlin restent indépendants d’Android. Les services Android utilisent le contexte Application.
Le ViewModel détient l’état et les travaux annulables ; les composables rendent l’état.
Injection explicite par constructeur : pas de framework DI tant que le nombre de dépendances ne le justifie pas.

## Contraintes du produit

Source : demande d’Eric du 25 octobre 2026. Nouveau code, identité familiale proche de tvOS,
performances, maintenabilité, diffusion Android TV/Google TV multimarque et publication Google Play visée.
Aucun ancien dépôt utilisé. La Sony est un appareil de test, pas une dépendance d’architecture.

## Compatibilité et sécurité

Socle API 26, compile/target 37 et AGP 9.2.1 / Gradle 9.6.0 / Kotlin 2.4.21. Le SDK 37 doit être installé. **Réévaluer targetSdk et exigences Play à la soumission**.
Les fonctions Sony/Philips/TCL/etc. devront implémenter des adaptateurs optionnels exposant Supported/Unavailable.
Une absence de capacité retire la commande ou propose l’écran système ; jamais un bouton muet.
ADB est uniquement un outil de développement. Le cœur public ne requiert ni root, ni ADB, ni permissions signature.
Pas de suppression du launcher d’origine ni de modifications des réglages sécurisés.

## Données et performances

Les favoris utilisent l’identifiant exact du composant, jamais le libellé. Les préférences sont locales et mises à jour
atomiquement par DataStore. Le résultat de PackageManager est traité sur IO, avec annulation de rafraîchissement.
Les icônes sont rasterisées hors UI en 160×160 et mises en cache avec une limite de 4 Mio.
La liste est lazy et conserve des clés stables. La mémoire des bitmaps visibles s’ajoute à celle du cache.
Les animations sont coupées si l’utilisateur ou le système le demande, uniquement.
Pas de flou plein écran continu dans cette première version.

## Évolution

N’ajouter un module fonctionnel qu’avec une frontière de responsabilité réelle. Les futurs connecteurs médias
seront séparés du launcher et devront avoir un état indisponible/offline/autorisation requise.
Aucun scraping de session, aucun catalogue factice présenté comme lié au compte.
Les tests de fluidité devront utiliser un build release signé pour test, jamais une mesure debug seule.
