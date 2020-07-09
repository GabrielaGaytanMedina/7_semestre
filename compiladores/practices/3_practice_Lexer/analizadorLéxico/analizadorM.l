%{
    /*TEAMANCOS-LEXER Version 1.0.0 
    FECHA DE ELABORACION 2/10/2019
    
    PROGRAMADO POR: 
    Cardenas Cardenas Jorge
    Murrieta Villegas Alfonso
    Reza Chavarría Sergio Gabriel
    Valdespino Mendieta Joaquín
    
    */

	#include<stdio.h>
    
    int estadoA =1; /* Estado para verificar si hay errores de comentarios no cerrados*/
    int cc =0; /*Variable para contador de caracteres "character counter"*/

%}
%option yylineno

/*EXPRESIONES REGULARES*/
entero [0-9]+
real   {entero}.{entero}
num {entero}|{real}
id     [a-zA-Z_][a-zA-Z0-9_]*
caux   [^\"]*
cad    \"{caux}\"
car    \'.\'
cb    [^*<>]*[" "\n\t]*
%x comentarioL
%x comentarioL2
%x comentarioM
%x comentarioM2

%%


"\n" {return 1; cc++; /*EXPRESIONES REGULARES, Token para saltos de linea*/}
{num} {return 2;cc+=yyleng;/*Token para números*/}
registro {return 3;cc+=yyleng;/*Tokens para palabras reservadas, linea 38 a 60*/}
inicio {return 4; cc+=yyleng;}
fin {return 5; cc+=yyleng;}
func {return 6;cc+=yyleng;}
ent {return 7; cc+=yyleng;}
real {return 8; cc+=yyleng;}
dreal {return 9;cc+=yyleng;}
car {return 10; cc+=yyleng;}
sin {return 11; cc+=yyleng;}
si {return 12; cc+=yyleng;}
entonces {return 13; cc+=yyleng;}
eino {return 14; cc+=yyleng;}
mientras {return 15; cc+=yyleng;}
hacer {return 16; cc+=yyleng;}
"mientras que" {return 17; cc+=yyleng;}
escribir {return 18; cc+=yyleng;}
leer {return 19; cc+=yyleng;}
devolver {return 20; cc+=yyleng;}
oo {return 21; cc+=yyleng;}
yy {return 22; cc+=yyleng;}
no {return 23; cc+=yyleng;}
verdadero {return 24; cc+=yyleng;}
falso {return 25; cc+=yyleng;}
":=" {return 26; cc+=yyleng;/*Tokens para operadores y caracteres especiales, linea 61 a 78*/}
"<" {return 27; cc+=yyleng;}
">" {return 28; cc+=yyleng;}
"<=" {return 29; cc+=yyleng;}
">=" {return 30; cc+=yyleng;}
"==" {return 31; cc+=yyleng;}
"<>" {return 32; cc+=yyleng;}
"+" {return 33; cc+=yyleng;}
"-" {return 34; cc+=yyleng;}
"*" {return 35; cc+=yyleng;}
"/" {return 36; cc+=yyleng;}
"%" {return 37; cc+=yyleng;}
"(" {return 38; cc+=yyleng;}
")" {return 39; cc+=yyleng;}
"[" {return 40; cc+=yyleng;}
"]" {return 41; cc+=yyleng;}
"." {return 42; cc+=yyleng;}
"," {return 43; cc+=yyleng;}

"--" {BEGIN(comentarioL); cc+=yyleng; /* PARTE DEDICADA A DETECTAR COMENTARIOS, de la linea 80 a 86 */}
<comentarioL>[^"-"\n]* {BEGIN(comentarioL2); cc+=yyleng;}
<comentarioL2>\n {BEGIN(INITIAL); cc+=yyleng;}

"<*" { BEGIN(comentarioM); estadoA=0; cc+=yyleng;}
<comentarioM>{cb}* {BEGIN(comentarioM2); cc+=yyleng;}
<comentarioM2>"*>" { estadoA =1; BEGIN(INITIAL); cc+=yyleng; }

{car} {return 44; cc+=yyleng;/*Token para caracter*/}
{cad} {return 45; cc+=yyleng;/*Token para cadena*/}
{id}  {return 46; cc+=yyleng;/*Token para identificardor*/}

[" "\t]*    {cc++;}

. {printf("error de caracter \"%s\" en linea %d y #caracter %d \n" ,yytext,yylineno,cc); cc+=yyleng; /*fprintf(yyout,"error de caracter %s en la linea %d \n",yytext,yylineno);*/}

%%

void tokens(); /* Prototipo de la función tokens */


/*
FUNCIÓN "main" 

-> Funciones que se llaman:
- A través de fclose y fopen es como se abren los archivos de entrada y salida.

-> Variables empleadas:
- Mediante la variable "estadoA" se verifica si los comentarios fueron cerrados.
- la variable global yyin contiene el apuntador al archivo de entrada 
- La variable global yyout contiene el apuntador al archivo de salida  


*/

int main(int argc, char **argv){

    FILE *f,*o; //Apuntadores a los archivos, "f" es para el de entrada y "o" es para el de salida
    
    if(argv[1]!=NULL){
        f = fopen(argv[1],"r");
        o = fopen("salida.txt","w");
    }
    else{
        printf("sin archivo de entrada");
        return 0;
    }
        
    yyin = f;
    yyout = o;
    tokens();//Llamamos función tokens

    if(estadoA==0){
        printf("error - comentario no cerrado \n");
    }

    fclose(yyin);
    fclose(yyout);
    return 0;
}


/*
FUNCIÓN "tokens()" 

- La función tokens es la encargada de imprimir tanto en consola como en el archivo de salida cada uno de los tokens del archivo de entrada.
- Para definir el fin de la función utilizamos la variable entera "tok" la cual se le asigna el valor de yylex() para de esa forma determinar 
  el token y terminar el ciclo además del llamado a esta función.
- En total hay 46 casos correspondientes a un token distinto.

NOTA: Los errores solo se muestran en consola (Se pueden habilitar al descomentar en linea 94).

*/

void tokens(){

    int tok;
    while((tok=yylex())!=0)
    {
        
        switch(tok)
        {
            case 1:
                printf("<\\n>\n");
            	fprintf(yyout,"<\\n> \n");
                break;
            case 2:
                printf("<num, %s > \n",yytext);
            	fprintf(yyout,"<num, %s > \n",yytext);
                break;                
            case 3:
                printf("<PR, registro>\n");
            	fprintf(yyout,"<PR, registro>\n");
                break;
            case 4:
                printf("<PR, inicio>\n");
            	fprintf(yyout,"<PR, inicio>\n");
                break;
            case 5:
                printf("<PR, fin>\n");
            	fprintf(yyout,"<PR, fin>\n");
                break;
            case 6:
                printf("<PR, func>\n");
            	fprintf(yyout,"<PR, func>\n");
                break;
            case 7:
                printf("<PR, ent>\n");
            	fprintf(yyout,"<PR, ent>\n");
                break;
            case 8:
                printf("<PR, real>\n");
            	fprintf(yyout,"<PR, real>\n");
                break;      
            case 9:
                printf("<PR, dreal>\n");
            	fprintf(yyout,"<PR, dreal>\n");
                break;
            case 10:
                printf("<PR, car>\n");
            	fprintf(yyout,"<PR, car>\n");
                break;
            case 11:
                printf("<PR, sin>\n");
            	fprintf(yyout,"<PR, sin>\n");
                break;
            case 12:
                printf("<PR, Si>\n");
            	fprintf(yyout,"<PR, Si>\n");
            	break;
            case 13:
                printf("<PR, entonces>\n");
            	fprintf(yyout,"<PR, entonces>\n");
            	break;
            case 14:
                printf("<PR, sino>\n");
            	fprintf(yyout,"<PR, sino>\n");
            	break;
            case 15:
                printf("<PR, mientras>\n");
            	fprintf(yyout,"<PR, mientras>\n");
            	break;
            case 16:
                printf("<PR, hacer>\n");
            	fprintf(yyout,"<PR, hacer>\n");
            	break;
            case 17:
                printf("<PR, mientras que>\n");
            	fprintf(yyout,"<PR, mientras que>\n");
            	break;
            case 18:
                printf("<PR, escribir>\n");
            	fprintf(yyout,"<PR, escribir>\n");
            	break;
            case 19:
                printf("<PR, leer>\n");
        		fprintf(yyout,"<leer>\n");
            	break;
            case 20:
                printf("<PR, devolver>\n");
            	fprintf(yyout,"<PR, devolver>\n");
            	break;
            case 21:
                printf("<OPL, oo>\n");
            	fprintf(yyout,"<OPL, oo>\n");
            	break;
            case 22:
                printf("<OPL, yy>\n");
            	fprintf(yyout,"<OPL, yy>\n");
            	break;
            case 23:
                printf("<OPL, no>\n");
            	fprintf(yyout,"<OPL, no>\n");
            	break;
            case 24:
                printf("<PR, verdadero>\n");
            	fprintf(yyout,"<PR, verdadero>\n");
            	break;
            case 25:
                printf("<PR, falso>\n");
            	fprintf(yyout,"<PR, falso>\n");
            	break;
            case 26:
                printf("<OPA, := >\n");
            	fprintf(yyout,"<asignacion, := >\n");
            	break;
            case 27:
                printf("<OPR, < >\n");
            	fprintf(yyout,"<OPR, < >\n");
            	break;
            case 28:
                printf("<OPR, > >\n");
            	fprintf(yyout,"<OPR, > >\n");
            	break;
            case 29:
                printf("<OPR, <= >\n");
            	fprintf(yyout,"<OPR, <= >\n");
            	break;
            case 30:
                printf("<OPR, >= >\n");
            	fprintf(yyout,"<OPR, >= >\n");
            	break;
            case 31:
                printf("<OPR, == >\n");
            	fprintf(yyout,"<OPR, == >\n");
            	break;
            case 32:
                printf("<OPR, <> >\n");
            	fprintf(yyout,"<OPR, <> >\n");
            	break;
            case 33:
                printf("<OP, + >\n");
            	fprintf(yyout,"<OP, + >\n");
            	break;
            case 34:
                printf("<OP, - >\n");
            	fprintf(yyout,"<OP, - >\n");
            	break;
            case 35:
                printf("<OP, * >\n");
            	fprintf(yyout,"<OP, * >\n");
            	break;
            case 36:
                printf("<OP, / >\n");
            	fprintf(yyout,"<OP, / >\n");
            	break;
            case 37:
                printf("<OP, %s >\n",yytext);
            	fprintf(yyout,"<OP, %s >\n",yytext);
            	break;
            case 38:
                printf("< ( >\n");
            	fprintf(yyout,"< ( >\n");
            	break;
            case 39:
                printf("< ) >\n");
            	fprintf(yyout,"< ) >\n");
            	break;
            case 40:
                printf("< [ >\n");
            	fprintf(yyout,"< [ >\n");
            	break;
            case 41:
                printf("< ] >\n");
            	fprintf(yyout,"< ] >\n");
            	break;
            case 42:
                printf("< . >\n");
            	fprintf(yyout,"< . >\n");
            	break;
            case 43:
                printf("< , >\n");
            	fprintf(yyout,"< , >\n");
            	break;
            case 44:
                printf("< car, %s > \n",yytext);
            	fprintf(yyout, "< car, %s > \n",yytext);
            	break;
            case 45:
                printf("< cad, %s > \n",yytext);
            	fprintf(yyout, "< cad, %s > \n",yytext);
            	break;
            case 46:
                printf("< id, %s > \n",yytext);
            	fprintf(yyout, "< id, %s > \n",yytext);
            	break;
            
            	
        }



    }

}
