#ifndef PILATABLASIMBOLOS_H
#define PILATABLASIMBOLOS_H
/*Creacion de pila de tablas de tipos
Reza Chavarria Sergio Gabriel 
Cardenas Cardenas Jorge
2/nov/2019 
Modificacion 
Valdespino Mendieta Joaquin 
Murrieta Villegas Alfonso
3/nov/2019
*/

#include"tablaSimbolos.h"
typedef struct _symstack symstack;
//Creacion de las pilas de tablas de simbolos
struct _symstack{
	symtab *root;
	int num;
};

/*Creacion de memoria de la pila de simbolos*/
symstack *crearSymStack();
/*Revision si la pila está vacia*/
int isEmpty(symstack *p);
/*Liberacion de memoria de la pila*/
void borrarSymStack(symstack *st);
/*Insercion de tabla de simbolos en la pila*/
void insertarSymTab(symstack *st,symtab *sym);
symtab* getCima(symstack *ss);
symtab*sacarSymTab(symstack *ss);

#endif

