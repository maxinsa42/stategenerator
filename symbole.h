/*************************************************************************
             Symbole  -  Représente un symbole du langage.
 -------------------
 début                : 01/03/2016
 copyright            : (C) 2016 par mgaillard
 *************************************************************************/

//---------- Interface de la classe <Symbole> (fichier symbole.h) ------
#if ! defined ( SYMBOLE_H )
#define SYMBOLE_H

//--------------------------------------------------- Interfaces utilisées
#include "symboletype.h"

//------------------------------------------------------------- Constantes

//------------------------------------------------------------------ Types

//------------------------------------------------------------------------
// Rôle de la classe <Symbole>
// Représente un symbole du langage.
// 
//------------------------------------------------------------------------

class Symbole
{
//----------------------------------------------------------------- PUBLIC
    
public:
//----------------------------------------------------- Méthodes publiques
    virtual void Print() const = 0;
    // Mode d'emploi :
    // Affiche le Symbole.
    
//------------------------------------------------- Surcharge d'opérateurs
    operator int() const;
    // Mode d'emploi :
    // Permet de caster le Symbole en int.
    // L'entier retourné est l'identifiant du Symbole.
    
//-------------------------------------------- Constructeurs - destructeur    
    Symbole(SymboleType type);
    // Mode d'emploi :
    //
    
    virtual ~Symbole();
    // Mode d'emploi :
    //
    
//------------------------------------------------------------------ PRIVE
    
protected:
//----------------------------------------------------- Méthodes protégées
    
private:
//------------------------------------------------------- Méthodes privées
    
protected:
//----------------------------------------------------- Attributs protégés
    //L'identifiant du Symbole.
    SymboleType type;
private:
//------------------------------------------------------- Attributs privés

    
//---------------------------------------------------------- Classes amies
    
//-------------------------------------------------------- Classes privées
    
//----------------------------------------------------------- Types privés
    
};

//----------------------------------------- Types dépendants de <Symbole>

#endif // SYMBOLE_H
