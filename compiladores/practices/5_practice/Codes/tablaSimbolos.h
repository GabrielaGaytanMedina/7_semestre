#ifndef TABLASIMBOLOS_H
#define TABLASIMBOLOS_H


#include<stdlib.h>
#include<stdio.h>
#include<string.h>

/*Creacion: Reza Chavarria Sergio Gabriel 1/nov/2019 15pm
*Modificacion Valdespino Mendieta Joaquin  2/nov/2019 17pm

*/
typedef struct _param param;
struct _param{
	int tipo;
	param *next;
};

typedef struct _listParam listParam;
struct _listParam{
	param *root;
	int num;

};
/*Retornoa un apuntador a una variable Param*/
param *crearParam(int tipo);
/*Borrar param*/
void borraParam(param *p);
/*Retorna un apuntador a la variable listParam*/
listParam *crearLP();
/* Agrega al final de la lista el parametro e incrementa num*/
void add( listParam * lp , int tipo) ;
/* Borra toda la lista , liberala memoria */
void borrarListParam ( listParam * lp) ;
/* Cuenta el numero de parametros en la lista */
int getNumListParam ( listParam * lp) ;

void print_list(listParam lista);


typedef struct _symbol symbol ;
struct _symbol {
	char * id;
	int tipo ;
	int dir ;
	int tipoVar;
	listParam *params ;
	symbol * next ;
} ;

/*Retorna un apuntador a una variable symbol*/
symbol *crearSymbol(int dir, int tipo,int tipovar, char * id,listParam *params);
/*Borra symbol, libera memoria*/
void borrarSymbol(symbol *s);
/*Impresion de la informacion del simbolo*/
void print_sym(symbol *s);


typedef struct _symtab symtab;
struct _symtab{
	symbol *root;
	int num;
	symtab *next;
} ;


/*Retorna un apuntador a una variable symtab*/

symtab *crearSymTab();
/* Borra toda la lista , libera la memoria */
void borrarSymTab(symtab *st);

/*Obtencion de parametros de los simbolos de la tabla*/
int insertar(symtab *st, symbol *sym);
int buscar(symtab *st,char *id);
int getTipo(symtab *st, char *id);
int getTipoVar(symtab *st, char *id);
int getDir(symtab *st, char *id);
listParam *getListParam (symtab *st,char *id);
int getNumParam(symtab *st, char *id);
void print_symtab(symtab *st);





#endif







