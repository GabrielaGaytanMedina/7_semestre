%{
#include <stdio.h>
%}

cadena [^" ""\n"]+
espacio [" "\n\t]
cadesp {cadena}{espacio}

%%

{cadesp} {printf("%s",yytext); EscribirM(yytext);}


. {printf("Error");}
%%

void EscribirM(char n[]){ /*funcion que nos permite escribir las letras en mayusculas e escribirlas en el archivo */
	int j=0;
	while(n[j]){
		if(n[j]!=" ")
			n[j] = toupper(n[j]); /*mayusculas*/
		else
			fprintf(yyout," ");
		j++;
	}

	fprintf(yyout,n);

}

int main(int argc, char **argv) {
    FILE *f,*o;
    f = fopen(argv[1],"r");
    o = fopen("salida","w");
    yyin = f;
    yyout = o;
    yylex();

    fclose(yyin);
    fclose(yyout);
	

}