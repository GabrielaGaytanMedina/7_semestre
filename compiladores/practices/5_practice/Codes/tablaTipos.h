#ifndef TABLATIPOS_H
#define TABLATIPOS_H



#include "tablaSimbolos.h"
#include <stdbool.h>

/*Creacion: Reza Chavarria Sergio Gabriel 1/nov/2019 16pm
*Cardenas Cardenas Jorge  2/nov/2019 14pm
*
*/

typedef struct _type type;
typedef struct _tipoBase tipoBase;
typedef union _tipo tipo;

union _tipo{
	int type;
	symtab *estructura;
};

struct _tipoBase{
	bool est;
	tipo t;
};

struct _type{
	int id;
	char * nombre;
	tipoBase tb;
	int tamBytes;
	int numElem;
	type *next;
};

typedef struct _typetab typetab;

struct _typetab{
	type *root;
	int num;
	typetab *next;
};

/*Prototipos*/
//Creacion de tipos
type *crearTipo(char nombre [10], tipoBase tb, int tam, int elem,int id);
//Eliminacion de tipos
void borrarType(type *t);

//creacion de una tabla de tipos
typetab *crearTypeTab();

//Insertar tipo en la tabla
int insertarTipo(typetab *tt,type *t);
//Obtencion del indice del tipo
tipoBase getTipoBase(typetab *tt, int id);
//Obtiene el tamaño
int getTam(typetab *tt, int id);
//Obtiene el numero de elementos
int getNumElem(typetab *tt, int id);
//Obtiene el nombre del tipo
char* getNombre(typetab *tt, int id);
//Impresion de los tipos de la tabla
void printT(typetab * tt);

#endif
