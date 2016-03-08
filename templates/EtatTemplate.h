

/*************************************************************************

 EtatTemplate  -  description

 -------------------

 début                : 7/03/2016

 copyright            : (C) 2016 par TFavrot

 *************************************************************************/



//---------- Interface de la classe <EtatTemplate> (fichier EtatTemplate.h) ------

#if ! defined ( ETATTEMPLATE )

#define ETATTEMPLATE



//--------------------------------------------------- Interfaces utilisées

#include "Etat.h"

//------------------------------------------------------------- Constantes



//------------------------------------------------------------------ Types



//------------------------------------------------------------------------

// Rôle de la classe <EtatTemplate>

//

//

//------------------------------------------------------------------------



class EtatTemplate : public Etat

{

    //----------------------------------------------------------------- PUBLIC

    

public:

    //----------------------------------------------------- Méthodes publiques

    // type Méthode ( liste de paramètres );

    // Mode d'emploi :

    //

    // Contrat :

    //

    bool transition (Automate & automate, Symbole *s);
    

    //-------------------------------------------- Constructeurs - destructeur

    EtatTemplate ( const string name );

    // Mode d'emploi (constructeur de copie) :

    //

    // Contrat :

    //

    

    virtual ~EtatTemplate ( );

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

    

private:

    //------------------------------------------------------- Attributs privés

    

    //---------------------------------------------------------- Classes amies

    

    //-------------------------------------------------------- Classes privées

    

    //----------------------------------------------------------- Types privés

    

};



//----------------------------------------- Types dépendants de <EtatTemplate>



#endif // ETATTEMPLATE
