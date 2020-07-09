#include"tablaSimbolos.h"
/*
*Develop by: Murrieta Villegas Alfonso 11-01-2019
*2° version develop by: Valdespino Mendieta Joaquín 11-03-2019
*/

//Creacion de los parametros
param *crearParam(int tipo){
	param *tmp= (param*)calloc(1,sizeof(param));
	tmp->tipo=tipo;
	tmp->next=NULL;
	return tmp;
	
}

/*Eliminacion de parametros*/
void borraParam(param *p){
	free(p);
}

//Creacion de la lista de parametros
listParam *crearLP(){
	listParam *lp=(listParam*)calloc(1,sizeof(listParam));
	lp->root=NULL;
	return lp;
}

/*Ingreso de datos en la lista de parametros*/
void add( listParam *lp , int tipo){
	
	if(lp->root==NULL){
		
		param *p = (param*)malloc(sizeof(param));
		p->next=NULL;
		p->tipo=tipo;
    	lp->root = p;
	}else
	{/*Caso en el que la lista ya contenga elementos los agregará al final*/
		param *par = lp->root;
		//Recorre la lista hasta encontrar el último parametro
		while (par->next != 0) {
       		par = par->next;
 		}
		param *nuevoPar;
		nuevoPar = crearParam(tipo);
		nuevoPar->next=NULL;
 		par->next = nuevoPar;
	}	
}

/*Eliminacion de datos de la lista*/
void borrarListParam (listParam *lp){
	
	while(lp->root->next!=0){
		lp->root->tipo=0;
		param *pc=lp->root;
		lp->root=lp->root->next;
		pc->next=NULL;
	}
	lp->root=NULL;
	
}

/*Obtencion de la cantidad de parametros*/
int getNumListParam ( listParam *lp){
	
	int cant=0;
	if(lp->root==NULL){
		return 0;
	}else{
		param *pcant=lp->root;
		while(pcant->next!=0){
			pcant=pcant->next;
			cant++;
		}
		cant++;
		return cant;
	}
	
	
}
/*Imrpesion de datos de la lista de parametros*/
void print_list(listParam lista) {
    if(lista.root==NULL){
    	printf("\nLA LISTA ESTA VACIA \n");
    }
    else{
    	printf("\nLos elementos de la lista son: \n");
    	param *current = lista.root;
   		while (current != 0) {
        	printf("%d, ",current->tipo);
			current = current->next;
   	 	}
	}
}

/*Creacion de simbolos*/
symbol *crearSymbol(int dir, int tipo,int tipovar, char id[32],listParam *params){
	symbol *sy=(symbol*)calloc(1,sizeof(symbol));
	sy->dir=dir;
	sy->tipo=tipo;
	sy->tipoVar=tipovar;
	sy->id=id;
	sy->next=NULL;
	sy->params=params;
	return sy;
}
/*Borra symbol, libera memoria*/
void borrarSymbol(symbol *s){
	s->dir=0;
	s->tipo=0;
	s->tipoVar=0;
	s->id='\0';
	s->next=NULL;
	s->params=NULL;
}

/*Impresion de la informacion de simbolos*/
void print_sym(symbol *s){
	if(s->id!='\0'){
		printf("\nDireccion: %d",s->dir);
		printf("\nTipo no. :%d",s->tipo);
		printf("\nTipo de valor: %d", s->tipoVar);
		printf("\nID: %s\n",s->id);
		print_list(*s->params);	
	}else if(s->id=='\0'){
		
		printf("\nNo hay simbolo existente");			
	}
	
}

/*Creacion de la tabla*/
symtab *crearSymTab(){
	symtab *st= (symtab*)calloc(1,sizeof(symtab));
	st->num=0;
	st->root=NULL;
	st->next=NULL;
	return st;
}

/*Eliminacion de tabla junto con los simbolos*/
void borrarSymTab(symtab *st){
	
	symbol *s=st->root;
	while(s->next!=NULL){
		borrarSymbol(s);
		s=s->next;
	}
	st->root=NULL;
}

/*Insercion de simbolos en la tabla*/
int insertar(symtab *st, symbol *sym){
    if(st->root==NULL){
        st->root=sym;
        st->root->next=NULL;
        st->num++;
        return 0; //agregado
            //st no agreado
    }else
    {/*Caso en el que la lista ya contenga elementos los agregará al final*/
        symbol *s = st->root;
        
        int posicion = 0;
        //Recorre la lista hasta encontrar el último elemento
        while (s->next != NULL) { //cambiado//
    		/*Revisa si el id ya fue introducido a la lista*/
    		
			if(strcmp(sym->id,s->id)!=0){
				s = s->next;
           		posicion++; //agregado 
				
			}else{
				printf("\nSimbolo no agregado\n");
				return -1;
			}
           	
         }
        if(strcmp(sym->id,s->id)==0){
        	
			printf("no agregado");
			return -1;
		}else{
			posicion ++; //agregado
	        s->next = sym;
	        sym->next=NULL;
	        st->num++;
	        return posicion; //agregado 
		}
    }

}
	
/*Busqueda de simbolos en la tabla*/
int buscar(symtab *st,char *id){
	
	if(st->root==NULL){
		printf("Tabla vacia");
		return -1;
	}else{
		symbol *par = st->root;
		int i=0;
		while (par->next != 0&&strcmp(id,par->id)!=0) {
       		par = par->next;
       		i++;
 		}
 		return i;
	}
}
/*Obtencion de tipo del id*/
int getTipo(symtab *st, char *id){
	if(st->root==NULL){
		printf("Tabla vacia");
		return -1;
	}else{
		symbol *par = st->root;
		
		while (par->next != 0&&strcmp(id,par->id)!=0) {
       		par = par->next;
 		}
 		return par->tipo;
	}
}

/*Obtencion del valor del id*/
int getTipoVar(symtab *st, char *id){
	if(st->root==NULL){
		printf("Tabla vacia");
		return -1;
	}else{
		symbol *par = st->root;
		
		while (par->next != 0&&strcmp(id,par->id)!=0) {
       		par = par->next;
 		}
 		return par->tipoVar;
	}
}

/*Obtiene direccion*/
int getDir(symtab *st, char *id){
	if(st->root==NULL){
		printf("Tabla vacia");
		return -1;
	}else{
		symbol *par = st->root;
		
		while (par->next != 0&&strcmp(id,par->id)!=0) {
       		par = par->next;
 		}
 		return par->dir;
	}
}
/*Obtencion de la lista de parametros*/
listParam *getListParam (symtab *st,char *id){
	if(st->root==NULL){
		printf("Tabla vacia");
		return NULL;
	}else{
		symbol *par = st->root;
		
		while (par->next != 0&&strcmp(id,par->id)!=0) {
       		par = par->next;
 		}
 		return par->params;
	}
}

/*Obtencion del numero de parametros*/
int getNumParam(symtab *st, char *id){
	if(st->root==NULL){
		printf("Tabla vacia");
		return -1;
	}else{
		symbol *par = st->root;
	
		while (par->next != 0) {
			//Si se tiene el id regresa la cantidad de parametros de la lista
       		if(strcmp(par->id,id)==0){
       			return getNumListParam(par->params);
			}
			par = par->next;
       		
 		}
 		return -1;
	}
}
/*Impresion de la tabla*/
void print_symtab(symtab *st){
	if(st!=NULL){
		symbol *sym=st->root;
		int i=0;
		while(sym!=0){
			printf("\n%d Simbolo:\n",i);
			print_sym(sym);
			sym=sym->next;
			i++;
		}
	}else{
		printf("Tabla inexistente");
	}
	
}


