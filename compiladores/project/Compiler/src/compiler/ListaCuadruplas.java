/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.util.LinkedList;

/**
 *
 * @author Jorge
 */
public class ListaCuadruplas {
    LinkedList <Cuadruplas> listaCuad;
    int numInstrucciones;
    
    /*Creacion de la lista*/
    public ListaCuadruplas(){
        listaCuad=new LinkedList<Cuadruplas>();
        this.numInstrucciones=0;
    }
    /*Agrega a la instancia una nueva instancia de cuadrupla*/
    public void agregaCuadrupla(String op, String arg1, String arg2, String res){
        listaCuad.add(new Cuadruplas(op,arg1,arg2,res));
        this.numInstrucciones=listaCuad.size();
    }
    public void agregaCuadrupla(Cuadruplas c1){
        listaCuad.add(c1);
    }
    
    public int lineasCode(){
        return this.numInstrucciones;
    }
    public void eliminaCode(){
        listaCuad.clear();
        this.numInstrucciones=0;
    }
    
    public void Codigo(){
        if(this.numInstrucciones!=0){
            for (int i = 0; i < this.numInstrucciones; i++) {
            System.out.println(listaCuad.get(i).toString());
            }
        }else{
            System.out.println("Sin datos en el código");
        }
    } 
}
