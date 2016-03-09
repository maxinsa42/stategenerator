/*************************************************************************
   SymboleType  -  Une énumération contenant tous les types de symboles.
 -------------------
 début                : 06/03/2016
 copyright            : (C) 2016 par mgaillard
 ************************************************************************/

//-- Interface de l'énumération' <SymboleType> (fichier symboletype.h) --

#if ! defined ( SYMBOLETYPE_H )
#define SYMBOLETYPE_H

//------------------------------------------------------------------------
// Rôle de l'énumération' <SymboleType>
// Contient tous les types de symboles.
//------------------------------------------------------------------------

enum SymboleType
{
    ERROR,
    VAR,
    VARp,
    CONST,
    ECRIRE,
    LIRE,
    POINT_VIRGULE,
    AFFECTATION,
    EGAL,
    VIRGULE,
    PLUS,
    MOINS,
    DIVISE,
    MULTIPLIE,
    VALEUR,
    IDENTIFIANT,
    EXPRESSION,
    EXPRESSION_MOINS,
    EXPRESSION_PLUS,
    PDECL,
    PDECL_CONST,
    PDECL_VAR,
    PIN,
    PIN_AFFECTER,
    PIN_ECRIRE,
    PIN_LIRE,
    PROGRAMME,
    TERME,
    TERME_DIVISION,
    TERME_MULTIPLICATION,
    FACTEUR,
    OUVRE_PAR,
    FERME_PAR,
    FIN,
    CONSTp,
};

#endif // SYMBOLETYPE_H
