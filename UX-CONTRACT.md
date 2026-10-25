# Contrat de comportement

Sources produit : `docs/ARCHITECTURE.md`, demande utilisateur du 25 octobre 2026.
Les textes appartiennent aux ressources Android `values` et `values-fr`.

| Capacité | Propriétaire | Règle | Vérification |
|---|---|---|---|
| Focus et activation | FocusCard | Bordure visible ; OK ouvre ; appui long présente les favoris | FocusCardTest + TV |
| Catalogue | HomeViewModel | Chargement, résultat, vide, erreur ; réessayer reste accessible | Validation runtime à faire |
| Favoris | PreferencesRepository | Identité de composant stable, modification atomique locale | Tests modèle + TV |
| Navigation | HomeScreen | Applications/Favoris ne quittent pas l’accueil ; Retour ferme le dialogue | TV à faire |
| Dialogues | Material3 AlertDialog | Focus modal, Retour annule, pas d’effet sur paramètres système | TV à faire |
| Réglages TV | Intent ACTION_SETTINGS | Ouvrir le système ; afficher une erreur si indisponible | Multimarque à faire |
| Animation | LiquideTokens + capacités | Sans zoom si désactivation système ou préférence | TV à faire |
| Défilement | LazyVerticalGrid | Une grille propriétaire ; cartes avec clés stables | Beaucoup d’apps à tester |

Les changements de favoris doivent survivre au redémarrage. Un échec de stockage est visible.
Le rechargement au retour des apps ne déplace pas volontairement le focus vers le premier élément.
L’état des filtres est saveable ; la restauration exacte du focus après suppression d’une app reste à qualifier.
Pas de compte média importé, pas de synchronisation cloud ni de collecte de données dans ce socle.

## Canonical UI Map

| Capability | Canonical owner | Source of truth | Allowed variants | Verification |
|---|---|---|---|---|
| Focus | FocusCard | DESIGN.md | app / toolbar | FocusCardTest, TV pending |
| Dialog | Material3 AlertDialog | UX-CONTRACT.md | settings / favorite / error | Runtime pending |
| Catalog | HomeViewModel | docs/ARCHITECTURE.md | all / favorites | Unit + TV pending |
