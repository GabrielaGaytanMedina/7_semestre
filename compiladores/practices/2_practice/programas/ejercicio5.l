%{ 
#include <stdio.h>
#include<stdlib.h>
#include<string.h>
int size=0,i=0,j, aux1, aux2,size2;
int total=0;
char *st;

%}
Entero 0|[1-9][0-9]*
expresion (-?{Entero}(\ )+)*
inicio \n?" "*
%%
{inicio}?{expresion}A\n {
	
	size=strlen(yytext);
	for(i=0; i<size;i++){
		if(yytext[i]!='\n'||yytext[i]!=' '||yytext[i]!='A'){
			aux1=i;
			aux2=aux1;
			while(yytext[aux2]!=' '){
				aux2++;
			}		
			size2=aux2-aux1;
			st= (char *)realloc(st,size2*sizeof(int));
			for(j=0; j<size2;j++){
				st[j]=yytext[aux1+j];
			}
			total+=atoi(st);
			for(j=0;j<size2;j++)
				st[j]='\0';
			i=aux2;	
		}else{ 
			i++;
		}
	}
	printf("La linea es A: %s\n",yytext);
	printf("La suma es: %d\n",total);
	total=0;

}

{inicio}{expresion}B\n {printf("La linea es B: %s\n",yytext);}

. {printf("Error");}

%%

int main(int argc, char **argv){
	st= (char *)calloc(10,sizeof(char));
	FILE *f, *o;
	f=fopen(argv[1],"r");
	if(f!=NULL){
		yyin=f;
		yylex();
		fclose(yyin);
		return 0;	
	}else{
		printf("Archivo inexistente :c\n");
		return 0;
		
	}
}	
