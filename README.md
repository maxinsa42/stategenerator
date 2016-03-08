# stategenerator
mvn / spring / java project:automaticaly generate cpp/headers for lutin automaton states, origitating csv specs

# Comment a mettre en place
Faire le checkout puis lancer un "clean".
Mvn va telecheracher les dependances (notamment quelques jar du spring framework).
Si tu essaye de lancer le projet maintenant tu vas voir une exception. -> Pas paniquer, c'est normal, il te faut encore un parametre pour ta JVM:
"-javaagent:"spring-instrument-4.2.5.RELEASE.jar". Eh bon c'est ca.

# Comment a configurer
Si tu es sur MacOs / Linux tu n'auras pas besoin des modifs.
Si tu es sur Windows il faut que tu remplaces quelques slashes par des antislashes dans stategenerator.properties
Si t'as envie de ameliorer le fichier tu peux utiliser SPEL et integrer ca quelque part: { systemProperties['file.separator'] }

# Lancer
Une fois tu vois ca, t'as reussi:
* Auto generation done. Your files are here:
*    -> /tmp/GeneratedState-* [.cpp / .h] 
