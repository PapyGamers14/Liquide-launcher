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

## Correction du Wrapper Gradle

Wrapper officiel Gradle 9.6.0 ajouté : scripts Unix/Windows, JAR et propriétés.
Empreinte SHA-256 du JAR contrôlée contre celle publiée par Gradle ; empreinte de distribution fixée.
Syntaxe shell validée. Le lancement du Wrapper atteint le téléchargement de la distribution,
mais Java renvoie UnknownHostException pour services.gradle.org dans cet environnement.
Cela ne constitue pas une compilation réussie ; relancer la synchronisation depuis Android Studio.

## Correction de compatibilité Android Studio

Le journal local Android Studio 2026.1.1 indique explicitement une limite AGP 9.2.1.
Le catalogue passe de 9.4.0 à 9.2.1 ; compileSdk/targetSdk 37 sont conservés (support documenté AGP 9.2).
La toolchain utilise le JDK 21 installé, avec bytecode 17 explicite, pour éviter d’exiger un JDK 17 absent.
Contrôles statiques et cohérence des sources exécutés ; nouvelle synchronisation IDE et compilation à confirmer.

## Première compilation réussie sur le Mac

La commande utilisateur `:core:model:test :app:assembleDebug` a terminé avec BUILD SUCCESSFUL.
APK présent dans app/build/outputs/apk/debug/app-debug.apk (environ 10 Mio).
Rapport JUnit vérifié : AppOrderingTest, 2 tests, 0 échec, 0 erreur, 0 ignoré.
La validation runtime TV reste en attente : appareil absent de adb devices, reconnexion à 192.168.1.47:5555
renvoyant No route to host. Aucune installation n’a été effectuée lors de cette tentative.
