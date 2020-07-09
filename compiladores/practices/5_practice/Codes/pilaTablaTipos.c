#include "pilaTablaTipos.h"
//creacion de la pila de la tabla de tipos
typestack *crearTypeStack(){
	typestack *s=(typestack*)calloc(1,sizeof(typestack));
	s->num=0;
	s->root=NULL;
	return s;
}
//liberacion de memoria 
void borrarTypeStack(typestack *ts){
	free(ts);
	ts->root=NULL;
}
//Ingreso de tablas a la pila
void insertarTypeTab(typestack *ts, typetab *sym){
	//caso en el que la pila esté vacia
	if(ts->root==NULL){
		ts->root=sym;
		ts->num++;
		
	}else{
	//Caso en el que la pila ya contenga elementos, el tope será la raíz
		sym->next=ts->root;
		ts->root=sym;
		ts->num++;
	}
}
typetab* getCimaType(typestack *ts){
	//Regresa la raiz de la "lista", que actuará como el tope
	if(ts->root!=NULL)
		return ts->root;
	return NULL;
}
//Pop de la pila
typetab* sacarTypeTab(typestack *ts){
	//Caso en el que no exista nada en la pila
	if(isEmptyTypeStack(ts)==1){
		printf("La pila está vacia");
		return NULL;
	}else{
	//Caso en el que la pila tiene elementos
		typetab *nuevo_root=NULL;
		typetab *aux=ts->root;
		nuevo_root=aux->next;
		ts->root=nuevo_root;
		ts->num--;
		return aux;
	}
}
/*Revisa si la pila está vacia*/
int isEmptyTypeStack(typestack *p){
	/*Revisa si la pila está vacia, regresa 1 si es verdad*/
	if(p->num==0)
		return 1;
	return 0;
}
