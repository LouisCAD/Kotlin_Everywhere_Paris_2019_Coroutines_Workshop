# Exercices

## Avant de commencer

Bienvenue dans le coeur de ce Workshop !

Vous allez manipuler et écrire des fonctions `suspend` à travers un group d'exercices.

Nous vous conseillons d'effectuer tous les exercices le plus rapidement possible en premier lieu, et dès que vous avez terminé, de laisser place à votre créativité ou votre curiosité pour tester les autres possibilités, ou regarder comment ça fonctionne sous le capot.

Vous trouverez **à la fin** de ce document une liste non exhaustives de cas d'usages des coroutines pour **vous inspirer** et vous aider à tirer parti des connaissances acquises dans vos projets et en récolter les bénéfices/bienfaits.

## Introduction

Le code que nous écrivons est souvent en train d'attendre.

Que cela soit des traitements externes (I/O, accès distant à une API ou BDD, action/entrée utilisateur…), des traitements internes (calculs, transformation, filtrage, rendu…) ou même jute un délai arbitraire, les solutions les plus basiques sont les fonctions bloquantes, et les callbacks.

### Kotlin + la lib kotlinx.coroutines

#### Un bref historique

Kotlin 1.0 est sorti en 2016. Peu de gens le savaient, mais le modifieur `suspend` pour les fonctions (`fun`) existait déjà et était utilisé dans les détails d'implémentation des séquences (`kotlin.sequences`).

Kotlin 1.1 a ajouté le support des coroutines (en expérimental), une manière d'écrire du code non bloquant, permettant de réduire drastiquement le nombre de threads ou callbacks nécessaires, et de grandement réduire la consommation de mémoire vive (RAM) de ce fait.

Ceci dit, il était peu pratique d'utiliser les coroutines telles que fournies dans la bibliothèque standard (stdlib), car, dans un souci de la garder petite, seule une API bas niveau minimaliste était intégrée.
L'équipe de Roman Elizarov (Kotlin libraries lead at JetBrains) a donc fourni une bibliothèque d'extension appelée `kotlinx.coroutines`, où la lettre X veut donc bien dire extension, où une API haut niveau et des intégrations variées sont fournies, y compris pour Android.

Kotlin 1.3 est sorti durant l'automne 2018, rendant les coroutines stables, avec la version 1.0 de la bibliothèque kotlinx.coroutines qui a apporté un changement majeur : la "concurrence structurée".

#### Qui fait quoi

Vous l'aurez compris, l'API fourni dans la stdlib Kotlin étant minimaliste et bas niveau, nous allons peu nous en servir.

Nous allons beaucoup utiliser le modifieur `suspend` devant les fonctions, qui les déclare non bloquantes, les autorisant alors à appeler d'autres fonctions `suspend`.

Il nous est fourni par le langage Kotlin lui même, et le compilateur s'occupe de les réécrire sous forme de "continuations", de sorte qu'elles fonctionnent pour les différents "targets" de Kotlin (JVM, Android, JS, Native, WebAssembly…), et ce, sans nécessiter un "runtime" spécifique.

Le reste est fourni par la bibliothèque multiplateforme kotlinx.coroutines, qui a un cycle de release plus fréquent que le langage Kotlin lui-même. Cela comporte la concurrence structurée, de quoi lancer des coroutines concurrentes, le support de l'annulation, TK

TK


