#ifndef PILATABLATIPOS_H
#define PILATABLATIPOS_H

/*Creacion de pila de tablas de tipos
Reza Chavarria Sergio Gabriel 
Cardenas Cardenas Jorge
2/nov/2019 
Modificacion 
Valdespino Mendieta Joaquin 
Murrieta Villegas Alfonso
3/nov/2019
*/
#include"TablaTipos.h"
typedef struct _typestack typestack;

//Creacion de las pilas de tablas de tipos
struct _typestack{
	typetab *root;
	int num;

};
/*Creacion de la pila*/
typestack *crearTypeStack();
/*Eliminacion de la pila*/
void borrarTypeStack(typestack *ts);
/*Insercion de una tabla a la pila*/
void insertarTypeTab(typestack *ts, typetab *sym);
/*Obtencion del tope de la pila indicada*/
typetab* getCimaType(typestack *ts);
/*Funcion pop del tope de la fila*/
typetab* sacarTypeTab(typestack *ts);
/*Revisa si la pila está vacia*/
int isEmptyTypeStack(typestack *p);


#endif
