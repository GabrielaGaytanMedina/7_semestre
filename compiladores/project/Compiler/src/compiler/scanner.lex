package compiler;

%%

%byaccj
%line
%column

%{
    private Parser parser;

    public Yylex(java.io.Reader r, Parser yyparser) {
      this(r);
      this.parser = yyparser;
    }

    public int getYycolumn(){
        return yycolumn + 1;
    }

    public int getYyline(){
            return yyline + 1;
    }

%}

entero = [0-9]+
real = {entero}.{entero}
id = [a-zA-Z_][a-zA-Z0-9_]*
cad = \"[^\"]*\"
car = '[^']'

%%
"--"[^\r\n]* { }
"<*"[^"*>"]*"*>"(\r\n)? { }
"registro" { return Parser.REGISTRO; }
"inicio" { return Parser.INICIO; }
"fin" { return Parser.FIN; }
"func" { return Parser.FUNC; }
"ent" { parser.yylval = new ParserVal(yytext()); return Parser.ENT; }
"real" { return Parser.REAL; }
"dreal" { return Parser.DREAL; }
"car" { return Parser.CAR; }
"sin" { return Parser.SIN; }
"si" { return Parser.SI; }
"entonces" { return Parser.ENTONCES; }
"sino" { return Parser.SINO; }
"mientras" { return Parser.MIENTRAS; }
"hacer" { return Parser.HACER; }
"mientras que" { return Parser.MIENTRASQUE; }
"escribir" { return Parser.ESCRIBIR; }
"leer" { return Parser.LEER; }
"devolver" {return Parser.DEVOLVER; }
"segun" {return Parser.SEGUN; }
"terminar" {return Parser.TERMINAR; }
"caso" {return Parser.CASO; }
"predet" {return Parser.PREDET; }
"segun" {return Parser.SEGUN; }
"oo" { return Parser.O; }
"yy" { return Parser.Y; }
"no" { return Parser.NO; }
"verdadero" { return Parser.VERDADERO; }
"falso" { return Parser.FALSO; }
"+" { return Parser.MAS; }
"-" { return Parser.MENOS; }
"*" { return Parser.POR; }
"/" {return Parser.DIV; }
"%" { return Parser.PRC; }
":=" { return Parser.ASIG; }
"<" { return Parser.MEN; }
">" { return Parser.MAY; }
"<=" { return Parser.MENI; }
">=" { return Parser.MAYI; }
"==" { return Parser.II; }
"<>" { return Parser.MM; }
"%" { return Parser.PRC; }
"(" { return Parser.LPAR; }
")" { return Parser.RPAR; }
"[" { return Parser.LCOR; }
"]" { return Parser.RCOR; }
"." { return Parser.PNT; }
"," { return Parser.COMA; }
":" { return Parser.COMA; }
{entero} { parser.yylval = new ParserVal((Object) Integer.parseInt(yytext())); return Parser.NUM; }
{real} { parser.yylval = new ParserVal((Object) Double.parseDouble(yytext())); return Parser.NUM; }
{id} { parser.yylval = new ParserVal((Object) String.valueOf(yytext())); return Parser.ID; }
{car} { parser.yylval = new ParserVal((Object) String.valueOf(yytext())); return Parser.CARACTER; }
{cad} { parser.yylval = new ParserVal((Object) String.valueOf(yytext())); return Parser.CADENA; }
\r\n { return Parser.SL; }
[\t ]+ {}
. {System.out.println("Lexical error " + yytext());}