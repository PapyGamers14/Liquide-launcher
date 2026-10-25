# Assistant TV — comptes personnels ChatGPT

## Intention corrigée par Eric

Chaque personne doit se connecter à son propre compte ChatGPT et utiliser les droits de son propre compte sur la TV.
Pas de clé API à fournir par l’utilisateur et pas de clé commune financée par le développeur.
La priorité actuelle reste le launcher haut de gamme fidèle à tvOS. L’assistant sera une application compagnon séparée.

## Piste officielle à qualifier : Sign in with ChatGPT

Les documents OpenAI consultés le 25 octobre 2026 décrivent une connexion d’identité et, séparément,
une autorisation d’utiliser l’usage inclus dans un plan éligible pour les applications prises en charge.
L’intégration et l’éligibilité de notre application restent à vérifier ; un compte ChatGPT ne suffit pas à les garantir.
La connexion et le partage du plan ne donnent pas accès aux conversations existantes ni aux mémoires ChatGPT.
Donc cette piste ne promet pas un clone complet du produit ChatGPT avec historique, voix et outils identiques.

Pour accéder au vrai ChatGPT avec son historique existant, une autre piste est d’ouvrir le site officiel
sur un navigateur TV compatible. Connexion, navigation D-pad, voix et stabilité doivent être testées sur TV.
Ne pas promettre un emballage WebView compatible ni utiliser les cookies privés ou des API non documentées.

## Exigences de conception

Compte personnel, déconnexion visible, protection des sessions sur appareil partagé, absence de compte commun,
respect des limites individuelles, identité propre à l’application, aucune affiliation officielle prétendue.
Pour SIWC : suivre le parcours officiel, ne jamais demander le mot de passe ou les cookies ChatGPT à notre application.
Les conditions SIWC imposent notamment contrôle utilisateur et stockage local des jetons persistants.
La conversation vocale et le micro de la télécommande sont à qualifier séparément.

Aucune authentification, clé, session, requête IA ou autorisation de microphone n’est implémentée dans ce socle.

## Sources officielles

- https://help.openai.com/en/articles/20001410-sign-in-with-chatgpt
- https://help.openai.com/en/articles/20001542-using-your-chatgpt-plan-in-other-apps-and-sites
- https://developers.openai.com/cookbook/articles/sign-in-with-chatgpt
- https://openai.com/policies/sign-in-with-chatgpt-terms/
