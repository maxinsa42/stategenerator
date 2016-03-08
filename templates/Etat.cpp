

/*************************************************************************

 Etat  -  description

 -------------------

 début                : 7/03/2016

 copyright            : (C) 2016 par TFavrot

 *************************************************************************/

​

//---------- Réalisation de la classe <Etat> (fichier Etat.cpp) --

​

//---------------------------------------------------------------- INCLUDE

​

//-------------------------------------------------------- Include système

using namespace std;

#include <iostream>

​

//------------------------------------------------------ Include personnel

#include "Etat.h"

​

//------------------------------------------------------------- Constantes

​

//---------------------------------------------------- Variables de classe

​

//----------------------------------------------------------- Types privés

​

​

//----------------------------------------------------------------- PUBLIC

//-------------------------------------------------------- Fonctions amies

​

//----------------------------------------------------- Méthodes publiques

// type Etat::Méthode ( liste de paramètres )

// Algorithme :

//

//{

//} //----- Fin de Méthode

void Etat::print() const{
	cout << name << endl;
}

​

//-------------------------------------------- Constructeurs - destructeur

Etat::Etat ( const string name )

// Algorithme :

//

{

#ifdef MAP

    cout << "Appel au constructeur de copie de <Etat>" << endl;

#endif

	this->name = name;

} //----- Fin de Etat (constructeur de copie)

    

    

    Etat::~Etat ( )

    // Algorithme :

    //

    {

#ifdef MAP

        cout << "Appel au destructeur de <Etat>" << endl;

#endif

    } //----- Fin de ~Etat

    

    

    //------------------------------------------------------------------ PRIVE

    

    //----------------------------------------------------- Méthodes protégées

    

    //------------------------------------------------------- Méthodes privées

