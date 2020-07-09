#include "tablaTipos.h"
#include <stdlib.h>
#include <stdio.h>

/*Creacion: Reza Chavarria Sergio Gabriel 1/nov/2019 16pm
*Cardenas Cardenas Jorge  2/nov/2019 14pm
*
*/


type *crearTipo(char nombre [10], tipoBase tb, int tam, int elem,int id){ //Creacion del Tipo
    type *tmp = (type*)calloc(1, sizeof(type));
    tmp->nombre = nombre;
    tmp->tb = tb;
    tmp->next = NULL;
    tmp->tamBytes=tam;
    tmp->numElem=elem;
    tmp->id=id;
    return tmp;    
}

void borrarType(type *t){ // libera el tipo de dato, No afecta la continuidad en la lista?
    free(t); 
}
//Crea un typetab
typetab *crearTypeTab(){
	typetab *tmp=(typetab*)calloc(1,sizeof(typetab));
	tmp->root=NULL;
	tmp->num=0;
	tmp->next=NULL;
	return tmp;
	
}


// Insertamos el tipo en la lista o tabla de tipos
int insertarTipo(typetab *tt,type *t){
    type *tmp = tt->root;
    if(tmp == NULL){ // Si la lista esta vacia
        t->id = 0; // Le asignamos el id 0
        tt->root = t; // Le asignamos la primera fila
        tt->num = 1; // Ponemos el contador en 1
        return tt->num - 1; // Regresa el indice donde se guardo el tipo, comienza en cero
    }
    while(tmp->next != NULL) // Vamos hasta el ultimo elemento de la tabla
        tmp = tmp->next;
    t->id = tt->num ; // le asignamos un id respecto al anterior
    tmp->next = t; // Guardamos la fila
    return tt->num++; // Regresamos el indice donde se inserto e Incrementamos el numero de filas.
}

tipoBase getTipoBase(typetab *tt, int id){
    type *tmp = tt->root;
    int i = 0;
    while(tmp != NULL && i++ < id) // Llegamos al elemento indicado por el indice, mientras existan elementos en la lista
        tmp = tmp->next;
    if(tmp != NULL)// Si tmp no es nulo, es que encontramos el id deceado
        return tmp->tb;
    return tmp->tb;
}

// Lo mismo que para getTipoBase, pero con el tamaño del tipo
int getTam(typetab *tt, int id){ 
    type *tmp = tt->root;
    int i = 0;
    while(tmp != NULL && i++ < id)
        tmp = tmp->next;
    if(tmp != NULL)
        return tmp->tamBytes;
    return -1;
}

// Lo mismo que para getTipoBase, pero con el Numero de elementos
int getNumElem(typetab *tt, int id){ 
    type *tmp = tt->root;
    int i = 0;
    
    if(tmp == NULL){
        return -1;
	}else{
		while(tmp != NULL && i++ < id)
        	tmp = tmp->next;
        return tmp->numElem;
	}
	return -1;
}

// Lo mismo que para getTipoBase,pero con el nombre del tipo
char* getNombre(typetab *tt, int id){ 
    type *tmp = tt->root;
    int i = 0;
    while(tmp != NULL && i++ < id)
        tmp = tmp->next;
    if(tmp != NULL)
        return tmp->nombre;
    return NULL;
}

// Funcion auxiliar para poder mostrar la tabla de tipos imprime id y nombre
void printT(typetab * tt){ 
    if(tt == NULL)
        return;
    type *tmp = tt->root;
    printf("Tabla de Tipos\n");
    printf("-------------------\n");
    printf("| id | Nombre     |\n");
    printf("-------------------\n");
    while(tmp != NULL){
        printf("| %2d | %10s |\n",tmp->id, tmp->nombre);
    printf("-------------------\n");
        tmp = tmp->next;
    }
}
