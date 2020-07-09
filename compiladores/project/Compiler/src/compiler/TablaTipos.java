/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 *
 * @author Jorge
 */
public class TablaTipos {
    
    private int id = 0;
    
    public Map<Integer, Tipo> tablaTipos = new HashMap();
    
    
    public void addTipo(Tipo tipo) {
        tipo.setId(id);
        tablaTipos.put(id++, tipo);
    }
    public Tipo getTipoBase(int id){
        if( tablaTipos.containsKey(id) ){
            return tablaTipos.getOrDefault(id, null).getTipoBase();
        }else
            return null;
    }
    
    public int getTam(int id){
        if( tablaTipos.containsKey(id) ){
            return tablaTipos.getOrDefault(id, null).getSize();
        }else
            return -1;
    }
    public int getNumElem(int id){
        if( tablaTipos.containsKey(id) ){
            return tablaTipos.getOrDefault(id, null).getNumElemtos();
        }else
            return -1;
    } 
    
    public String getNombre(int id){
        if( tablaTipos.containsKey(id) ){
            return tablaTipos.getOrDefault(id, null).getTipo();
        }else
            return null;
    }
     
    public String getTipo(int id){
        if( tablaTipos.containsKey(id) ){
            return tablaTipos.getOrDefault(id, null).getTipo();
        }else
            return null;
    }
}
