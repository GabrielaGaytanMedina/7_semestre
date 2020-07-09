%{
    #include <stdio.h>
    extern int yylex();
    extern int yylineno;
    extern char *yytext;
    void yyerror(char * msg);
%}

%token NUM
%token PYC
%token IGUAL
%token COMA
%token INT
%token FLOAT
%token ID

%left ADD
%left MULT

%nonassoc LPAR RPAR

%start p

%%

p : d s;
d : d t l PYC
    |
    ;
t : INT
    |FLOAT
    ;
l : l COMA ID 
    |ID
    ;
s : s s
    |ID IGUAL e PYC
    ;
e : e ADD e
    |e MULT e
    |LPAR e RPAR
    |ID
    |NUM
    ;

%%

void yyerror(char * msg){
    printf("%s en la linea %d, %s\n", msg, yylineno, yytext);
}