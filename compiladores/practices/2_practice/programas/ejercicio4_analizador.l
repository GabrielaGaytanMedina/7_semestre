%{
	#include<stdio.h>
	#include<string.h>
%}
%option yylineno

entero [0-9]+
real   {entero}.{entero}
id     [a-zA-Z_][a-zA-Z0-9_]*
caux   [^\"]*
cad    \"{caux}\"
pres   if|then|else|while|do|case|is|void|true|false|begin|end|not
arit	[+"-"*/%]
rela    ¿|¡|¡¿|= 
asig	:=
espc    [{}();,]
espacio [" "\n\t]
coment --.*\n
coaux	{espacio}*.*{espacio}*
comult "<*"{coaux}*"*>"

%%
"\n"	{printf("<space>\n");  fprintf(yyout,"%s %s \n","<space>",yytext); }
{real} {printf("<real> %s \n",yytext); fprintf(yyout,"%s %s \n","<real>",yytext); }
{entero} {printf("<entero> %s \n",yytext); fprintf(yyout,"%s %s \n","<entero>",yytext);}
{pres} {printf("<reservada> %s \n",yytext); fprintf(yyout,"%s %s \n","<reservada>",yytext);}
{arit} {printf("<aritmetico> %s \n",yytext);fprintf(yyout,"%s %s \n","<aritmetico>",yytext);}
{asig} {printf("<asignacion> %s \n",yytext);fprintf(yyout,"%s %s \n","<asignacion>",yytext);}
{rela} {printf("<relacional> %s \n",yytext);fprintf(yyout,"%s %s \n","<relacional>",yytext);}
{espc} {printf("<especial> %s \n",yytext);fprintf(yyout,"%s %s \n","<especial>",yytext);}
{coment} {printf("<coment> %s \n",yytext);fprintf(yyout,"%s %s \n","<coment>",yytext);}
{comult} {printf("<comult> %s \n",yytext);fprintf(yyout,"%s %s \n","<comult>",yytext);}
{cad} {printf("<cadena> %s \n",yytext);fprintf(yyout,"%s %s \n","<cadena>",yytext);}
{id} {printf("<id> %s \n",yytext);fprintf(yyout,"%s %s \n","<id>",yytext);}
{espacio} {printf("<space> %s \n",yytext);fprintf(yyout,"%s %s \n","<space>",yytext);}

. {printf("error de caracter %s en linea %d \n" ,yytext,yylineno); fprintf(yyout,"error de caracter %s en la linea %d \n",yytext,yylineno);}

%%


int main(int argc, char **argv)
{
    FILE *f,*o;
    f = fopen(argv[1],"r");
    o = fopen("salida","w");
    yyin = f;
    yyout = o;
    yylex();

    fclose(yyin);
    fclose(yyout);

}
