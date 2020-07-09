%{
    import java.io.*;
    import java.util.LinkedList;
    import java.util.Stack;
    

%}

%token <obj> NUM ID
%token ASIG MEN MAY MENI MAYI II MM MAS MENOS POR DIV PRC LPAR RPAR LCOR RCOR PNT COMA nl
%token SEGUN TERMINAR CASO PREDET DOS SEGUN SL REGISTRO INICIO MIENTRAS FIN ENT REAL DREAL CAR SIN ID FUNC SI ENTONCES SINO MIENTRAS MIENTRASQUE ESCRIBIR LEER DEVOLVER SOL VERDADERO FALSO CADENA CARACTER HACER

%left MEN MAY
%left MENI MAYI
%left II MM
%left MAS MENOS O
%left POR, DIV, PRC, Y
%left NO
%nonassoc LPAR RPAR
%nonassoc LCOR RCOR5

%type<obj> base tipo declaraciones program tipo_registro tipo_arreglo argumentos lista_arg arg tipo_arg param_arr sentencias expresion_booleana expresion variable arreglo sentencia relacional parametros lista_param

%start program
%%

program:
        {
            dir = 0;
            indexEti = 0;
            TablaSimbolos ts = new TablaSimbolos();
            TablaTipos tt = new TablaTipos();
            Pilas.stackSimbolos.push(ts);
            Pilas.stackTipos.push(tt);
        } 
    declaraciones SL funciones 
    {
     System.out.println("COMPILED SUCCESSFUL"); 
     try{
        Operaciones.GenerarArchivoCode(lc);
        }catch(IOException e){
            System.out.println("File not Generated");
        }
      }
;
declaraciones: tipo lista_var SL declaraciones { $$ = $1; }
    | tipo_registro lista_var SL declaraciones { $$ = $1; }   
    | error { $$ = null; }   
    | { $$ = null; }   
;
tipo_registro: REGISTRO SL INICIO SL 
        {
            TablaSimbolos ts = new TablaSimbolos();
            TablaTipos tt = new TablaTipos();
            stackDir.push(dir);
            dir = 0;
            Pilas.stackSimbolos.push(ts);
            Pilas.stackTipos.push(tt);
        }declaraciones SL FIN 
;
tipo: base { base = (Tipo) $1; } tipo_arreglo { $$ = $2; }
;
base: ENT  { $$ = Tipo.ent; type = Tipo.ent; }
    | REAL { $$ = Tipo.real; type = Tipo.real; }
    | DREAL { $$ = Tipo.dreal; type = Tipo.dreal; }
    | CAR { $$ = Tipo.car; type = Tipo.car; }
    | SIN {$$ = Tipo.sin; type = Tipo.sin; }
;
tipo_arreglo: LCOR NUM RCOR tipo_arreglo
            {
                if ($2 instanceof Integer && (Integer) $2 > 0){
                    TablaTipos tmp = Pilas.stackTipos.peek();
                    Atributos aux = new Atributos();
                    aux.tipo = new Tipo("array", (Integer) $2, ((Atributos)$4).tipo);
                    tmp.addTipo(aux.tipo);
                    $$ = aux;
                }
            }
    |   
        { 
            Atributos aux = new Atributos();
            aux.tipo = base;
            $$ = aux; 
        }
;
lista_var: lista_var COMA ID
        {
            if(!Pilas.stackSimbolos.peek().getID((String)$3)){
                TablaSimbolos aux = Pilas.stackSimbolos.peek();
                aux.addSimbol((String) $3, new Simbolo(type, dir, "var"));
                dir += Pilas.stackTipos.peek().getTam(type.getId());
            }
            else{
                throw new CompilerException("Semantic Error: '" + lexer.yytext() + "' variable already declared " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
            }
        }
    | ID
        {
            if(!Pilas.stackSimbolos.peek().getID((String)$1)){
               TablaSimbolos aux = Pilas.stackSimbolos.peek();
                aux.addSimbol((String) $1, new Simbolo(type, dir, "var"));
                dir += Pilas.stackTipos.peek().getTam(type.getId());
            }
            else{
                throw new CompilerException("Semantic Error: '" + lexer.yytext() + "' variable already declared " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
            }
        }
;
funciones: FUNC tipo ID LPAR argumentos RPAR INICIO 
        {
            if(!Pilas.stackSimbolos.firstElement().getID((String)$3)){
                TablaSimbolos aux = Pilas.stackSimbolos.firstElement();
                aux.addSimbol((String) $3, new Simbolo(type, null, "func"));
                Pilas.stackDir.push(dir);
                funcType = type;
                FuncReturn = false;
                dir = 0;
                TablaSimbolos ts2 = new TablaSimbolos();
                TablaTipos tt2 = new TablaTipos();
                Pilas.stackSimbolos.push(ts2);
                Pilas.stackTipos.push(tt2);
                lc.agregaCuadrupla ("Jlabel",null, null,(String) $3);
            }
        }
        SL declaraciones sentencias SL FIN SL 
        {
            if(Pilas.stackSimbolos.firstElement().getID((String)$3)){
                dir = Pilas.stackDir.pop();
                
                Etiqueta L = new Etiqueta(numEti);
                numEti++;
                //backpach();
                lc.agregaCuadrupla("Jlabel", null, null,  L.Etiqueta);
                Pilas.stackTipos.pop();
                Pilas.stackSimbolos.pop();
                Pilas.stackSimbolos.firstElement().tablaSimbolos.get((String) $3).setParam(((Atributos) $5).lista);
                if(!((Tipo) $2).getTipo().equals("sin") && FuncReturn == false){
                    throw new CompilerException("Semantic Error: the function must return 'sin' in line: " + lexer.getYyline() + "\nBUILD FAILED");
                }

                
            }
            else{
                throw new CompilerException("Semantic Error: '" + lexer.yytext() + "' variable already declared " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
            }
        }
        funciones
    | error
    |
;
argumentos: lista_arg { $$ = $1; }
    | SIN 
        { 
            Atributos aux = new Atributos();
            aux.tipo = Tipo.sin;
            $$ = aux;
        }
;
lista_arg: lista_arg arg
        {
            $$ = (Atributos) $1;
            ((Atributos) $$).lista.add(((Atributos) $2).tipo);
        }
    | arg
        {
            Atributos aux = new Atributos();
            aux.lista = new LinkedList();
            aux.lista.add(((Atributos) $1).tipo);
        }
;
arg: tipo_arg ID
        {
            if(!Pilas.stackSimbolos.peek().getID((String) $2)){
                Pilas.stackSimbolos.peek().addSimbol((String) $2, new Simbolo(type, dir, "var"));
                dir += Pilas.stackTipos.peek().getTam(type.getId());
            }
            else {
                throw new CompilerException("Semantic Error: '" + lexer.yytext() + "' variable already declared " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
            }
            $$ = (Tipo) $1;
        }
;
tipo_arg: base { base = (Tipo) $1; } param_arr { $$ = (Tipo) $3; }
        
;
param_arr: LCOR RCOR param_arr
        {
            Tipo tmp = new Tipo("array", 0, (Tipo) $3);
            Pilas.stackTipos.peek().addTipo(tmp);
            $$ = tmp;
        }
    | { $$ = (Tipo) base; }
;
sentencias: sentencias SL { Etiqueta L = new Etiqueta(numEti++); } sentencia
        {
            Atributos aux = new Atributos();
            aux.listNext = ((Atributos) $4 ).listNext;
            $$ = aux;
        }
    | sentencia
        {
            Atributos aux = new Atributos();
            aux.listNext = ((Atributos) $1).listNext;
            $$ = aux;
        }
;
sentencia: SI 
     expresion_booleana ENTONCES SL 
        {
            L = new Etiqueta(numEti++);
            
        } sentencias SL FIN 
        {
            Atributos aux= new Atributos();
            Operaciones.backpatch(lc,((Atributos) $2).listTrue, L.Etiqueta);
            aux.listNext = Operaciones.combinar(((Atributos) $2).listFalse, ((Atributos) $6).listNext);
            $$=aux;
        }
    | SI 
    expresion_booleana SL 
        {
            L = new Etiqueta(numEti++);
            L1 = new Etiqueta(numEti++);
        }
    sentencias SL SINO SL sentencias SL FIN
        {
            Atributos aux= new Atributos();
            Operaciones.backpatch(lc,((Atributos) $2).listTrue, L.Etiqueta);
            Operaciones.backpatch(lc,((Atributos) $2).listFalse, L1.Etiqueta);
            aux.listNext = Operaciones.combinar(((Atributos) $5).listNext, ((Atributos) $9).listNext);
            $$=aux;
        }
    | MIENTRAS  expresion_booleana 
        {
            L = new Etiqueta(numEti++);
            L1 = new Etiqueta(numEti++);
        }
    HACER SL sentencias SL FIN
        {
            Atributos aux= new Atributos();
            Operaciones.backpatch(lc,((Atributos) $6).listNext, L.Etiqueta);
            Operaciones.backpatch(lc,((Atributos) $2).listTrue, L1.Etiqueta);
            aux.listNext = ((Atributos) $2).listTrue;
            $$=aux;
            lc.agregaCuadrupla("goto", null, null, L.Etiqueta);
        }
    | HACER SL
        {
            L = new Etiqueta(numEti++);
            L1 = new Etiqueta(numEti++);
        }
    sentencias SL MIENTRASQUE expresion_booleana {
        {

            Atributos hacerAux= new Atributos();
            Operaciones.backpatch(lc,((Atributos) $7).listTrue, L.Etiqueta);
            Operaciones.backpatch(lc,((Atributos) $4).listNext, L1.Etiqueta);
            hacerAux.listNext = ((Atributos) $7).listFalse;
            $$=hacerAux;
            lc.agregaCuadrupla("label", null, null, L.Etiqueta);
        }
    }
    | ID ASIG expresion 
        {
            if(Pilas.stackSimbolos.peek().getID((String) $1)){
                Tipo t = Pilas.stackSimbolos.peek().getTipo((String) $1);
                int dirs = Pilas.stackSimbolos.peek().tablaSimbolos.get((String) $1).getDir();
                a1 = Operaciones.reducir(((Atributos) $3).dir, ((Atributos) $3).tipo, t, null);
                lc.agregaCuadrupla("=", "" + a1, null, "Id" + dirs);
            }
            else{
                throw new CompilerException("Semantic Error: '" + ((String) $1) + "' the identifier does not exist " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
            }
            Atributos aux=new Atributos();
            $$=aux;
        }
    | ESCRIBIR expresion
        {
            lc.agregaCuadrupla("print", "" + ((Atributos) $2).dir, null, null);
            $$ = new Atributos();
        }
    | LEER variable 
        {
            lc.agregaCuadrupla("scan", "" + ((Atributos) $2).dir, null, "" + ((Atributos) $2).dir);
            $$ = new Atributos();
        }
    | DEVOLVER 
        {
            if(funcType.getTipo().equals("sin")){
                lc.agregaCuadrupla("return", null, null, null);
            }
            else{
                throw new CompilerException("Semantic Error: the function must return '" + funcType.getTipo() + "' in line: " + lexer.getYyline() + "\nBUILD FAILED");
            }
            $$ = new Atributos();
        }
    | DEVOLVER expresion
        {
            if(!funcType.getTipo().equals("sin")){
                a1 = Operaciones.reducir(((Atributos) $2).dir, ((Atributos) $2).tipo, funcType, null);
                lc.agregaCuadrupla("return", "" + ((Atributos) $2).dir, null, null);
                FuncReturn = true;
            }
            else{
                throw new CompilerException("Semantic Error: the function must return 'sin' in line: " + lexer.getYyline() + "\nBUILD FAILED");
            }
            $$ = new Atributos();
        }
    | TERMINAR
;
expresion_booleana: expresion_booleana O expresion_booleana
        {
            L = new Etiqueta(numEti++);
            Operaciones.backpatch(lc,((Atributos) $1).listFalse, L.Etiqueta);
            Atributos aux = new Atributos();
            aux.listTrue = Operaciones.combinar(((Atributos) $1).listTrue, ((Atributos) $3).listTrue);
            aux.listFalse = ((Atributos) $3).listFalse;
            $$ = aux;
            lc.agregaCuadrupla("label", null, null, L.Etiqueta);

        }
    | expresion_booleana Y expresion_booleana
        {
            L = new Etiqueta(numEti++);
            Operaciones.backpatch(lc,((Atributos) $1).listTrue, L.Etiqueta);
            Atributos aux = new Atributos();
            aux.listFalse = Operaciones.combinar(((Atributos) $1).listFalse, ((Atributos) $3).listFalse);
            aux.listTrue = ((Atributos) $3).listTrue;
            $$ = aux;
            lc.agregaCuadrupla("label", null, null, L.Etiqueta);
        }
    | NO expresion_booleana
    {
        Atributos aux= new Atributos();
        aux.listTrue=((Atributos)$2).listFalse;
        aux.listFalse=((Atributos)$2).listTrue;
        $$ = aux;
    }
    | relacional
    {
        Atributos aux= new Atributos();
        aux.listTrue=((Atributos)$1).listTrue;
        aux.listFalse=((Atributos)$1).listTrue;
        $$ = aux;
    }
    | VERDADERO
    {
        i1= new Index(numEti++);
        Atributos aux= new Atributos();
        aux.listTrue= new LinkedList();
        aux.listFalse= new LinkedList();
        aux.listTrue.add(i1.index);
        $$ = aux;
        lc.agregaCuadrupla("goto",null,null,""+i1.index);
    }
    | FALSO
    {
        i1= new Index(numEti++);
        Atributos aux= new Atributos();
        aux.listTrue=new LinkedList();
        aux.listFalse= new LinkedList();
        aux.listFalse.add(i1.index);
        $$ = aux;
        lc.agregaCuadrupla("goto",null,null,""+i1.index);
    }
;
relacional: relacional MEN relacional
    {
        Atributos aux= new Atributos();
        aux.listTrue=new LinkedList();
        aux.listFalse=new LinkedList();
        i1= new Index(numEti++);
        i2= new Index(numEti++);
        aux.listTrue.add(i1.index);
        aux.listFalse.add(i2.index);
        aux.tipo=Operaciones.max(((Atributos)$1).tipo,((Atributos)$3).tipo);
        a1=Operaciones.ampliar(((Atributos)$1).dir,((Atributos)$1).tipo,((Atributos)$$).tipo,lc);
        a2=Operaciones.ampliar(((Atributos)$3).dir,((Atributos)$3).tipo,((Atributos)$$).tipo,lc);
        lc.agregaCuadrupla("<",""+a1,""+a2,""+i1.index);
        lc.agregaCuadrupla("goto",null,null,""+i2.index);
        $$ = aux;
    }
    | relacional MAY relacional
    {
        Atributos aux= new Atributos();
        aux.listTrue=new LinkedList();
        aux.listFalse=new LinkedList();
        i1= new Index(numEti++);
        i2= new Index(numEti++);
        aux.listTrue.add(i1.index);
        aux.listFalse.add(i2.index);
        aux.tipo=Operaciones.max(((Atributos)$1).tipo,((Atributos)$3).tipo);
        a1=Operaciones.ampliar(((Atributos)$1).dir,((Atributos)$1).tipo,((Atributos)$$).tipo,lc);
        a2=Operaciones.ampliar(((Atributos)$3).dir,((Atributos)$3).tipo,((Atributos)$$).tipo,lc);
        lc.agregaCuadrupla(">",""+a1,""+a2,""+i1.index);
        lc.agregaCuadrupla("goto",null,null,""+i2.index);
        $$ = aux;
    }
    | relacional MENI relacional
    {
        Atributos aux= new Atributos();
        aux.listTrue=new LinkedList();
        aux.listFalse=new LinkedList();
        i1= new Index(numEti++);
        i2= new Index(numEti++);
        aux.listTrue.add(i1.index);
        aux.listFalse.add(i2.index);
        aux.tipo=Operaciones.max(((Atributos)$1).tipo,((Atributos)$3).tipo);
        a1=Operaciones.ampliar(((Atributos)$1).dir,((Atributos)$1).tipo,((Atributos)$$).tipo,lc);
        a2=Operaciones.ampliar(((Atributos)$3).dir,((Atributos)$3).tipo,((Atributos)$$).tipo,lc);
        lc.agregaCuadrupla("<=",""+a1,""+a2,""+i1.index);
        lc.agregaCuadrupla("goto",null,null,""+i2.index);
        $$ = aux;
    }
    | relacional MAYI relacional
    {
        Atributos aux= new Atributos();
        aux.listTrue=new LinkedList();
        aux.listFalse=new LinkedList();
        i1= new Index(numEti++);
        i2= new Index(numEti++);
        aux.listTrue.add(i1.index);
        aux.listFalse.add(i2.index);
        aux.tipo=Operaciones.max(((Atributos)$1).tipo,((Atributos)$3).tipo);
        a1=Operaciones.ampliar(((Atributos)$1).dir,((Atributos)$1).tipo,((Atributos)$$).tipo,lc);
        a2=Operaciones.ampliar(((Atributos)$3).dir,((Atributos)$3).tipo,((Atributos)$$).tipo,lc);
        lc.agregaCuadrupla(">=",""+a1,""+a2,""+i1.index);
        lc.agregaCuadrupla("goto",null,null,""+i2.index);
        $$ = aux;
    }
    | relacional II relacional
    {
        Atributos aux= new Atributos();
        aux.listTrue=new LinkedList();
        aux.listFalse=new LinkedList();
        i1= new Index(numEti++);
        i2= new Index(numEti++);
        aux.listTrue.add(i1.index);
        aux.listFalse.add(i2.index);
        aux.tipo=Operaciones.max(((Atributos)$1).tipo,((Atributos)$3).tipo);
        a1=Operaciones.ampliar(((Atributos)$1).dir,((Atributos)$1).tipo,((Atributos)$$).tipo,lc);
        a2=Operaciones.ampliar(((Atributos)$3).dir,((Atributos)$3).tipo,((Atributos)$$).tipo,lc);
        lc.agregaCuadrupla("==",""+a1,""+a2,""+i1.index);
        lc.agregaCuadrupla("goto",null,null,""+i2.index);
        $$ = aux;
    }
    | relacional MM relacional
    {
        Atributos aux= new Atributos();
        aux.listTrue=new LinkedList();
        aux.listFalse=new LinkedList();
        i1= new Index(numEti++);
        i2= new Index(numEti++);
        aux.listTrue.add(i1.index);
        aux.listFalse.add(i2.index);
        aux.tipo=Operaciones.max(((Atributos)$1).tipo,((Atributos)$3).tipo);
        a1=Operaciones.ampliar(((Atributos)$1).dir,((Atributos)$1).tipo,((Atributos)$$).tipo,lc);
        a2=Operaciones.ampliar(((Atributos)$3).dir,((Atributos)$3).tipo,((Atributos)$$).tipo,lc);
        lc.agregaCuadrupla("<>",""+a1,""+a2,""+i1.index);
        lc.agregaCuadrupla("goto",null,null,""+i2.index);
        $$ = aux;
    }
    | expresion
    {
        $$=$1;
    }
;
expresion: expresion MAS expresion
    {
        Atributos mas=new Atributos();
        mas.tipo=Operaciones.max(((Atributos)$1).tipo, ((Atributos)$3).tipo);
        mas.dir=new Temp(numEti++).dir;
        $$=mas;

        a1=Operaciones.ampliar(((Atributos)$1).dir,((Atributos)$1).tipo,((Atributos)$$).tipo,lc);
        a2=Operaciones.ampliar(((Atributos)$3).dir,((Atributos)$3).tipo,((Atributos)$$).tipo,lc);
        lc.agregaCuadrupla("+",Integer.toString(a1),Integer.toString(a2),""+((Atributos)$$).dir);
    }
    | expresion MENOS expresion
    {
        Atributos min=new Atributos();
        min.tipo=Operaciones.max(((Atributos)$1).tipo, ((Atributos)$3).tipo);
        min.dir=new Temp(numEti++).dir;
        $$=min;

        a1=Operaciones.ampliar(((Atributos)$1).dir,((Atributos)$1).tipo,((Atributos)$$).tipo,lc);
        a2=Operaciones.ampliar(((Atributos)$3).dir,((Atributos)$3).tipo,((Atributos)$$).tipo,lc);
        lc.agregaCuadrupla("-",Integer.toString(a1),Integer.toString(a2),""+((Atributos)$$).dir);
    }
    | expresion POR expresion
    {
        Atributos por=new Atributos();
        por.tipo=Operaciones.max(((Atributos)$1).tipo, ((Atributos)$3).tipo);
        por.dir=new Temp(numEti++).dir;
        $$=por;

        a1=Operaciones.ampliar(((Atributos)$1).dir,((Atributos)$1).tipo,((Atributos)$$).tipo,lc);
        a2=Operaciones.ampliar(((Atributos)$3).dir,((Atributos)$3).tipo,((Atributos)$$).tipo,lc);
        lc.agregaCuadrupla("*",Integer.toString(a1),Integer.toString(a2),""+((Atributos)$$).dir);
    }
    | expresion DIV expresion
    {
        Atributos div=new Atributos();
        div.tipo=Operaciones.max(((Atributos)$1).tipo, ((Atributos)$3).tipo);
        div.dir=new Temp(numEti++).dir;
        $$=div;

        a1=Operaciones.ampliar(((Atributos)$1).dir,((Atributos)$1).tipo,((Atributos)$$).tipo,lc);
        a2=Operaciones.ampliar(((Atributos)$3).dir,((Atributos)$3).tipo,((Atributos)$$).tipo,lc);
        lc.agregaCuadrupla("/",Integer.toString(a1),Integer.toString(a2),""+((Atributos)$$).dir);
    }
    | expresion PRC expresion 
    {
        Atributos mod=new Atributos();
        mod.tipo=Operaciones.max(((Atributos)$1).tipo, ((Atributos)$3).tipo);
        mod.dir=new Temp(numEti++).dir;
        $$=mod;

        a1=Operaciones.ampliar(((Atributos)$1).dir,((Atributos)$1).tipo,((Atributos)$$).tipo,lc);
        a2=Operaciones.ampliar(((Atributos)$3).dir,((Atributos)$3).tipo,((Atributos)$$).tipo,lc);
        lc.agregaCuadrupla("%",Integer.toString(a1),Integer.toString(a2),""+((Atributos)$$).dir);
    }
    | LPAR expresion RPAR
    {
        $$=(Atributos)$2;
    }
    | variable 
    {
        Atributos var1= new Atributos();
        var1.dir = new Temp(numEti++).dir;
        var1.tipo = ((Atributos)$1).tipo;
        lc.agregaCuadrupla("*", "" + ((Atributos)$1).base.getId(), null, "" +  var1.dir);
        $$ = var1;

    }
    | NUM 
        {
            Atributos aux = new Atributos();
            if ($1 instanceof Integer){
                aux.dir = ((Integer) $1);
                aux.tipo = Tipo.ent;
            }
            else{
                aux.tipo = Tipo.real;
                aux.dir = ((Integer) $1);
            }
            $$ = aux;
        }
    | CADENA 
    {
        Atributos cad= new Atributos();
        cad.tipo=Tipo.car;
        $$=cad;
    }
    | CARACTER
    {
        Atributos car= new Atributos();
        car.tipo=Tipo.car;
        $$=car;
    }
    | ID LPAR parametros RPAR
    {
        if(Pilas.stackSimbolos.firstElement().getID((String)$1)){
            if(Pilas.stackSimbolos.firstElement().getTipoVar((String)$1).equals("func")){
                LinkedList<Integer> listaAux=Pilas.stackSimbolos.firstElement().getListParam((String)$1);
                if(listaAux.size()!=((Atributos)$3).lista.size()){
                    throw new CompilerException("Semantic Error: the number of arguments do not match " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
                }else{
                    for(int i=0;i<((Atributos)$3).lista.size();i++){
                        if(!listaAux.get(i).equals(((Atributos)$3).lista.get(i))){
                            System.out.println("El tipo de parametros no coincide");
                            throw new CompilerException("Semantic Error: the arguments do not match " + " in line: " + lexer.getYyline() + "\nBUILD FAILED");
                        }
                    }
                }
                Atributos exprAux= new Atributos();
                exprAux.dir=new Temp(numEti++).dir;
                exprAux.tipo=Pilas.stackSimbolos.firstElement().getTipo((String)$1);
                $$=exprAux;
                lc.agregaCuadrupla("=","call",""+(String)$1,""+exprAux.dir);
            }else{
                 throw new CompilerException("Semantic Error: '" + (String)$1 + "' the function does not exist " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
            }
        }else{
            throw new CompilerException("Semantic Error: '" + (String)$1 + "' the identifier does not exist " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
        }
    }
;
variable: ID arreglo 
        {
            if(Pilas.stackSimbolos.peek().getID((String) $1) || Pilas.stackSimbolos.firstElement().getID((String) $1)){
                Atributos aux = new Atributos();
                aux.dir = ((Atributos) $2).dir;
                aux.base = ((Atributos) $2).base;
                aux.tipo = ((Atributos) $2).tipo;
                $$ = aux;
            }
            else{
                throw new CompilerException("Semantic Error: '" + (String)$1 + "' the identifier does not exist " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
            }
        }
    | ID PNT ID 
        {
            if(Pilas.stackSimbolos.peek().getID((String) $1)){
                Tipo tt = Pilas.stackSimbolos.firstElement().getTipo((String) $1);
                Tipo tt2 = Pilas.stackTipos.firstElement().tablaTipos.get(tt.getId());
                if(tt2.getTipo().equals("registro")){
                    Tipo tipoBase = Pilas.stackTipos.firstElement().getTipoBase(tt2.getId());
                    if(tipoBase.getId() != -1){
                        Atributos var2 = new Atributos();
                        var2.tipo = tipoBase;
                        //var.dir = ((String) $3);
                        //var.base = ((String) $1);
                        $$ = var2;
                    }
                    else{
                        throw new CompilerException("Semantic Error: '" + (String)$1 + "' the structure does not exist " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
                    }
                }
                else{
                    throw new CompilerException("Semantic Error: '" + (String)$1 + "' the structure does not exist " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
                }
            }
            else{
                throw new CompilerException("Semantic Error: '" + (String)$1 + "' the structure does not exist " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
            }
        }
;
arreglo: ID LCOR expresion RCOR
    {
        if(Pilas.stackSimbolos.peek().getID((String)$1)){
            Tipo t=Pilas.stackSimbolos.peek().getTipo((String)$1);
            if(Pilas.stackTipos.peek().getTipo(t.getId()).equals("array")){
                if(((Atributos)$3).tipo.equals("ent")){
                    Atributos auxarr= new Atributos();
                    auxarr.base=((Atributos)$1).base;
                    auxarr.tipo=Pilas.stackTipos.peek().getTipoBase(t.getId());
                    auxarr.size=Pilas.stackTipos.peek().getTam(((Atributos)$$).tipo.getId());
                    auxarr.dir=new Temp(numEti++).dir;
                    Temp temp=new Temp(numEti++);
                    $$=auxarr;
                    lc.agregaCuadrupla("*", ""+((Atributos)$3).dir, ""+((Atributos)$$).size, ""+((Atributos)$$).dir);
                    
                }else{
                    throw new CompilerException("Semantic Error: the index must be 'ent' " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
                }
            }else{
                throw new CompilerException("Semantic Error: '" + (String)$1 + "' the identifier is not an array " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
            }
        }
    }
    |arreglo LCOR expresion RCOR 
    {
        
        if(Pilas.stackTipos.peek().getTipoBase(((Atributos)$1).tipo.getId()).equals("array")){
            if((((Atributos)$3).tipo).equals("ent")){
                Atributos auxarr= new Atributos();
                auxarr.base=((Atributos)$1).base;
                auxarr.tipo=Pilas.stackTipos.peek().getTipoBase(((Atributos)$1).tipo.getId());
                auxarr.size=Pilas.stackTipos.peek().getTam(((Atributos)$$).tipo.getId());
                auxarr.dir=new Temp(numEti++).dir;
                Temp temp=new Temp(numEti);
                $$=auxarr;
                lc.agregaCuadrupla("*", ""+((Atributos)$3).dir, ""+((Atributos)$1).size, ""+temp.temp);
                lc.agregaCuadrupla("+",""+((Atributos)$1).dir,""+temp.temp,""+((Atributos)$1).dir);

            }else{
                throw new CompilerException("Semantic Error: the index must be 'ent' " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
            }
        }else{
           throw new CompilerException("Semantic Error: '" + (String)$1 + "' the identifier is not an array " +"in line: " + lexer.getYyline() + "\nBUILD FAILED");
        }
    }
    |
;
parametros: lista_param
    {
        $$ = $1;
    }
    |
    {
        $$ = new Atributos();
    }
;
lista_param: lista_param COMA expresion
    {
        Atributos auxlist= new Atributos();
        auxlist.lista=new LinkedList<Tipo>();
        auxlist.lista.add(((Atributos)$3).tipo);
        $$ = auxlist;
        lc.agregaCuadrupla("param",""+((Atributos)$3).dir,null,null);
    }
    | expresion
    {
        Atributos auxlist= new Atributos();
        auxlist.lista=new LinkedList<Tipo>();
        auxlist.lista.add(((Atributos)$1).tipo);
        $$ = auxlist;
        lc.agregaCuadrupla("param",""+((Atributos)$1).dir,null,null);
    }
;
%%

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
