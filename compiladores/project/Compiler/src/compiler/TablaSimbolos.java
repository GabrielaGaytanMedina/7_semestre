package compiler;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author Jorge
 */
public class TablaSimbolos {
    
    public Map<String, Simbolo> tablaSimbolos = new HashMap(); // Clave o id unico 

   
    public void addSimbol(String id, Simbolo simbolo) {
        tablaSimbolos.put(id, simbolo);
    }

    public void addSimbol(String id, Simbolo simbolo, LinkedList params) {
        tablaSimbolos.put(id, simbolo);
    }

    boolean buscar(String id){
        return tablaSimbolos.containsKey(id);
    }
    
    Tipo getTipo(String id){
        if( tablaSimbolos.containsKey(id) ){
            return tablaSimbolos.getOrDefault(id, null).getTipo();
        }else
            return null;

    }
    
    String getTipoVar(String id){
        if( tablaSimbolos.containsKey(id) ){
            return tablaSimbolos.getOrDefault(id, null).getVar();
        }else
            return null;    
    }
    
    boolean getID(String id){
        if(tablaSimbolos.containsKey(id))
            return true;
        return false;
    }
    
    LinkedList getListParam(String id){
        if( tablaSimbolos.containsKey(id) ){
            return tablaSimbolos.getOrDefault(id, null).getParam();
        }else
            return null;
    }
    
    int getNumParam(String id){
        if( tablaSimbolos.containsKey(id) ){
            return tablaSimbolos.getOrDefault(id, null).getParam().size();
        }else
            return -1;
    }
}
