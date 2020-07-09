#include"pilaTablaSimbolos.h"

/*
*Develop by: Murrieta Villegas Alfonso 11-01-2019
*2° version develop by: Valdespino Mendieta Joaquín 11-03-2019
*/


//Creacion de la pila de tabla de simbolos
symstack *crearSymStack(){
	symstack *s=(symstack*)calloc(1,sizeof(symstack));
	s->num=0;
	s->root=NULL;
	return s;
}
//Liberacion de memoria
void borrarSymStack(symstack *st){
	free(st);
	st->root=NULL;
}
//Ingreso de tablas a la pila
void insertarSymTab(symstack *st,symtab *sym){
	//caso en el que la pila esté vacia
	if(st->root==NULL){
		st->root=sym;
		st->num++;
	}else{
	//Caso en el que la pila ya contenga elementos, el tope será la raíz
		sym->next=st->root;
		st->root=sym;
		st->num++;
	}
	
}

//La raiz actua como el tope
symtab *getCima(symstack *ss){
	//Regresa la raiz de la "lista", que actuará como el tope
	if(ss->root!=NULL)
		return ss->root;
	return NULL;
}

symtab *sacarSymTab(symstack *ss){
	
	//Caso en el que no exista nada en la pila
	if(isEmpty(ss)==1){
		printf("La pila está vacía");
		return NULL;
	}
	else{
		//Caso en el que la pila tiene elementos
		symtab *nuevo_root=NULL;
		symtab *aux=ss->root;
		nuevo_root=aux->next;
		ss->root=nuevo_root;
		ss->num--;
		return aux;
	
	}
	
}
/*Funcion para saber si se tienen elementos en la pila*/
int isEmpty(symstack *p){
	if(p->num==0)
		return 1;
	return 0;
}

