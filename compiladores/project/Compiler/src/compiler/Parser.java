//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";



package compiler;



//#line 2 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
    import java.io.*;
    import java.util.LinkedList;
    import java.util.Stack;
    

//#line 23 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short NUM=257;
public final static short ID=258;
public final static short ASIG=259;
public final static short MEN=260;
public final static short MAY=261;
public final static short MENI=262;
public final static short MAYI=263;
public final static short II=264;
public final static short MM=265;
public final static short MAS=266;
public final static short MENOS=267;
public final static short POR=268;
public final static short DIV=269;
public final static short PRC=270;
public final static short LPAR=271;
public final static short RPAR=272;
public final static short LCOR=273;
public final static short RCOR=274;
public final static short PNT=275;
public final static short COMA=276;
public final static short nl=277;
public final static short SEGUN=278;
public final static short TERMINAR=279;
public final static short CASO=280;
public final static short PREDET=281;
public final static short DOS=282;
public final static short SL=283;
public final static short REGISTRO=284;
public final static short INICIO=285;
public final static short MIENTRAS=286;
public final static short FIN=287;
public final static short ENT=288;
public final static short REAL=289;
public final static short DREAL=290;
public final static short CAR=291;
public final static short SIN=292;
public final static short FUNC=293;
public final static short SI=294;
public final static short ENTONCES=295;
public final static short SINO=296;
public final static short MIENTRASQUE=297;
public final static short ESCRIBIR=298;
public final static short LEER=299;
public final static short DEVOLVER=300;
public final static short SOL=301;
public final static short VERDADERO=302;
public final static short FALSO=303;
public final static short CADENA=304;
public final static short CARACTER=305;
public final static short HACER=306;
public final static short O=307;
public final static short Y=308;
public final static short NO=309;
public final static short RCOR5=310;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
   20,    0,    3,    3,    3,    3,   23,    4,   24,    2,
    1,    1,    1,    1,    1,    5,    5,   22,   22,   25,
   26,   21,   21,   21,    6,    6,    7,    7,    8,   27,
    9,   10,   10,   28,   11,   11,   29,   16,   30,   16,
   31,   16,   32,   16,   16,   16,   16,   16,   16,   16,
   12,   12,   12,   12,   12,   12,   17,   17,   17,   17,
   17,   17,   17,   13,   13,   13,   13,   13,   13,   13,
   13,   13,   13,   13,   14,   14,   15,   15,   15,   18,
   18,   19,   19,
};
final static short yylen[] = {                            2,
    0,    4,    4,    4,    1,    0,    0,    8,    0,    3,
    1,    1,    1,    1,    1,    4,    0,    3,    1,    0,
    0,   16,    1,    0,    1,    1,    2,    1,    2,    0,
    3,    3,    0,    0,    4,    1,    0,    8,    0,   11,
    0,    8,    0,    7,    3,    2,    2,    1,    2,    1,
    3,    3,    2,    1,    1,    1,    3,    3,    3,    3,
    3,    3,    1,    3,    3,    3,    3,    3,    3,    1,
    1,    1,    1,    4,    2,    3,    4,    4,    0,    1,
    0,    3,    1,
};
final static short yydefred[] = {                         1,
    0,    0,    5,    0,   11,   12,   13,   14,   15,    9,
    0,    0,    0,    0,    0,   19,    0,    0,    0,    0,
    0,   10,    0,    0,   23,    0,    2,    0,    7,    0,
   18,    3,    0,    4,    0,    0,    0,    0,   16,    0,
    0,    0,   30,    0,    0,   28,    0,    8,    0,    0,
   27,   29,    0,   31,   20,    0,    0,   32,    0,    0,
    0,   50,    0,    0,    0,    0,    0,    0,    0,   36,
    0,   71,    0,    0,   55,   56,   72,   73,    0,    0,
    0,   70,    0,    0,    0,    0,   47,    0,   43,    0,
    0,    0,    0,    0,    0,    0,   53,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   39,    0,    0,    0,    0,    0,    0,    0,    0,
   76,    0,   69,    0,   52,    0,    0,    0,   66,   67,
   68,    0,    0,    0,    0,   61,   62,    0,   37,    0,
   21,   35,    0,   74,    0,    0,    0,    0,    0,    0,
    0,   77,    0,   78,    0,    0,    0,    0,   22,    0,
    0,    0,    0,   42,    0,   38,    0,    0,   40,
};
final static short yydgoto[] = {                          1,
   10,   11,   12,   13,   22,   44,   45,   46,   47,   54,
   69,   80,   81,   82,   95,   70,   83,  119,  120,    2,
   27,   17,   35,   15,   57,  151,   49,  116,  149,  138,
  100,  114,
};
final static short yysindex[] = {                         0,
    0,   20,    0, -273,    0,    0,    0,    0,    0,    0,
 -230, -240, -230, -206, -218,    0, -260, -249, -254, -225,
 -162,    0, -160,   20,    0,   51,    0,   20,    0, -137,
    0,    0, -132,    0,   20, -218, -133, -136,    0,   56,
 -138,    0,    0, -122,   51,    0, -106,    0, -119, -110,
    0,    0, -111,    0,    0, -119, -107,    0,   20,  -36,
  -86,    0, -252, -252, -174,  -94, -174, -105, -103,    0,
 -174,    0, -257, -174,    0,    0,    0,    0, -252, -282,
   83,    0,   35, -246,   83, -255,    0,   83,    0, -104,
   83,  -91, -174,  -74,  -84, -163,    0, -252, -252, -116,
 -174, -174, -174, -174, -174, -174, -174, -174, -174, -174,
 -174,    0,  -85,  -36,  -73,  -36, -174,   83,  -75,  -77,
    0, -174,    0,  -99,    0,  -62, -112, -112,    0,    0,
    0, -130, -130, -172, -172,    0,    0,  -36,    0,  -58,
    0,    0,  -51,    0, -174,   48,  -36,  -56,  -36,  -68,
 -249,    0,   83,    0,  -53,  -76,  -52, -252,    0,  -46,
  -49,  -45, -282,    0,  -36,    0,  -48,  -42,    0,
};
final static short yyrindex[] = {                         0,
    0,  -37,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -26,    0,    0,  257,    0,    0,
    0,    0,    0, -198,    0,    0,    0, -198,    0,    0,
    0,    0,    0,    0,  -37,  -26,    0,    0,    0,    0,
    0, -183,    0,    0,   -6,    0,    0,    0,    9,    0,
    0,    0,    0,    0,    0,    9,    0,    0,  -25,    0,
    0,    0,    0,    0,    0,    0,  -15,    0,    0,    0,
    0,    0, -196,    0,    0,    0,    0,    0,    0,  -35,
  -69,    0,   30,    0,   -4, -227,    0,    4,    0,  -14,
    5,    0,   11,    0, -147,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -245,    0,   17,
    0,    0,    0, -274,    0,    0, -121,  -95,    0,    0,
    0,   -5,   -1,  -59,  -55,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -14,
  257,    0, -148,    0,    0,  -14,    0,    0,    0,  -14,
    0,  -14,    8,    0,    0,    0,    0,  -14,    0,
};
final static short yygindex[] = {                         0,
   46,  251,  -11,    0,  283,    0,    0,  248,    0,  264,
 -102,  -64,  -63,  255,    0,  207,  220,    0,    0,    0,
  173,  319,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static int YYTABLESIZE=353;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         84,
   92,   85,   92,   88,   72,   73,   25,   91,   51,   14,
   96,  140,   32,   93,   97,   23,   34,   94,   74,   94,
   51,   23,   24,   38,   98,   99,   83,   16,   28,  118,
   83,   51,   51,  124,  125,  148,  112,  127,  128,  129,
  130,  131,   18,   26,  155,   79,  157,   60,  113,   75,
   76,   77,   78,  143,   21,   79,   79,   29,  146,    6,
   98,   99,  167,   79,   79,   79,   79,   79,   79,   79,
   79,   79,   79,   79,   15,   79,   79,   79,   20,   79,
    6,  153,   72,   73,    6,   43,   79,    6,   26,   15,
   43,  110,  111,  163,   30,    6,   74,   31,   79,    6,
    6,    6,  101,  102,  103,  104,  105,    6,  123,   79,
   79,   79,   75,   75,   75,   75,   75,   75,   75,   75,
   75,   75,   75,   82,   75,   37,   75,   82,   75,   77,
   78,  108,  109,  110,  111,   75,   36,   40,   64,   64,
   64,   64,   64,   64,   64,   64,   41,   75,   48,   50,
   64,   52,   64,   53,   64,  103,  104,  105,   75,   75,
   75,   64,   56,   86,   65,   65,   65,   65,   65,   65,
   65,   65,   71,   64,   55,   59,   65,   89,   65,   90,
   65,  117,  115,  121,   64,   64,   64,   65,  122,  126,
   63,   63,   63,   63,   63,   63,  144,  139,  145,   65,
   59,   59,   59,   59,   60,   60,   60,   60,   99,  141,
   65,   65,   65,   63,  101,  102,  103,  104,  105,  161,
  147,   61,  152,   59,  150,   63,  156,   60,  158,  160,
  162,   17,    6,  165,  168,   59,   63,   63,   63,   60,
  164,  166,   62,   34,  169,    6,   59,   59,   59,   63,
   60,   60,   60,    6,   57,   57,   24,   64,   58,   58,
    6,   65,   66,   67,   34,   25,   33,   48,    6,   68,
   41,   34,    6,    6,    6,    3,   33,   57,   46,   34,
    6,   58,   81,   34,   34,   34,   49,   45,   80,   57,
   44,   34,   51,   58,  106,  107,  108,  109,  110,  111,
   57,   57,   57,    4,   58,   58,   58,    5,    6,    7,
    8,    9,   54,  101,  102,  103,  104,  105,   39,   58,
   87,  154,  142,  159,   54,  132,  133,  134,  135,  136,
  137,   19,    0,    0,    0,   54,   54,   54,    5,    6,
    7,    8,    9,    5,    6,    7,    8,   42,  101,  102,
  103,  104,  105,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         64,
  258,   65,  258,   67,  257,  258,  256,   71,  283,  283,
   74,  114,   24,  271,   79,  276,   28,  275,  271,  275,
  295,  276,  283,   35,  307,  308,  272,  258,  283,   93,
  276,  306,  307,   98,   99,  138,  283,  101,  102,  103,
  104,  105,  283,  293,  147,  273,  149,   59,  295,  302,
  303,  304,  305,  117,  273,  283,  309,  283,  122,  258,
  307,  308,  165,  260,  261,  262,  263,  264,  265,  266,
  267,  268,  269,  270,  258,  272,  273,  274,  285,  276,
  279,  145,  257,  258,  283,   40,  283,  286,  272,  273,
   45,  264,  265,  158,  257,  294,  271,  258,  295,  298,
  299,  300,  266,  267,  268,  269,  270,  306,  272,  306,
  307,  308,  260,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  270,  272,  272,  258,  274,  276,  276,  304,
  305,  262,  263,  264,  265,  283,  274,  271,  260,  261,
  262,  263,  264,  265,  266,  267,  283,  295,  287,  272,
  272,  258,  274,  273,  276,  268,  269,  270,  306,  307,
  308,  283,  274,  258,  260,  261,  262,  263,  264,  265,
  266,  267,  259,  295,  285,  283,  272,  283,  274,  283,
  276,  273,  287,  258,  306,  307,  308,  283,  273,  306,
  260,  261,  262,  263,  264,  265,  272,  283,  276,  295,
  260,  261,  262,  263,  260,  261,  262,  263,  308,  283,
  306,  307,  308,  283,  266,  267,  268,  269,  270,  296,
  283,  258,  274,  283,  283,  295,  283,  283,  297,  283,
  283,  258,  258,  283,  283,  295,  306,  307,  308,  295,
  287,  287,  279,  258,  287,  283,  306,  307,  308,  286,
  306,  307,  308,  279,  260,  261,    0,  294,  260,  261,
  286,  298,  299,  300,  279,  272,  258,  283,  294,  306,
  306,  286,  298,  299,  300,  256,   26,  283,  283,  294,
  306,  283,  272,  298,  299,  300,  283,  283,  272,  295,
  283,  306,   45,  295,  260,  261,  262,  263,  264,  265,
  306,  307,  308,  284,  306,  307,  308,  288,  289,  290,
  291,  292,  283,  266,  267,  268,  269,  270,   36,   56,
   66,  274,  116,  151,  295,  106,  107,  108,  109,  110,
  111,   13,   -1,   -1,   -1,  306,  307,  308,  288,  289,
  290,  291,  292,  288,  289,  290,  291,  292,  266,  267,
  268,  269,  270,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=310;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"NUM","ID","ASIG","MEN","MAY","MENI","MAYI","II","MM","MAS",
"MENOS","POR","DIV","PRC","LPAR","RPAR","LCOR","RCOR","PNT","COMA","nl","SEGUN",
"TERMINAR","CASO","PREDET","DOS","SL","REGISTRO","INICIO","MIENTRAS","FIN",
"ENT","REAL","DREAL","CAR","SIN","FUNC","SI","ENTONCES","SINO","MIENTRASQUE",
"ESCRIBIR","LEER","DEVOLVER","SOL","VERDADERO","FALSO","CADENA","CARACTER",
"HACER","O","Y","NO","RCOR5",
};
final static String yyrule[] = {
"$accept : program",
"$$1 :",
"program : $$1 declaraciones SL funciones",
"declaraciones : tipo lista_var SL declaraciones",
"declaraciones : tipo_registro lista_var SL declaraciones",
"declaraciones : error",
"declaraciones :",
"$$2 :",
"tipo_registro : REGISTRO SL INICIO SL $$2 declaraciones SL FIN",
"$$3 :",
"tipo : base $$3 tipo_arreglo",
"base : ENT",
"base : REAL",
"base : DREAL",
"base : CAR",
"base : SIN",
"tipo_arreglo : LCOR NUM RCOR tipo_arreglo",
"tipo_arreglo :",
"lista_var : lista_var COMA ID",
"lista_var : ID",
"$$4 :",
"$$5 :",
"funciones : FUNC tipo ID LPAR argumentos RPAR INICIO $$4 SL declaraciones sentencias SL FIN SL $$5 funciones",
"funciones : error",
"funciones :",
"argumentos : lista_arg",
"argumentos : SIN",
"lista_arg : lista_arg arg",
"lista_arg : arg",
"arg : tipo_arg ID",
"$$6 :",
"tipo_arg : base $$6 param_arr",
"param_arr : LCOR RCOR param_arr",
"param_arr :",
"$$7 :",
"sentencias : sentencias SL $$7 sentencia",
"sentencias : sentencia",
"$$8 :",
"sentencia : SI expresion_booleana ENTONCES SL $$8 sentencias SL FIN",
"$$9 :",
"sentencia : SI expresion_booleana SL $$9 sentencias SL SINO SL sentencias SL FIN",
"$$10 :",
"sentencia : MIENTRAS expresion_booleana $$10 HACER SL sentencias SL FIN",
"$$11 :",
"sentencia : HACER SL $$11 sentencias SL MIENTRASQUE expresion_booleana",
"sentencia : ID ASIG expresion",
"sentencia : ESCRIBIR expresion",
"sentencia : LEER variable",
"sentencia : DEVOLVER",
"sentencia : DEVOLVER expresion",
"sentencia : TERMINAR",
"expresion_booleana : expresion_booleana O expresion_booleana",
"expresion_booleana : expresion_booleana Y expresion_booleana",
"expresion_booleana : NO expresion_booleana",
"expresion_booleana : relacional",
"expresion_booleana : VERDADERO",
"expresion_booleana : FALSO",
"relacional : relacional MEN relacional",
"relacional : relacional MAY relacional",
"relacional : relacional MENI relacional",
"relacional : relacional MAYI relacional",
"relacional : relacional II relacional",
"relacional : relacional MM relacional",
"relacional : expresion",
"expresion : expresion MAS expresion",
"expresion : expresion MENOS expresion",
"expresion : expresion POR expresion",
"expresion : expresion DIV expresion",
"expresion : expresion PRC expresion",
"expresion : LPAR expresion RPAR",
"expresion : variable",
"expresion : NUM",
"expresion : CADENA",
"expresion : CARACTER",
"expresion : ID LPAR parametros RPAR",
"variable : ID arreglo",
"variable : ID PNT ID",
"arreglo : ID LCOR expresion RCOR",
"arreglo : arreglo LCOR expresion RCOR",
"arreglo :",
"parametros : lista_param",
"parametros :",
"lista_param : lista_param COMA expresion",
"lista_param : expresion",
};

//#line 703 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"

    private Yylex lexer;
    private int dir;
    private int indexEti;
    private int numEti = 0;
    private int a1, a2;
    private Index i1, i2;
    private boolean FuncReturn;
    private Tipo base;
    private Tipo type;
    private Tipo funcType;
    ListaCuadrupla lc= new ListaCuadrupla();
    Etiqueta L, L1;
    private static Stack<Integer> stackDir = new Stack();


    private int yylex () {
      int yyl_return = -1;
      try {
        //yylval = new ParserVal(new Object());
        yyl_return = lexer.yylex();
      }
      catch (IOException e) {
        System.err.println("IO error :" + e);
      }
      return yyl_return;
    }


    public void yyerror (String error) {
      System.err.println ("Sintax Error: '" + lexer.yytext() + "' in line: " + lexer.getYyline() + " column: " + lexer.getYycolumn());
    }


    public Parser(Reader r) {
      lexer = new Yylex(r, this);
    }
//#line 474 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
throws CompilerException
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 28 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
            dir = 0;
            indexEti = 0;
            TablaSimbolos ts = new TablaSimbolos();
            TablaTipos tt = new TablaTipos();
            Pilas.stackSimbolos.push(ts);
            Pilas.stackTipos.push(tt);
        }
break;
case 2:
//#line 37 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
     System.out.println("COMPILED SUCCESSFUL"); 
     try{
        Operaciones.GenerarArchivoCode(lc);
        }catch(IOException e){
            System.out.println("File not Generated");
        }
      }
break;
case 3:
//#line 46 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{ yyval.obj = val_peek(3).obj; }
break;
case 4:
//#line 47 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{ yyval.obj = val_peek(3).obj; }
break;
case 5:
//#line 48 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{ yyval.obj = null; }
break;
case 6:
//#line 49 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{ yyval.obj = null; }
break;
case 7:
//#line 52 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
            TablaSimbolos ts = new TablaSimbolos();
            TablaTipos tt = new TablaTipos();
            stackDir.push(dir);
            dir = 0;
            Pilas.stackSimbolos.push(ts);
            Pilas.stackTipos.push(tt);
        }
break;
case 9:
//#line 61 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{ base = (Tipo) val_peek(0).obj; }
break;
case 10:
//#line 61 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{ yyval.obj = val_peek(1).obj; }
break;
case 11:
//#line 63 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{ yyval.obj = Tipo.ent; type = Tipo.ent; }
break;
case 12:
//#line 64 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{ yyval.obj = Tipo.real; type = Tipo.real; }
break;
case 13:
//#line 65 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{ yyval.obj = Tipo.dreal; type = Tipo.dreal; }
break;
case 14:
//#line 66 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{ yyval.obj = Tipo.car; type = Tipo.car; }
break;
case 15:
//#line 67 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{yyval.obj = Tipo.sin; type = Tipo.sin; }
break;
case 16:
//#line 70 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
                if (val_peek(2).obj instanceof Integer && (Integer) val_peek(2).obj > 0){
                    TablaTipos tmp = Pilas.stackTipos.peek();
                    Atributos aux = new Atributos();
                    aux.tipo = new Tipo("array", (Integer) val_peek(2).obj, ((Atributos)val_peek(0).obj).tipo);
                    tmp.addTipo(aux.tipo);
                    yyval.obj = aux;
                }
            }
break;
case 17:
//#line 80 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{ 
            Atributos aux = new Atributos();
            aux.tipo = base;
            yyval.obj = aux; 
        }
break;
case 18:
//#line 87 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
            if(!Pilas.stackSimbolos.peek().getID((String)val_peek(0).obj)){
                TablaSimbolos aux = Pilas.stackSimbolos.peek();
                aux.addSimbol((String) val_peek(0).obj, new Simbolo(type, dir, "var"));
                dir += Pilas.stackTipos.peek().getTam(type.getId());
            }
            else{
                throw new CompilerException("Semantic Error: '" + lexer.yytext() + "' variable already declared " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
            }
        }
break;
case 19:
//#line 98 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
            if(!Pilas.stackSimbolos.peek().getID((String)val_peek(0).obj)){
               TablaSimbolos aux = Pilas.stackSimbolos.peek();
                aux.addSimbol((String) val_peek(0).obj, new Simbolo(type, dir, "var"));
                dir += Pilas.stackTipos.peek().getTam(type.getId());
            }
            else{
                throw new CompilerException("Semantic Error: '" + lexer.yytext() + "' variable already declared " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
            }
        }
break;
case 20:
//#line 110 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
            if(!Pilas.stackSimbolos.firstElement().getID((String)val_peek(4).obj)){
                TablaSimbolos aux = Pilas.stackSimbolos.firstElement();
                aux.addSimbol((String) val_peek(4).obj, new Simbolo(type, null, "func"));
                Pilas.stackDir.push(dir);
                funcType = type;
                FuncReturn = false;
                dir = 0;
                TablaSimbolos ts2 = new TablaSimbolos();
                TablaTipos tt2 = new TablaTipos();
                Pilas.stackSimbolos.push(ts2);
                Pilas.stackTipos.push(tt2);
                lc.agregaCuadrupla ("Jlabel",null, null,(String) val_peek(4).obj);
            }
        }
break;
case 21:
//#line 126 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
            if(Pilas.stackSimbolos.firstElement().getID((String)val_peek(11).obj)){
                dir = Pilas.stackDir.pop();
                
                Etiqueta L = new Etiqueta(numEti);
                numEti++;
                /*backpach();*/
                lc.agregaCuadrupla("Jlabel", null, null,  L.Etiqueta);
                Pilas.stackTipos.pop();
                Pilas.stackSimbolos.pop();
                Pilas.stackSimbolos.firstElement().tablaSimbolos.get((String) val_peek(11).obj).setParam(((Atributos) val_peek(9).obj).lista);
                if(!((Tipo) val_peek(12).obj).getTipo().equals("sin") && FuncReturn == false){
                    throw new CompilerException("Semantic Error: the function must return 'sin' in line: " + lexer.getYyline() + "\nBUILD FAILED");
                }

                
            }
            else{
                throw new CompilerException("Semantic Error: '" + lexer.yytext() + "' variable already declared " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
            }
        }
break;
case 25:
//#line 151 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{ yyval.obj = val_peek(0).obj; }
break;
case 26:
//#line 153 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{ 
            Atributos aux = new Atributos();
            aux.tipo = Tipo.sin;
            yyval.obj = aux;
        }
break;
case 27:
//#line 160 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
            yyval.obj = (Atributos) val_peek(1).obj;
            ((Atributos) yyval.obj).lista.add(((Atributos) val_peek(0).obj).tipo);
        }
break;
case 28:
//#line 165 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
            Atributos aux = new Atributos();
            aux.lista = new LinkedList();
            aux.lista.add(((Atributos) val_peek(0).obj).tipo);
        }
break;
case 29:
//#line 172 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
            if(!Pilas.stackSimbolos.peek().getID((String) val_peek(0).obj)){
                Pilas.stackSimbolos.peek().addSimbol((String) val_peek(0).obj, new Simbolo(type, dir, "var"));
                dir += Pilas.stackTipos.peek().getTam(type.getId());
            }
            else {
                throw new CompilerException("Semantic Error: '" + lexer.yytext() + "' variable already declared " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
            }
            yyval.obj = (Tipo) val_peek(1).obj;
        }
break;
case 30:
//#line 183 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{ base = (Tipo) val_peek(0).obj; }
break;
case 31:
//#line 183 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{ yyval.obj = (Tipo) val_peek(0).obj; }
break;
case 32:
//#line 187 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
            Tipo tmp = new Tipo("array", 0, (Tipo) val_peek(0).obj);
            Pilas.stackTipos.peek().addTipo(tmp);
            yyval.obj = tmp;
        }
break;
case 33:
//#line 192 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{ yyval.obj = (Tipo) base; }
break;
case 34:
//#line 194 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{ Etiqueta L = new Etiqueta(numEti++); }
break;
case 35:
//#line 195 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
            Atributos aux = new Atributos();
            aux.listNext = ((Atributos) val_peek(0).obj ).listNext;
            yyval.obj = aux;
        }
break;
case 36:
//#line 201 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
            Atributos aux = new Atributos();
            aux.listNext = ((Atributos) val_peek(0).obj).listNext;
            yyval.obj = aux;
        }
break;
case 37:
//#line 209 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
            L = new Etiqueta(numEti++);
            
        }
break;
case 38:
//#line 213 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
            Atributos aux= new Atributos();
            Operaciones.backpatch(lc,((Atributos) val_peek(6).obj).listTrue, L.Etiqueta);
            aux.listNext = Operaciones.combinar(((Atributos) val_peek(6).obj).listFalse, ((Atributos) val_peek(2).obj).listNext);
            yyval.obj=aux;
        }
break;
case 39:
//#line 221 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
            L = new Etiqueta(numEti++);
            L1 = new Etiqueta(numEti++);
        }
break;
case 40:
//#line 226 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
            Atributos aux= new Atributos();
            Operaciones.backpatch(lc,((Atributos) val_peek(9).obj).listTrue, L.Etiqueta);
            Operaciones.backpatch(lc,((Atributos) val_peek(9).obj).listFalse, L1.Etiqueta);
            aux.listNext = Operaciones.combinar(((Atributos) val_peek(6).obj).listNext, ((Atributos) val_peek(2).obj).listNext);
            yyval.obj=aux;
        }
break;
case 41:
//#line 234 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
            L = new Etiqueta(numEti++);
            L1 = new Etiqueta(numEti++);
        }
break;
case 42:
//#line 239 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
            Atributos aux= new Atributos();
            Operaciones.backpatch(lc,((Atributos) val_peek(2).obj).listNext, L.Etiqueta);
            Operaciones.backpatch(lc,((Atributos) val_peek(6).obj).listTrue, L1.Etiqueta);
            aux.listNext = ((Atributos) val_peek(6).obj).listTrue;
            yyval.obj=aux;
            lc.agregaCuadrupla("goto", null, null, L.Etiqueta);
        }
break;
case 43:
//#line 248 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
            L = new Etiqueta(numEti++);
            L1 = new Etiqueta(numEti++);
        }
break;
case 44:
//#line 252 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
        {

            Atributos hacerAux= new Atributos();
            Operaciones.backpatch(lc,((Atributos) val_peek(0).obj).listTrue, L.Etiqueta);
            Operaciones.backpatch(lc,((Atributos) val_peek(3).obj).listNext, L1.Etiqueta);
            hacerAux.listNext = ((Atributos) val_peek(0).obj).listFalse;
            yyval.obj=hacerAux;
            lc.agregaCuadrupla("label", null, null, L.Etiqueta);
        }
    }
break;
case 45:
//#line 264 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
            if(Pilas.stackSimbolos.peek().getID((String) val_peek(2).obj)){
                Tipo t = Pilas.stackSimbolos.peek().getTipo((String) val_peek(2).obj);
                int dirs = Pilas.stackSimbolos.peek().tablaSimbolos.get((String) val_peek(2).obj).getDir();
                a1 = Operaciones.reducir(((Atributos) val_peek(0).obj).dir, ((Atributos) val_peek(0).obj).tipo, t, null);
                lc.agregaCuadrupla("=", "" + a1, null, "Id" + dirs);
            }
            else{
                throw new CompilerException("Semantic Error: '" + ((String) val_peek(2).obj) + "' the identifier does not exist " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
            }
            Atributos aux=new Atributos();
            yyval.obj=aux;
        }
break;
case 46:
//#line 278 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
            lc.agregaCuadrupla("print", "" + ((Atributos) val_peek(0).obj).dir, null, null);
            yyval.obj = new Atributos();
        }
break;
case 47:
//#line 283 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
            lc.agregaCuadrupla("scan", "" + ((Atributos) val_peek(0).obj).dir, null, "" + ((Atributos) val_peek(0).obj).dir);
            yyval.obj = new Atributos();
        }
break;
case 48:
//#line 288 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
            if(funcType.getTipo().equals("sin")){
                lc.agregaCuadrupla("return", null, null, null);
            }
            else{
                throw new CompilerException("Semantic Error: the function must return '" + funcType.getTipo() + "' in line: " + lexer.getYyline() + "\nBUILD FAILED");
            }
            yyval.obj = new Atributos();
        }
break;
case 49:
//#line 298 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
            if(!funcType.getTipo().equals("sin")){
                a1 = Operaciones.reducir(((Atributos) val_peek(0).obj).dir, ((Atributos) val_peek(0).obj).tipo, funcType, null);
                lc.agregaCuadrupla("return", "" + ((Atributos) val_peek(0).obj).dir, null, null);
                FuncReturn = true;
            }
            else{
                throw new CompilerException("Semantic Error: the function must return 'sin' in line: " + lexer.getYyline() + "\nBUILD FAILED");
            }
            yyval.obj = new Atributos();
        }
break;
case 51:
//#line 312 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
            L = new Etiqueta(numEti++);
            Operaciones.backpatch(lc,((Atributos) val_peek(2).obj).listFalse, L.Etiqueta);
            Atributos aux = new Atributos();
            aux.listTrue = Operaciones.combinar(((Atributos) val_peek(2).obj).listTrue, ((Atributos) val_peek(0).obj).listTrue);
            aux.listFalse = ((Atributos) val_peek(0).obj).listFalse;
            yyval.obj = aux;
            lc.agregaCuadrupla("label", null, null, L.Etiqueta);

        }
break;
case 52:
//#line 323 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
            L = new Etiqueta(numEti++);
            Operaciones.backpatch(lc,((Atributos) val_peek(2).obj).listTrue, L.Etiqueta);
            Atributos aux = new Atributos();
            aux.listFalse = Operaciones.combinar(((Atributos) val_peek(2).obj).listFalse, ((Atributos) val_peek(0).obj).listFalse);
            aux.listTrue = ((Atributos) val_peek(0).obj).listTrue;
            yyval.obj = aux;
            lc.agregaCuadrupla("label", null, null, L.Etiqueta);
        }
break;
case 53:
//#line 333 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
        Atributos aux= new Atributos();
        aux.listTrue=((Atributos)val_peek(0).obj).listFalse;
        aux.listFalse=((Atributos)val_peek(0).obj).listTrue;
        yyval.obj = aux;
    }
break;
case 54:
//#line 340 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
        Atributos aux= new Atributos();
        aux.listTrue=((Atributos)val_peek(0).obj).listTrue;
        aux.listFalse=((Atributos)val_peek(0).obj).listTrue;
        yyval.obj = aux;
    }
break;
case 55:
//#line 347 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
        i1= new Index(numEti++);
        Atributos aux= new Atributos();
        aux.listTrue= new LinkedList();
        aux.listFalse= new LinkedList();
        aux.listTrue.add(i1.index);
        yyval.obj = aux;
        lc.agregaCuadrupla("goto",null,null,""+i1.index);
    }
break;
case 56:
//#line 357 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
        i1= new Index(numEti++);
        Atributos aux= new Atributos();
        aux.listTrue=new LinkedList();
        aux.listFalse= new LinkedList();
        aux.listFalse.add(i1.index);
        yyval.obj = aux;
        lc.agregaCuadrupla("goto",null,null,""+i1.index);
    }
break;
case 57:
//#line 368 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
        Atributos aux= new Atributos();
        aux.listTrue=new LinkedList();
        aux.listFalse=new LinkedList();
        i1= new Index(numEti++);
        i2= new Index(numEti++);
        aux.listTrue.add(i1.index);
        aux.listFalse.add(i2.index);
        aux.tipo=Operaciones.max(((Atributos)val_peek(2).obj).tipo,((Atributos)val_peek(0).obj).tipo);
        a1=Operaciones.ampliar(((Atributos)val_peek(2).obj).dir,((Atributos)val_peek(2).obj).tipo,((Atributos)yyval.obj).tipo,lc);
        a2=Operaciones.ampliar(((Atributos)val_peek(0).obj).dir,((Atributos)val_peek(0).obj).tipo,((Atributos)yyval.obj).tipo,lc);
        lc.agregaCuadrupla("<",""+a1,""+a2,""+i1.index);
        lc.agregaCuadrupla("goto",null,null,""+i2.index);
        yyval.obj = aux;
    }
break;
case 58:
//#line 384 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
        Atributos aux= new Atributos();
        aux.listTrue=new LinkedList();
        aux.listFalse=new LinkedList();
        i1= new Index(numEti++);
        i2= new Index(numEti++);
        aux.listTrue.add(i1.index);
        aux.listFalse.add(i2.index);
        aux.tipo=Operaciones.max(((Atributos)val_peek(2).obj).tipo,((Atributos)val_peek(0).obj).tipo);
        a1=Operaciones.ampliar(((Atributos)val_peek(2).obj).dir,((Atributos)val_peek(2).obj).tipo,((Atributos)yyval.obj).tipo,lc);
        a2=Operaciones.ampliar(((Atributos)val_peek(0).obj).dir,((Atributos)val_peek(0).obj).tipo,((Atributos)yyval.obj).tipo,lc);
        lc.agregaCuadrupla(">",""+a1,""+a2,""+i1.index);
        lc.agregaCuadrupla("goto",null,null,""+i2.index);
        yyval.obj = aux;
    }
break;
case 59:
//#line 400 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
        Atributos aux= new Atributos();
        aux.listTrue=new LinkedList();
        aux.listFalse=new LinkedList();
        i1= new Index(numEti++);
        i2= new Index(numEti++);
        aux.listTrue.add(i1.index);
        aux.listFalse.add(i2.index);
        aux.tipo=Operaciones.max(((Atributos)val_peek(2).obj).tipo,((Atributos)val_peek(0).obj).tipo);
        a1=Operaciones.ampliar(((Atributos)val_peek(2).obj).dir,((Atributos)val_peek(2).obj).tipo,((Atributos)yyval.obj).tipo,lc);
        a2=Operaciones.ampliar(((Atributos)val_peek(0).obj).dir,((Atributos)val_peek(0).obj).tipo,((Atributos)yyval.obj).tipo,lc);
        lc.agregaCuadrupla("<=",""+a1,""+a2,""+i1.index);
        lc.agregaCuadrupla("goto",null,null,""+i2.index);
        yyval.obj = aux;
    }
break;
case 60:
//#line 416 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
        Atributos aux= new Atributos();
        aux.listTrue=new LinkedList();
        aux.listFalse=new LinkedList();
        i1= new Index(numEti++);
        i2= new Index(numEti++);
        aux.listTrue.add(i1.index);
        aux.listFalse.add(i2.index);
        aux.tipo=Operaciones.max(((Atributos)val_peek(2).obj).tipo,((Atributos)val_peek(0).obj).tipo);
        a1=Operaciones.ampliar(((Atributos)val_peek(2).obj).dir,((Atributos)val_peek(2).obj).tipo,((Atributos)yyval.obj).tipo,lc);
        a2=Operaciones.ampliar(((Atributos)val_peek(0).obj).dir,((Atributos)val_peek(0).obj).tipo,((Atributos)yyval.obj).tipo,lc);
        lc.agregaCuadrupla(">=",""+a1,""+a2,""+i1.index);
        lc.agregaCuadrupla("goto",null,null,""+i2.index);
        yyval.obj = aux;
    }
break;
case 61:
//#line 432 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
        Atributos aux= new Atributos();
        aux.listTrue=new LinkedList();
        aux.listFalse=new LinkedList();
        i1= new Index(numEti++);
        i2= new Index(numEti++);
        aux.listTrue.add(i1.index);
        aux.listFalse.add(i2.index);
        aux.tipo=Operaciones.max(((Atributos)val_peek(2).obj).tipo,((Atributos)val_peek(0).obj).tipo);
        a1=Operaciones.ampliar(((Atributos)val_peek(2).obj).dir,((Atributos)val_peek(2).obj).tipo,((Atributos)yyval.obj).tipo,lc);
        a2=Operaciones.ampliar(((Atributos)val_peek(0).obj).dir,((Atributos)val_peek(0).obj).tipo,((Atributos)yyval.obj).tipo,lc);
        lc.agregaCuadrupla("==",""+a1,""+a2,""+i1.index);
        lc.agregaCuadrupla("goto",null,null,""+i2.index);
        yyval.obj = aux;
    }
break;
case 62:
//#line 448 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
        Atributos aux= new Atributos();
        aux.listTrue=new LinkedList();
        aux.listFalse=new LinkedList();
        i1= new Index(numEti++);
        i2= new Index(numEti++);
        aux.listTrue.add(i1.index);
        aux.listFalse.add(i2.index);
        aux.tipo=Operaciones.max(((Atributos)val_peek(2).obj).tipo,((Atributos)val_peek(0).obj).tipo);
        a1=Operaciones.ampliar(((Atributos)val_peek(2).obj).dir,((Atributos)val_peek(2).obj).tipo,((Atributos)yyval.obj).tipo,lc);
        a2=Operaciones.ampliar(((Atributos)val_peek(0).obj).dir,((Atributos)val_peek(0).obj).tipo,((Atributos)yyval.obj).tipo,lc);
        lc.agregaCuadrupla("<>",""+a1,""+a2,""+i1.index);
        lc.agregaCuadrupla("goto",null,null,""+i2.index);
        yyval.obj = aux;
    }
break;
case 63:
//#line 464 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
        yyval.obj=val_peek(0).obj;
    }
break;
case 64:
//#line 469 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
        Atributos mas=new Atributos();
        mas.tipo=Operaciones.max(((Atributos)val_peek(2).obj).tipo, ((Atributos)val_peek(0).obj).tipo);
        mas.dir=new Temp(numEti++).dir;
        yyval.obj=mas;

        a1=Operaciones.ampliar(((Atributos)val_peek(2).obj).dir,((Atributos)val_peek(2).obj).tipo,((Atributos)yyval.obj).tipo,lc);
        a2=Operaciones.ampliar(((Atributos)val_peek(0).obj).dir,((Atributos)val_peek(0).obj).tipo,((Atributos)yyval.obj).tipo,lc);
        lc.agregaCuadrupla("+",Integer.toString(a1),Integer.toString(a2),""+((Atributos)yyval.obj).dir);
    }
break;
case 65:
//#line 480 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
        Atributos min=new Atributos();
        min.tipo=Operaciones.max(((Atributos)val_peek(2).obj).tipo, ((Atributos)val_peek(0).obj).tipo);
        min.dir=new Temp(numEti++).dir;
        yyval.obj=min;

        a1=Operaciones.ampliar(((Atributos)val_peek(2).obj).dir,((Atributos)val_peek(2).obj).tipo,((Atributos)yyval.obj).tipo,lc);
        a2=Operaciones.ampliar(((Atributos)val_peek(0).obj).dir,((Atributos)val_peek(0).obj).tipo,((Atributos)yyval.obj).tipo,lc);
        lc.agregaCuadrupla("-",Integer.toString(a1),Integer.toString(a2),""+((Atributos)yyval.obj).dir);
    }
break;
case 66:
//#line 491 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
        Atributos por=new Atributos();
        por.tipo=Operaciones.max(((Atributos)val_peek(2).obj).tipo, ((Atributos)val_peek(0).obj).tipo);
        por.dir=new Temp(numEti++).dir;
        yyval.obj=por;

        a1=Operaciones.ampliar(((Atributos)val_peek(2).obj).dir,((Atributos)val_peek(2).obj).tipo,((Atributos)yyval.obj).tipo,lc);
        a2=Operaciones.ampliar(((Atributos)val_peek(0).obj).dir,((Atributos)val_peek(0).obj).tipo,((Atributos)yyval.obj).tipo,lc);
        lc.agregaCuadrupla("*",Integer.toString(a1),Integer.toString(a2),""+((Atributos)yyval.obj).dir);
    }
break;
case 67:
//#line 502 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
        Atributos div=new Atributos();
        div.tipo=Operaciones.max(((Atributos)val_peek(2).obj).tipo, ((Atributos)val_peek(0).obj).tipo);
        div.dir=new Temp(numEti++).dir;
        yyval.obj=div;

        a1=Operaciones.ampliar(((Atributos)val_peek(2).obj).dir,((Atributos)val_peek(2).obj).tipo,((Atributos)yyval.obj).tipo,lc);
        a2=Operaciones.ampliar(((Atributos)val_peek(0).obj).dir,((Atributos)val_peek(0).obj).tipo,((Atributos)yyval.obj).tipo,lc);
        lc.agregaCuadrupla("/",Integer.toString(a1),Integer.toString(a2),""+((Atributos)yyval.obj).dir);
    }
break;
case 68:
//#line 513 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
        Atributos mod=new Atributos();
        mod.tipo=Operaciones.max(((Atributos)val_peek(2).obj).tipo, ((Atributos)val_peek(0).obj).tipo);
        mod.dir=new Temp(numEti++).dir;
        yyval.obj=mod;

        a1=Operaciones.ampliar(((Atributos)val_peek(2).obj).dir,((Atributos)val_peek(2).obj).tipo,((Atributos)yyval.obj).tipo,lc);
        a2=Operaciones.ampliar(((Atributos)val_peek(0).obj).dir,((Atributos)val_peek(0).obj).tipo,((Atributos)yyval.obj).tipo,lc);
        lc.agregaCuadrupla("%",Integer.toString(a1),Integer.toString(a2),""+((Atributos)yyval.obj).dir);
    }
break;
case 69:
//#line 524 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
        yyval.obj=(Atributos)val_peek(1).obj;
    }
break;
case 70:
//#line 528 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
        Atributos var1= new Atributos();
        var1.dir = new Temp(numEti++).dir;
        var1.tipo = ((Atributos)val_peek(0).obj).tipo;
        lc.agregaCuadrupla("*", "" + ((Atributos)val_peek(0).obj).base.getId(), null, "" +  var1.dir);
        yyval.obj = var1;

    }
break;
case 71:
//#line 537 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
            Atributos aux = new Atributos();
            if (val_peek(0).obj instanceof Integer){
                aux.dir = ((Integer) val_peek(0).obj);
                aux.tipo = Tipo.ent;
            }
            else{
                aux.tipo = Tipo.real;
                aux.dir = ((Integer) val_peek(0).obj);
            }
            yyval.obj = aux;
        }
break;
case 72:
//#line 550 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
        Atributos cad= new Atributos();
        cad.tipo=Tipo.car;
        yyval.obj=cad;
    }
break;
case 73:
//#line 556 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
        Atributos car= new Atributos();
        car.tipo=Tipo.car;
        yyval.obj=car;
    }
break;
case 74:
//#line 562 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
        if(Pilas.stackSimbolos.firstElement().getID((String)val_peek(3).obj)){
            if(Pilas.stackSimbolos.firstElement().getTipoVar((String)val_peek(3).obj).equals("func")){
                LinkedList<Integer> listaAux=Pilas.stackSimbolos.firstElement().getListParam((String)val_peek(3).obj);
                if(listaAux.size()!=((Atributos)val_peek(1).obj).lista.size()){
                    throw new CompilerException("Semantic Error: the number of arguments do not match " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
                }else{
                    for(int i=0;i<((Atributos)val_peek(1).obj).lista.size();i++){
                        if(!listaAux.get(i).equals(((Atributos)val_peek(1).obj).lista.get(i))){
                            System.out.println("El tipo de parametros no coincide");
                            throw new CompilerException("Semantic Error: the arguments do not match " + " in line: " + lexer.getYyline() + "\nBUILD FAILED");
                        }
                    }
                }
                Atributos exprAux= new Atributos();
                exprAux.dir=new Temp(numEti++).dir;
                exprAux.tipo=Pilas.stackSimbolos.firstElement().getTipo((String)val_peek(3).obj);
                yyval.obj=exprAux;
                lc.agregaCuadrupla("=","call",""+(String)val_peek(3).obj,""+exprAux.dir);
            }else{
                 throw new CompilerException("Semantic Error: '" + (String)val_peek(3).obj + "' the function does not exist " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
            }
        }else{
            throw new CompilerException("Semantic Error: '" + (String)val_peek(3).obj + "' the identifier does not exist " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
        }
    }
break;
case 75:
//#line 590 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
            if(Pilas.stackSimbolos.peek().getID((String) val_peek(1).obj) || Pilas.stackSimbolos.firstElement().getID((String) val_peek(1).obj)){
                Atributos aux = new Atributos();
                aux.dir = ((Atributos) val_peek(0).obj).dir;
                aux.base = ((Atributos) val_peek(0).obj).base;
                aux.tipo = ((Atributos) val_peek(0).obj).tipo;
                yyval.obj = aux;
            }
            else{
                throw new CompilerException("Semantic Error: '" + (String)val_peek(1).obj + "' the identifier does not exist " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
            }
        }
break;
case 76:
//#line 603 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
            if(Pilas.stackSimbolos.peek().getID((String) val_peek(2).obj)){
                Tipo tt = Pilas.stackSimbolos.firstElement().getTipo((String) val_peek(2).obj);
                Tipo tt2 = Pilas.stackTipos.firstElement().tablaTipos.get(tt.getId());
                if(tt2.getTipo().equals("registro")){
                    Tipo tipoBase = Pilas.stackTipos.firstElement().getTipoBase(tt2.getId());
                    if(tipoBase.getId() != -1){
                        Atributos var2 = new Atributos();
                        var2.tipo = tipoBase;
                        /*var.dir = ((String) $3);*/
                        /*var.base = ((String) $1);*/
                        yyval.obj = var2;
                    }
                    else{
                        throw new CompilerException("Semantic Error: '" + (String)val_peek(2).obj + "' the structure does not exist " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
                    }
                }
                else{
                    throw new CompilerException("Semantic Error: '" + (String)val_peek(2).obj + "' the structure does not exist " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
                }
            }
            else{
                throw new CompilerException("Semantic Error: '" + (String)val_peek(2).obj + "' the structure does not exist " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
            }
        }
break;
case 77:
//#line 630 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
        if(Pilas.stackSimbolos.peek().getID((String)val_peek(3).obj)){
            Tipo t=Pilas.stackSimbolos.peek().getTipo((String)val_peek(3).obj);
            if(Pilas.stackTipos.peek().getTipo(t.getId()).equals("array")){
                if(((Atributos)val_peek(1).obj).tipo.equals("ent")){
                    Atributos auxarr= new Atributos();
                    auxarr.base=((Atributos)val_peek(3).obj).base;
                    auxarr.tipo=Pilas.stackTipos.peek().getTipoBase(t.getId());
                    auxarr.size=Pilas.stackTipos.peek().getTam(((Atributos)yyval.obj).tipo.getId());
                    auxarr.dir=new Temp(numEti++).dir;
                    Temp temp=new Temp(numEti++);
                    yyval.obj=auxarr;
                    lc.agregaCuadrupla("*", ""+((Atributos)val_peek(1).obj).dir, ""+((Atributos)yyval.obj).size, ""+((Atributos)yyval.obj).dir);
                    
                }else{
                    throw new CompilerException("Semantic Error: the index must be 'ent' " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
                }
            }else{
                throw new CompilerException("Semantic Error: '" + (String)val_peek(3).obj + "' the identifier is not an array " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
            }
        }
    }
break;
case 78:
//#line 653 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
        
        if(Pilas.stackTipos.peek().getTipoBase(((Atributos)val_peek(3).obj).tipo.getId()).equals("array")){
            if((((Atributos)val_peek(1).obj).tipo).equals("ent")){
                Atributos auxarr= new Atributos();
                auxarr.base=((Atributos)val_peek(3).obj).base;
                auxarr.tipo=Pilas.stackTipos.peek().getTipoBase(((Atributos)val_peek(3).obj).tipo.getId());
                auxarr.size=Pilas.stackTipos.peek().getTam(((Atributos)yyval.obj).tipo.getId());
                auxarr.dir=new Temp(numEti++).dir;
                Temp temp=new Temp(numEti);
                yyval.obj=auxarr;
                lc.agregaCuadrupla("*", ""+((Atributos)val_peek(1).obj).dir, ""+((Atributos)val_peek(3).obj).size, ""+temp.temp);
                lc.agregaCuadrupla("+",""+((Atributos)val_peek(3).obj).dir,""+temp.temp,""+((Atributos)val_peek(3).obj).dir);

            }else{
                throw new CompilerException("Semantic Error: the index must be 'ent' " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
            }
        }else{
           throw new CompilerException("Semantic Error: '" + (String)val_peek(3).obj + "' the identifier is not an array " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
        }
    }
break;
case 80:
//#line 677 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
        yyval.obj = val_peek(0).obj;
    }
break;
case 81:
//#line 681 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
        yyval.obj = new Atributos();
    }
break;
case 82:
//#line 686 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
        Atributos auxlist= new Atributos();
        auxlist.lista=new LinkedList<Tipo>();
        auxlist.lista.add(((Atributos)val_peek(0).obj).tipo);
        yyval.obj = auxlist;
        lc.agregaCuadrupla("param",""+((Atributos)val_peek(0).obj).dir,null,null);
    }
break;
case 83:
//#line 694 "C:\Users\Sergio\Desktop\Compiler\src\compiler\parser.y"
{
        Atributos auxlist= new Atributos();
        auxlist.lista=new LinkedList<Tipo>();
        auxlist.lista.add(((Atributos)val_peek(0).obj).tipo);
        yyval.obj = auxlist;
        lc.agregaCuadrupla("param",""+((Atributos)val_peek(0).obj).dir,null,null);
    }
break;
//#line 1443 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
