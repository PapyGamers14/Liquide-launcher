# Liquide Launcher — socle Android TV

Projet neuf, natif Kotlin/Compose. Version **0.1.0-dev** : socle fonctionnel de développement,
**pas une version finale ni une validation Google Play**. Aucun code de l’ancien PC n’est requis.

## Déjà implémenté

- Découverte des applications proposant une activité Android TV, sans liste de marques/packages codée en dur.
- Ouverture par composant explicite ; gestion des applications désinstallées/inaccessibles.
- Grille adaptative, navigation au focus, favoris avec appui long et stockage DataStore local.
- Réglage de réduction des animations.
- Rafraîchissement au retour à l’accueil et cache d’icônes borné à 4 Mio, chargé hors du thread UI.
- Interface française/anglaise, états chargement/vide/échec, accès aux réglages système.
- Activités HOME et LEANBACK déclarées. Le choix effectif d’accueil dépend du firmware.
- Aucun compte collecté, aucune télémétrie, aucune permission Internet, root, superposition ou accessibilité.

## Périmètre de compatibilité

Cible initiale : **Android TV / Google TV certifiés, Android 8.0 (API 26) et versions supérieures**,
télécommande D-pad, architecture indépendante du fabricant et sans code natif lié à un processeur.
Ce n’est pas une certification de tous les appareils : elle nécessite la matrice de tests de `docs/COMPATIBILITY.md`.
Les anciens Android 5–7 et les boîtiers Android mobiles non-TV ne sont pas inclus.
Un appareil Google TV n’expose pas nécessairement un choix d’accueil par défaut à l’utilisateur.

## Organisation

- `app/` : manifeste, démarrage et assemblage des dépendances.
- `core/model/` : identités, préférences et classement des applications ; sans dépendance Android.
- `core/platform/` : PackageManager, lancement, DataStore, capacités et icônes.
- `feature/home/` : état d’écran, composants focus, thème et accueil.
- `docs/` : architecture, compatibilité, validation et prochaines étapes.

## Ouvrir et compiler sur le Mac

Prérequis : Android Studio, SDK Android 37 + Build Tools 36.0.0 et JDK **17 ou 21**.
Le terminal doit avoir accès à Internet pour télécharger Gradle et les dépendances.

```sh
export JAVA_HOME="/Applications/Android Studio.app/Contents/jbr/Contents/Home"
export ANDROID_HOME="/opt/homebrew/share/android-commandlinetools"
./gradlew :core:model:test :app:lintDebug :app:assembleDebug
```

Adaptez `ANDROID_HOME` si Android Studio utilise un autre SDK. Ce chemin correspond au SDK détecté sur le Mac.
Le script `gradlew` fourni est un **bootstrap source**, et non le Wrapper binaire officiel :
il télécharge Gradle 9.6.0 et contrôle son SHA-256 publié par Gradle, puis le conserve dans `.tooling/`.
Pour obtenir le Wrapper standard après ce premier téléchargement :

```sh
./gradlew wrapper --gradle-version 9.6.0
```

Ouvrez ensuite le dossier dans Android Studio et synchronisez le projet.
APK de débogage attendu après une compilation réussie : `app/build/outputs/apk/debug/app-debug.apk`.
Aucune clé privée ni signature de publication n’est incluse.

## Tester sur la Sony

Activez le débogage et acceptez l’autorisation sur la télévision.
Utilisez le port réellement proposé par le téléviseur, sans supposer qu’il est toujours 5555.

```sh
adb pair IP_TV:PORT_ASSOCIATION
adb connect IP_TV:PORT_CONNEXION
adb devices -l
adb -s IP_TV:PORT_CONNEXION install -r app/build/outputs/apk/debug/app-debug.apk
adb -s IP_TV:PORT_CONNEXION shell am start -n fr.liquide.launcher/.MainActivity
```

`adb pair` ne s’applique que si le firmware propose une association sans fil avec code.
Pour les firmwares proposant seulement le débogage réseau classique, utiliser leur procédure et `adb connect`.
Toujours cibler explicitement la TV avec `-s` si d’autres appareils sont connectés.
Aucun script ne désactive l’accueil Sony/Google ni ne modifie automatiquement les paramètres sécurisés.

## Vérification

- `./gradlew :core:model:test` : classement stable, doublons, favoris manquants.
- `./gradlew :app:lintDebug :app:assembleDebug` : analyse Android et compilation.
- `./gradlew :feature:home:connectedDebugAndroidTest` : activation D-pad sur émulateur ou TV de test.
- `python3 tools/verify_sources.py` : cohérence des ressources/manifeste/limites de sécurité, pas compilation Kotlin.
- `docs/VERIFICATION.md` : résultats réels et limites de cette livraison.

## Prochaines étapes

1. Compiler et tester ce socle ; identifier modèle/API/mémoire de la Sony.
2. Comparer visuellement accueil et panneaux aux références tvOS ; implémenter le matériau verre adapté au GPU.
3. Ajouter recherche, profils locaux et sources HDMI avec détection de capacités et repli.
4. Diagnostiquer les recommandations accessibles, sans extraire les sessions privées des applications.
5. Couvrir plusieurs versions Android TV/marques puis préparer AAB, confidentialité et Play Console.

Ni les catalogues de streaming, ni les aperçus vidéo, ni le verre réfractif, ni les panneaux HDMI ne sont implémentés dans ce socle.

## État GitHub

Le projet est proposé dans une branche de développement. La CI est fournie sous forme de modèle
`docs/ci/android.yml.example` : elle n’est pas active, car la connexion Git locale ne dispose pas du scope workflow.
Compilation Android et validation sur TV restent à réaliser avant intégration en version publiée.
