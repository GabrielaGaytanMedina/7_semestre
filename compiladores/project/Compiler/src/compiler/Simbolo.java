package compiler;

import java.util.LinkedList;

/**
 *
 * @author Jorge
 */

public class Simbolo {

    private Tipo tipo;
    private Integer dir;
    private String var;
    private String id;
    private LinkedList param;
    
    public Simbolo(String id, Tipo tipo, Integer dir, String var, LinkedList param) {
        this.tipo = tipo;
        this.dir = dir;
        this.var = var;
        this.param = param;
    }
    
    public Simbolo(Tipo tipo, Integer dir, String var){
        this.tipo = tipo;
        this.dir = dir;
        this.var = var;
    }
    
    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Integer getDir() {
        return dir;
    }

    public void setDir(Integer dir) {
        this.dir = dir;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }
    
    public String getID(){
        return this.id;
    }
    
    public void setID(String id){
        this.id=id;
    }

    public LinkedList getParam() {
        return param;
    }

    public void setParam(LinkedList param) {
        this.param = param;
    }
    
    
}
