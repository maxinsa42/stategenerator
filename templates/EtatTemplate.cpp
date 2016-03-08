

/*************************************************************************

 EtatTemplate  -  description

 -------------------

 début                : 7/03/2016

 copyright            : (C) 2016 par TFavrot

 *************************************************************************/



//---------- Réalisation de la classe <EtatTemplate> (fichier EtatTemplate.cpp) --



//---------------------------------------------------------------- INCLUDE



//-------------------------------------------------------- Include système

using namespace std;

#include <iostream>



//------------------------------------------------------ Include personnel

#include "EtatTemplate.h"



//------------------------------------------------------------- Constantes



//---------------------------------------------------- Variables de classe



//----------------------------------------------------------- Types privés





//----------------------------------------------------------------- PUBLIC

//-------------------------------------------------------- Fonctions amies



//----------------------------------------------------- Méthodes publiques

// type EtatTemplate::Méthode ( liste de paramètres )

// Algorithme :

//

//{

//} //----- Fin de Méthode


bool EtatTemplate::transition (Automate & automate, Symbole *s)
{
	
}

//-------------------------------------------- Constructeurs - destructeur

EtatTemplate::EtatTemplate ( const string name ) : Etat(name)

// Algorithme :

//

{

#ifdef MAP

    cout << "Appel au constructeur de <EtatTemplate>" << endl;

#endif

    } //----- Fin de EtatTemplate (constructeur de copie)
        

    EtatTemplate::~EtatTemplate ( )

    // Algorithme :

    //

    {

#ifdef MAP

        cout << "Appel au destructeur de <EtatTemplate>" << endl;

#endif

    } //----- Fin de ~EtatTemplate

    

    

    //------------------------------------------------------------------ PRIVE

    

    //----------------------------------------------------- Méthodes protégées

    

    //------------------------------------------------------- Méthodes privées

