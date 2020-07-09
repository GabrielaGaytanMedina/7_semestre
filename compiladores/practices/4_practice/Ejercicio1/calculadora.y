%{
	#include<stdio.h>
	void yyerror(char *msg);
	extern int yylex();
	extern int yylineno;
%}
%union{
	struct{
		int ival;
		int tipo;
	}numero;
}
%token<numero> NUM
%token LN

%left MAS
%left MIN
%left MUL
%left DIV
%left MOD

%nonassoc LPAR RPAR
%type<numero> expresion
%start line

%%

line: expresion LN {printf("El valor es %d\n", $1.ival);} line 
| expresion {printf("El valor es %d\n", $1.ival);}

expresion: expresion MAS expresion {$$.ival=$1.ival+$3.ival;} 
| expresion MIN expresion {$$.ival=$1.ival-$3.ival;}
| expresion MUL expresion {$$.ival=$1.ival*$3.ival;}
| expresion DIV expresion {$$.ival=$1.ival/$3.ival;}
| expresion MOD expresion {$$.ival=$1.ival%$3.ival;}
| LPAR expresion RPAR {$$=$2;}
| NUM{$$=$1;} 

%%

void yyerror(char *msg){
	printf("%s en la linea %d\n",msg,yylineno);
}

