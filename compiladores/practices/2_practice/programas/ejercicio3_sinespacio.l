%{
#include <stdio.h>
%}

cadena [^" ""\n""\t"]+
espacio [" "\n\t]

%%

{cadena} {printf("%s",yytext); fprintf(yyout,yytext);}
{espacio} {}


%%



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