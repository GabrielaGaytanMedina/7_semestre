/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cuadruplas;

/**
 *
 * Autores: 
 *      Murrieta Villegas Alfonso
 *      Reza Chavarría Sergio Gabriel
 * 19 - 11 - 2019
 */
public class PruebaCuadrupla {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Cuadruplas c1=new Cuadruplas("+","R1","R2","total");
        Cuadruplas c2=new Cuadruplas("*","R5","R7","Cantidad");
        Cuadruplas c3=new Cuadruplas("/","R9","R2","Dato");
        ListaCuadrupla lc=new ListaCuadrupla();
        
        System.out.println("Pruebas cuadruplas");
        System.out.println(c1.toString());
        System.out.println(c2.toString());
        System.out.println(c3.toString());
        c1.eliminaCuad();
        System.out.println(c1);
        
        System.out.println("Pruebas lista");
        lc.agregaCuadrupla("-","Dato1","Dato2", "res");
        lc.agregaCuadrupla("+","t0","t1","t1");
        lc.agregaCuadrupla(c2);
        lc.agregaCuadrupla(c3);
        
        System.out.println("Codigo Actual");
        lc.Codigo();
        
        System.out.println("Eliminacion");
        lc.eliminaCode();
        
        lc.Codigo();
        
    }
    
}
