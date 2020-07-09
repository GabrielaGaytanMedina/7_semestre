%{ 
#include <stdio.h> 
int letras=0; //contabilzar fin 
int palabras=0;
int lineas =0; //contabilizar la ultima
%}

cadena [^" ""\n"]+ /*expresion para cualquier cadena*/
espacio " "|\t  /*se omite el el salto para contabilizar las lineas*/

%%

{cadena} {printf("%s \n",yytext); Contarletra(yytext); palabras++;}
{espacio} {/*printf("%s \n",yytext);*/ letras++;}
"\n"  {lineas++; letras++;} /*cuenta las lineas*/
\"	  {letras++;}

%%

void Contarletra(char n[]){ /*funcion que permite contar las letras de una cadena*/
	int j=0;
	while(n[j]){
		if(n[j]!=" "){
			letras++;
		}
		
		j++;
	}

}


int main(int argc, char **argv) {
    FILE *f;
    f = fopen(argv[1],"r");
    
    yyin = f;
    yylex();

    printf("%d %d %d \n",lineas,palabras,letras); /*impresion en formato del comando wc*/

    fclose(yyin);

	

}