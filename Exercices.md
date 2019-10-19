# Exercices

## Avant de commencer

Bienvenue dans le coeur de ce Workshop !

Vous allez manipuler et écrire des fonctions `suspend` à travers un group d'exercices.

Nous vous conseillons d'effectuer tous les exercices le plus rapidement possible en premier lieu, et dès que vous avez terminé, de laisser place à votre créativité ou votre curiosité pour tester les autres possibilités, ou regarder comment ça fonctionne sous le capot.

Vous trouverez **à la fin** de ce document une liste non exhaustive de cas d'usages des coroutines pour **vous inspirer** et vous aider à tirer parti des connaissances acquises dans vos projets afin d'en récolter les bénéfices/bienfaits.

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

Vous l'aurez compris, **l'API fourni dans la stdlib Kotlin** étant **minimaliste et bas niveau**, nous allons peu nous en servir directement.

Nous allons beaucoup utiliser **le modifieur `suspend`** devant les fonctions, qui les déclare non bloquantes, les autorisant alors à appeler d'autres fonctions `suspend`.

Il nous est **fourni par le langage Kotlin** lui même, et le compilateur s'occupe de les réécrire sous forme de "continuations", de sorte qu'elles fonctionnent pour les différents "targets" de Kotlin (JVM, Android, JS, Native, WebAssembly…), et ce, sans nécessiter un "runtime" spécifique.

**Le reste** est fourni par la **bibliothèque multiplateforme kotlinx.coroutines**, qui a un cycle de release plus fréquent que le langage Kotlin lui-même.

Voici une liste non exhaustive de ce que cela comporte :
- la concurrence structurée et le support de l'annulation (spoiler: killer feature)
- de quoi lancer des coroutines concurrentes très facilement (la base)
- de quoi changer de contexte d'exécution (ex: thread ou thread pool) très facilement
- de quoi passer d'un callback annulable à une fonction `suspend` annulable
- de quoi communiquer entre plusieurs coroutines et callbacks (facilement, encore)
- de quoi créer et traiter des flux de valeurs avec une grande simplicité
- des intégrations optionelles avec Android, RxJava, JavaFx, Java Swing, JS promises, Firebase & Google Play Services Android, etc.

Vous allez découvrir les bases essentielles dans ce workshop.
Vous aurez ensuite le loisir de poursuivre les essais et l'exploration, prenant alors
part à cette véritable révolution industrielle que sont les coroutines de Kotlin et la
concurrence structurée.

## Le vif du sujet : la pratique

C'est en foregeant qu'on devient forgeron.
C'est en allant en Afrique qu'on devient Afrikaneur.
C'est en faisant n'importe quoi qu'on devient n'importe qui.
C'est en pédalant qu'on devient ~une pédale~ musclé.

Bref, la pratique va vous rendre maître des coroutines, alors c'est parti pour faire attendre votre code !

### Vérification préliminaire

✓ Le projet Gradle présent a déjà Kotlin/JVM 1.3+ de configuré, et la dépendance kotlinx.coroutines core d'ajoutée.

👉 Ouvrez le fichier [`main.kt`](exercises/src/main/kotlin/main.kt) du module `exercises`. (Un double clic sur la touche <kbd>⇧</kbd> de votre clavier vous permet de rapidement chercher dans tout le projet)

Il possède déjà une fonction `main()` où la fonction `withEnterAndExitLog` est utilisée 2 fois, la fonction `log`, 1 fois, et la fonction `TODO`, 1 fois également.

On ne va probablement pas vous apprendre que `main` est la fonction principale pour exécuter le programme, mais les 3 autres fonctions utilisées vont nous être d'une grande aide.

- `withEnterAndExitLog` prend un tag en entrée puis affiche un log dans la console relatif à l'exécution du bloc passé, notamment le temps d'exécution, ce qui nous sera très utile pour comprendre ce qu'il se passe par la suite.
- `log` est un simple alias/raccourci vers `println`, mais vous le taperez plus vite sans fautes de frappe, on vous fait gagner du temps !
- `TODO` fait partie de la stdlib de Kotlin. Ça compile, mais si ça s'exécute, ça lance une `kotlin.NotImplementedError`.

👉 Voyez par vous même en cliquant sur le bouton "play" vert en forme de triangle situé à la gauche de la déclaration de la fonction `main`.

La compilation va s'effectuer, vous allez voir 5 lignes de logs dans la console, et l'erreur lancée par la fonction TODO.

Si ce n'est pas le cas, veuillez sortir de la salle… ou trouver une solution ! Nous sommes là pour vous aider si vous rencontrez tout problème ou vous interrogez.

### Quitte à attendre, commençons par allonger les délais…

🔁 Petite analogie :
Avec les coroutines, au lieu de faire une salle d'attente pour chaque nouveau client, vous faites une queue, et c'est plus facile à gérer comme ça, notamment quand le moment de fermer les portes arrive !

Vous le comprennez : les salles d'attentes multiples, ce sont les callbacks, les clients, ce sont les commandes/expressions/fonctions à exécuter, et la queue, ce sont des lignes de code successives.

Mais pour ça il faut de l'attente. En attendant d'avoir des choses intéressantes à attendre, vous vous y attendez peut-être, nous allons attendre rien que pour attendre.

❌ Exit `Thread.sleep`, car vu le coût d'un thread (~1Mo de RAM chacun), on a autre chose à faire que de le garer et le laisser dormir, c'est pas l'hôtel ici !

📝 Nous allons donc utiliser la fonction `delay` (fournie évidemment par kotlinx.coroutines), qui va suspendre la coroutine (c'est à dire la fonction `suspend` qui l'appelle), sans bloquer le thread appelant (peu importe lequel, pas de traitement de faveur), et dès que le délai donné (en millisecondes) sera passé, elle tentera de trouver un thread de disponible au plus tôt pour rependre la suite.

ℹ️ À l'usage, c'est comme si la fonction retournait, même s'il s'agit sous le capot d'une "continuation" qui "reprend" ("resume" en anglais), la vraie méthode ayant déjà retourné depuis belle lurette pour ne pas que le thread reste bloqué.

👉 À la place du `TODO()`, mettez `delay(1234)` (ou `delay(4321)` pour démontrer une patience exceptionnelle).

Oh non ! C'est souligné en rouge ! Profitez-en pour regarder brièvement le message d'erreur.
Pas la peine de tenter d'exécuter, ça ne fonctionnera pas.

🕤 Pas de temps à perdre, rajoutez le modifieur `suspend` au début de la signature de la fonction `main`. Pouf l'erreur est partie !

Mais comment est-ce possible ? `delay` est appelée par la lambda donnée à `withEnterAndExitLog`, pas par `main` directement ? Eh bien si ! En fait, `withEnterAndExitLog` est une fonction `inline`, et la lambda qu'on lui donne est alors "inlinée" dans `main`, qui grâce au travail de kotlinc, le compilateur Kotlin, appelera directement le code de la lambda `inline`. Et du coup, vu que le lieu où la lambda est inlinée est une fonction `suspend`, alors on peut en appeler dedans.

La stdlib de Kotlin est pourvue d'un grand nombre de fonctions `inline`, et si vous utilisez déjà Kotlin, il est fort probable que vous en utilisiez provenant de diverses bibliothèques, et que vous en ayez même déjà écrit. Eh bien ça fonctionne très bien avec les coroutines !
 
 ▶️ Exécutez le programme à nouveau et observez les logs pour voir ce qu'il se passe.
 
 Vous constatez l'utilité de notre fonction `withEnterAndExitLog` pour apprendre !

### Changer de ~thread~ dispatcher : un jeu d'enfant !

TK
