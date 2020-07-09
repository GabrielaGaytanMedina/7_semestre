%{
#include<stdio.h>
#include<stdlib.h>	
#include "calculadora.tab.h"
int linea=1;
%}

%option noyywrap
dig [0-9]

%%
"+" {return MAS;}
"-" {return MIN;}
"*" {return MUL;}
"/" {return DIV;}
"%" {return MOD;}
"(" {return LPAR;}
")" {return RPAR;}
{dig}+ {
		yylval.numero.ival = atoi(yytext);
		yylval.numero.tipo=1;
		return NUM;
		}
[ \t]+ { }
"\n" {
	linea++;
	return LN;
		}
. {printf("Error lexico: en la linea %d\n",linea);}
%%

