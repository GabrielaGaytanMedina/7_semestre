/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
*Proyecto Compiladores
* Cárdenas Cárdenas Jorge
* Murrieta Villegas Alfonso
* Reza Chavarria Sergio Gabriel
* Valdespino Mendieta Joaquin
*/
public class Compiler {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            compiler.Parser yyparser;
            if(args.length < 1)
                throw new ArrayIndexOutOfBoundsException("Fatal error: no input files");
            yyparser = new compiler.Parser(new FileReader(args[0]));
            yyparser.yyparse();
        } catch(ArrayIndexOutOfBoundsException | FileNotFoundException | CompilerException e){
            System.err.println(e.getMessage());
        } catch(Exception e){
            System.err.println("BUILD FAILED");
            e.printStackTrace();
        }
    }
    
}
