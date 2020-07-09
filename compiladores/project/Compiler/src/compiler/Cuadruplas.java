/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

/**
 *
 * @author Sergio
 */
public class Cuadruplas {

    String operador;
    String argumento1;
    String argumento2;
    String resultado;
    
    /*Creación de cuadrupla*/
    public Cuadruplas(String op, String arg1, String arg2, String res){
        this.operador=op;
        this.argumento1=arg1;
        this.argumento2=arg2;
        this.resultado=res;
    }
    
    /*Eliminación de info*/
    public void eliminaCuad(){
        this.operador=null;
        this.argumento1=null;
        this.argumento2=null;
        this.resultado=null;
    }
    @Override
    public String toString(){
        if(this.argumento1!=null){
            return this.resultado+"="+this.argumento1+this.operador+this.argumento2;
        }else
            return "Sin datos en la cuadrupla";
    }
}
