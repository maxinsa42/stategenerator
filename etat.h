

/*************************************************************************

 Etat  -  description

 -------------------

 début                : 7/03/2016

 copyright            : (C) 2016 par TFavrot

 *************************************************************************/


//---------- Interface de la classe <Etat> (fichier Etat.h) ------

#if ! defined ( ETAT_H )

#define ETAT_H

//--------------------------------------------------- Interfaces utilisées

#include <vector>
#include "symbole.h"
#include "automate.h"

//------------------------------------------------------------- Constantes

//------------------------------------------------------------------ Types


//------------------------------------------------------------------------

// Rôle de la classe <Etat>

//

//

//------------------------------------------------------------------------



class Etat

{

    //----------------------------------------------------------------- PUBLIC

    

public:

    //----------------------------------------------------- Méthodes publiques

    
    void Print() const;
   
	virtual bool Transition (Automate & automate, Symbole *s);
    
    virtual Symbole* Reduction (vector<Symbole*> s);

    //-------------------------------------------- Constructeurs - destructeur

    Etat () {}

    // Mode d'emploi (constructeur de copie) :

    //

    // Contrat :

    //
    

    virtual ~Etat ( );

    // Mode d'emploi :

    //

    // Contrat :

    //

    

    //------------------------------------------------------------------ PRIVE

    

protected:

    //----------------------------------------------------- Méthodes protégées

    

private:

    //------------------------------------------------------- Méthodes privées

    

protected:

    //----------------------------------------------------- Attributs protégés
	string name;
    

private:

    //------------------------------------------------------- Attributs privés

    

    //---------------------------------------------------------- Classes amies

    

    //-------------------------------------------------------- Classes privées

    

    //----------------------------------------------------------- Types privés

    

};



//----------------------------------------- Types dépendants de <Etat>



#endif // ETAT_H

