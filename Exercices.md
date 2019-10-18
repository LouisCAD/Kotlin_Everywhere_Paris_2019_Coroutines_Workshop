# Exercices

## Avant de commencer

Bienvenue dans le coeur de ce Workshop !

Vous allez manipuler et écrire des fonctions `suspend` à travers un group d'exercices.

Nous vous conseillons d'effectuer tous les exercices le plus rapidement possible en premier lieu, et dès que vous avez terminé, de laisser place à votre créativité ou votre curiosité pour tester les autres possibilités, ou regarder comment ça fonctionne sous le capot.

Vous trouverez **à la fin** de ce document une liste non exhaustives de cas d'usages des coroutines pour **vous inspirer** et vous aider à tirer parti des connaissances acquises dans vos projets et en récolter les bénéfices/bienfaits.

## Introduction

Le code que nous écrivons est souvent en train d'attendre.

Que cela soit des traitements externes (I/O, accès distant à une API ou BDD, action/entrée utilisateur…), des traitements internes (calculs, transformation, filtrage, rendu…) ou même jute un délai arbitraire, les solutions les plus basiques sont les fonctions bloquantes, et les callbacks.


