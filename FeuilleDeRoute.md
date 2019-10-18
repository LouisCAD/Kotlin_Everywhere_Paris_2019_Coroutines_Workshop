## Feuille de route (proposition)

*Note importante :* toutes les étapes sont entourées de logs de début et de fin (utiliser une fonction avec lambda inline)

### Introduction

(Théories avec exemples, présentation + slides publiques)

1. Constat : notre code est souvent train d'attendre.
2. Le code bloquant : séquentiel et simple.
3. Dans la vraie vie, on ne peut pas bloquer. (asynchrone)
4. Dans la vraie vie, on change vite d'avis. (annulation)
5. Les différents styles d'asynchrone : des callbacks à Rx en passant par les futures.
6. Les coroutines : un nouveau style familier, avec des bonus à découvrir…

### Kotlin + la lib kotlinx.coroutines

(Explication de ce que fait Kotlin, de ce que fait kotlinx.coroutines, et pourquoi pas directement dans la stdlib)

1. Kotlin stdlib : CoroutineContext, Continuation, startCoroutine, suspendCoroutine, etc
2. Kotlin (dont kotlinc) : `suspend`, intrinsics
3. kotlinx.coroutines (x for eXtension): API haut niveau, et intégrations variées (Rx, Android, Swing, JavaFx, Google Play Services / Firebase, etc), exploration au cours du workshop

### Quitte à attendre, commençons par allonger les délais…

(Pratique de l'usage des coroutines avec `delay` pour stubber des interfaces)

1. Une seule dépendance nécessaire pour démarrer
2. Fonction `suspend fun main` basique avec delay + log (alias à println)
3. Implémentation d'une interface avec delay
4. Test de l'interface dans la fonction main
5. Ajoutons quelques conditions
6. Composons les fonctions
7. Conclusion (récap de ce qu'on a appris, plus cas d'usages de `delay` dans la vraie vie)

### Changer de ~thread~ dispatcher : un jeu d'enfant !

1. Utiliser un code dummy avec `withContext(Dispatchers.Default)` (ex: boucle for + `calcul intensif de quelques secondes`)
2. Ecrire un gros fichier texte avec `withContext(Dispatchers.IO)` avec logs avant/après
3. Remplacer `withContext` par l'opérateur `invoke`
4. Lancer l'écriture d'un autre fichier de manière concurrente avec `launch` avec logs avant/après
5. Exécuter et observer les logs
6. Déplacer le `launch` au début du block `withContext`
7. Exécuter et observer les logs : withContext attend la fin du launch… mais pourquoi ?

### Concurrence structurée : vous ne pourrez plus vous en passer !

1. Les problèmes de concurrence
2. Opérations en parallèle avec async/await
3. Gestion de l'échec d'un coroutine: Job/SupervisorJob
4. Complétion du scope et de ses coroutines
5. Annulation du scope, `isActive` et resume

### Gestion des exceptions

1. try/catch !
2. Remplacer par un `ExceptionHandler` dans le contexte coroutine courant

### Custom coroutine

1. Crée sa propre coroutine avec `suspendCoroutine`
2. Gérer l'annulation
3. En faire une fonction générique

### Channels

1. 

### Actors

1. Exécution séquentielles de `suspend` functions
2. Les différentes capacités
3. Gérer la backpressure, `offer` vs `send`
4. Gérer la cloture d'un actor avec `CompletionHandler`

### Flows

1. Flow simple pour commencer, valider que les flows sont "froids"
2. Jouons avec les différents opérateurs
2. Flow pour wrapper des callbacks
