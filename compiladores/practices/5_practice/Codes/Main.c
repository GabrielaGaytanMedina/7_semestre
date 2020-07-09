#include"tablaTipos.h"
#include"tablaSimbolos.h"
#include"pilaTablaSimbolos.h"
#include"pilaTablaTipos.h"
#include <stdio.h>
#include <stdlib.h>
/*Prueba de estructuras
	Creacion Reza Chavarria Sergio Gabriel 3/nov/2019
*/
int main(){
	/*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
	/*Prueba de tabla de simbolos*/
		/*Prueba parametros*/
		printf("/*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/\n");
		printf("Prueba de la tabla de simbolos\n");
	    param *p1=crearParam(10);
	    param *p2=crearParam(5);
		printf("Parametro 1: %d\nParametro 2: %d\n",p1->tipo,p2->tipo);
		
		/*Creacion de lista de parametros*/
		printf("Lista de parametros");
		listParam *lp=crearLP();
		add(lp,20);
		add(lp,13);
		add(lp,3);
		//print_list(*lp);
		printf("\nNumero de parametros %d",getNumListParam(lp));
		/*Eliminacion*/
		borrarListParam(lp);
		print_list(*lp);
		
		
		add(lp,4);
		add(lp,5);
		/*Creacion de un simbolo*/
		printf("Creacion de simbolos\n");
		symbol *s1=crearSymbol(0,1,2,"prueba",lp);
		print_sym(s1);
		symbol *s2=crearSymbol(4,4,5,"seraBorrado",lp);
		print_sym(s2);
		
		
		/*Eliminacion*/
		borrarSymbol(s2);
		print_sym(s2);
		s2=crearSymbol(4,4,5,"Nuevo elemento",lp);
		
		/*Creacion de tabla*/
		symtab *st1=crearSymTab();
		/*Simbolos*/
		symbol *sst0=crearSymbol(9,3,10,"Nuevo elemento 0",lp);
		symbol *sst1=crearSymbol(10,4,2,"Nuevo elemento 1",lp);
		symbol *sst2=crearSymbol(4,4,5,"Nuevo elemento 2",lp);
		symbol *sst3=crearSymbol(2,5,3,"Nuevo elemento 2",lp);
		symbol *sst4=crearSymbol(1,4,2,"Nuevo elemento 1",lp);
		
		insertar(st1,sst0);
		insertar(st1,sst1);
		insertar(st1,sst2);
		insertar(st1,sst3);
		insertar(st1,sst4);
		printf("Tabla de simbolos despues de la insercion");
		print_symtab(st1);
		
		
		/*Obtencion de informacion de la tabla*/
		printf("\nLugar de Nuevo elemento 1: %d",buscar(st1,"Nuevo elemento 1"));
		printf("\nTipo de valor de  Nuevo elemento 2: %d", getTipoVar(st1," Nuevo elemento 2"));
		printf("\nDireccion de Nuevo elemento 0: %d", getDir(st1," Nuevo elemento 2"));
		print_list(*getListParam(st1," Nuevo elemento 2"));
		
		
	/*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
	/*Prueba tabla de tipos*/
		printf("\n/*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/\n");
		printf("\n\nPrueba de tabla de tipos");
		//Estatico
		tipo t;
	    t.type = 1; // Creamos un tipo para probar las funciones
	    tipoBase tb;
	    tb.est = false;
	    tb.t = t; // creamos un tipo base para probar
	    typetab * lista = crearTypeTab(); // creamos una tabla de tipos, esto podria ir en una funcion
	    
	    //Tipo de una tabla
		tipo b;
	    b.estructura=st1;
	    tipoBase tb2;
	    tb2.est=true;
	    tb2.t=b;
		typetab *lista2 =crearTypeTab();
		// creamos 4 tipos de ejemplo
	    type *hola = crearTipo("hola", tb,1,3,4);
	    type *mundo = crearTipo("mundo", tb,10,23,2);
	    type *adios = crearTipo("adios", tb2,1,45,23);
	    type *cuqui = crearTipo("cuqui", tb2,23,1,2);
	    // Metemos los tipos a la lista o tabla de Tipos
	    printf("%d",insertarTipo(lista, hola));
	    printf("%d\n",insertarTipo(lista, adios));
	    insertarTipo(lista2, mundo);
	    insertarTipo(lista2, cuqui);
	    // Mostramos una tabla de tipos para comprobar que funcionen las bibliotecas
	    printT(lista);
	    printf("\nNombre del tipo en la posicion 2: %s\n", getNombre(lista,0));
    	
    	printT(lista2);
		printf("\nSize de lista 2: %d\n",getNumElem(lista2, 1 ));
	
	
		
	/*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
	/*Prueba de pila de tabla de simbolos*/
		printf("\n/*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/\n");
		printf("Prueba de pila de tabla de simbolos");
		
		symstack *newSymStack=crearSymStack();
		
		//Segunda tabla
		symtab *st2=crearSymTab();
		symbol *ssst0=crearSymbol(7,3,10,"ELE",lp);
		symbol *ssst1=crearSymbol(1,4,23,"MENTO",lp);
		symbol *ssst2=crearSymbol(2,6,5,"SOLO",lp);
		insertar(st2,ssst0);
		insertar(st2,ssst1);
		insertar(st2,ssst2);
		
		/*Ingresar las tablas a la pila*/
		
		insertarSymTab(newSymStack,st1);
		
		insertarSymTab(newSymStack,st2);
		
		printf("\nImpresion de la cima despues de ingresar las 2 tablas");
		print_symtab(getCima(newSymStack));
		
		/*Prueba de pop*/
		printf("\nPop a la pila\n");
		print_symtab(sacarSymTab(newSymStack));
		printf("\nImpresion de la cima nueva\n");
		print_symtab(getCima(newSymStack));
		printf("\nPop a la pila e impresion de la nueva cima\n");
		print_symtab(sacarSymTab(newSymStack));
		printf("\nImpresion de la cima nueva. Sin elementos\n");
		print_symtab(getCima(newSymStack));
		
	
	/*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/
	/*Prueba de pila de tabla de tipos*/
		printf("\n/*+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++*/\n");
		printf("Prueba de la pila de tabla de simbolos");
		typestack *newTypeStack=crearTypeStack();
		/*Agregado de las listas ya creadas*/
		insertarTypeTab(newTypeStack,lista);
		insertarTypeTab(newTypeStack,lista2);
		
		printf("\nCima de la Pila\n");
		printT(getCimaType(newTypeStack));
		printf("\nPop y nuevo tope\n");
		printT(sacarTypeTab(newTypeStack));
		printT(getCimaType(newTypeStack));
		printT(sacarTypeTab(newTypeStack));
		printT(getCimaType(newTypeStack));
	
	return 0;
}
