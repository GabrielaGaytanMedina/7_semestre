%{
    #include <stdio.h>
    #include "scanner.tab.h"
%}

%option noyywrap
%option yylineno

id [A-Za-z_][A-Za-z0-9_]*
ent [0-9]
real [0-9]*.[0-9]+
num {ent}|{real}

%%
{num} {return (NUM);}
"+" {return (ADD);}
"*" {return (MULT);}
"(" {return (LPAR);}
")" {return (RPAR);}
";" {return (PYC);}
"," {return (COMA);}
"=" {return (IGUAL);}
int {return (INT);}
float {return (FLOAT);}
{id} {return (ID);}
[" "\t\n] {}
. {}

%%