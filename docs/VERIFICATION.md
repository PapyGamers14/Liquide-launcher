# Vérification du socle — 25 octobre 2026

## Effectué et réussi

- `python3 tools/verify_sources.py` : XML, correspondance des traductions, ressources référencées,
  intentions HOME/LEANBACK, absence de permissions inattendues, correspondance palette/documentation,
  bannière 320×180 et absence de commandes shell ou de branchement sur le fabricant dans le runtime.
- `sh -n gradlew` et `sh -n tools/connect-tv.sh` : syntaxe des scripts.
- Audit statique de contrat UI : aucune erreur ni avertissement après correction du tableau des propriétaires.

## Tenté et bloqué

`./gradlew :core:model:test :app:lintDebug :app:assembleDebug` : échec avant compilation,
le réseau de cet environnement ne résout pas `services.gradle.org`. Aucune dépendance n’a pu être téléchargée.
Le SDK installé sur le Mac est API 35 ; la configuration mise à jour nécessite API 37.

## Non effectué

Compilation Kotlin, tests JUnit, Android Lint, test D-pad instrumenté, test TV, benchmark release,
comparaison visuelle runtime et qualification Play Store. Aucun APK validé n’est fourni.
La maquette image de la conversation est une étude visuelle, pas un résultat de compilation Android.
Le modèle de CI est livré dans docs/ci/android.yml.example et reste à activer. Aucun résultat CI n’est revendiqué.
