#include <stdio.h>
extern FILE *yyin;
extern int yylex();
extern void yyerror(char *msg);
extern int yyparse();

int main(int argc, char **argv){
	FILE *f=fopen(argv[1],"r");
	if(argc<2) return -1;
	if(!f) return -1;
	yyin=f;
	yyparse();
	fclose(f);
	return 0;
}