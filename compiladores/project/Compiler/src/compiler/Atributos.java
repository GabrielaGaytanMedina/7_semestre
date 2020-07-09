package compiler;

import java.util.LinkedList;

/**
 *
 * @author Jorge
 */
public class Atributos {
    
    public Tipo tipo;
    public int valor;
    public int dir;
    public int size;
    public Tipo base;
    public LinkedList<Tipo> lista;
    public LinkedList<Integer> listNext;
    public LinkedList<Integer> listTrue;
    public LinkedList<Integer> listFalse;

    public Atributos() {
        this.tipo = null;
        this.valor = 0;
        this.dir = 0;
        this.size = 0;
        this.base = null;
        this.lista = null;
        this.listNext = null;
        this.listTrue = null;
        this.listFalse = null;
    }
            
            
            
            
            
}
