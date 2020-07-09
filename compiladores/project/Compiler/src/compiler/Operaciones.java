/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 *
 * @author Jorge
 */
public class Operaciones {
    
    public static Tipo max(Tipo expr1, Tipo expr2){
        int dato1=0;
        int dato2=0;
        
        if(expr1.getTipo().equals("dreal")){
            dato1=4;
        }else if(expr1.getTipo().equals("real")){
            dato1=3;
        }else if(expr1.getTipo().equals("ent")){
            dato1=2;
        }else if(expr1.getTipo().equals("car")){
            dato1=1;
        }
        if(expr2.getTipo().equals("dreal")){
            dato2=4;
        }else if(expr2.getTipo().equals("real")){
            dato2=3;
        }else if(expr2.getTipo().equals("ent")){
            dato2=2;
        }else if(expr2.getTipo().equals("car")){
            dato2=1;
        }
        
        if(dato1>=dato2){
            switch(dato1){
                case 1:
                    return new Tipo("car", expr1.getNumElemtos(),null,expr1.getSize());
                case 2:
                    return new Tipo("ent", expr1.getNumElemtos(),null,expr1.getSize());
                case 3:
                    return new Tipo("real", expr1.getNumElemtos(),null,expr1.getSize());
                case 4:
                    return new Tipo("dreal", expr1.getNumElemtos(),null,expr1.getSize());
            }
        
        }else{
            switch(dato2){
                case 1:
                    return new Tipo("car", expr2.getNumElemtos(),expr2.getTipoBase(),expr2.getSize());
                case 2:
                    return new Tipo("ent", expr2.getNumElemtos(),expr2.getTipoBase(),expr2.getSize());
                case 3:
                    return new Tipo("real", expr2.getNumElemtos(),expr2.getTipoBase(),expr2.getSize());
                case 4:
                    return new Tipo("dreal", expr2.getNumElemtos(),expr2.getTipoBase(),expr2.getSize());
            }
        }
        return null;
        
        
        
    }
    public static int ampliar(int dir, Tipo t, Tipo w , ListaCuadrupla lc){
        if(t.getTipo().equals(w.getTipo()))
            return dir;
        else if(t.getTipo().equals("ent")&&w.getTipo().equals("real")){
            int temp=dir;
            lc.agregaCuadrupla("=", "(real)", "a" ,"temp");//Detalle 
            return temp;
        }else if(t.getTipo().equals("ent")&&w.getTipo().equals("dreal")){
            int temp=dir;
            lc.agregaCuadrupla("=", "(dreal)", "a" ,"temp");//Detalle 
            return temp;
        }else{
            System.out.println("Error");
        }
        return -1;
        
        
    }
    
    public static int reducir(int dir, Tipo t, Tipo w , ListaCuadrupla lc){
        if(t.getTipo().equals(w.getTipo())){
            return dir;
        }
        else if(t.getTipo().equals("dreal") && w.getTipo().equals("real") ){
            int temp = dir;
            lc.agregaCuadrupla("=","(real)", "a","temp");    
        }
        else if (t.getTipo().equals("real") && w.getTipo().equals("ent") ){
            int temp = dir;
            lc.agregaCuadrupla("=","(ent)", "a","temp");        
        }
        else{
            System.out.println("Error de reducción de tipo");
        }
                
        return -1;
    }
    public static void backpatch(ListaCuadrupla cuadruplas ,LinkedList next, String Etiqueta){
        for(int i =0; i < cuadruplas.listaCuad.size(); i++ ){
            for(int j =0; j < next.size(); j++ ){
                if (cuadruplas.listaCuad.get(i).operador.contains("goto") && i ==(int)next.get(j)) {
                    cuadruplas.listaCuad.get(i).resultado = Etiqueta;
                     
                }
            }
        }
    }
    
    
       public static void GenerarArchivoCode(ListaCuadrupla lc) throws IOException{
        File f = new File("Codigo3.txt");
        if(!f.exists()){
            f.createNewFile();
        }
        
        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);
        
        bw.write("Codigo de tres direcciones:\n");
        
           for (int i = 0; i < lc.listaCuad.size(); i++) {
               switch (lc.listaCuad.get(i).operador) {
                   case "Jlabel":
                       bw.write(lc.listaCuad.get(i).resultado+":\n");
                       break;
                    case "goto":
                       bw.write("\tgoto "+lc.listaCuad.get(i).resultado+"\n");
                       break;
                    case "label":
                       bw.write(lc.listaCuad.get(i).resultado+":\n");
                       break;
                    case "=":
                       bw.write("\t"+lc.listaCuad.get(i).resultado+"="+lc.listaCuad.get(i).argumento1+"\n");
                       break;
                    case "print":
                       bw.write("\tprint "+lc.listaCuad.get(i).argumento1+"\n");
                       break;
                    case "scan":
                       bw.write("\tscan "+lc.listaCuad.get(i).resultado+"\n");
                       break;
                    case "return":
                       if(lc.listaCuad.get(i).argumento1==null)
                        bw.write("\treturn\n");
                       else
                        bw.write("\treturn "+lc.listaCuad.get(i).argumento1+"\n");
                       break;
                    case "<": //dudoso
                       bw.write("\tif "+lc.listaCuad.get(i).argumento1+" < "+lc.listaCuad.get(i).argumento2+" goto "+lc.listaCuad.get(i).resultado+"\n");
                       break;
                    case ">":
                        bw.write("\tif "+lc.listaCuad.get(i).argumento1+" > "+lc.listaCuad.get(i).argumento2+" goto "+lc.listaCuad.get(i).resultado+"\n");
                       break;
                    case "<=":
                       bw.write("\tif "+lc.listaCuad.get(i).argumento1+" <= "+lc.listaCuad.get(i).argumento2+" goto "+lc.listaCuad.get(i).resultado+"\n");
                       break;
                    case ">=":
                       bw.write("\tif "+lc.listaCuad.get(i).argumento1+" >= "+lc.listaCuad.get(i).argumento2+" goto "+lc.listaCuad.get(i).resultado+"\n");
                       break;
                    case "==":
                       bw.write("\tif "+lc.listaCuad.get(i).argumento1+" == "+lc.listaCuad.get(i).argumento2+" goto "+lc.listaCuad.get(i).resultado+"\n");
                       break;
                    case "<>":
                       bw.write("\tif "+lc.listaCuad.get(i).argumento1+" <> "+lc.listaCuad.get(i).argumento2+" goto "+lc.listaCuad.get(i).resultado+"\n");
                       break;
                    case "+":
                       bw.write("\tId"+lc.listaCuad.get(i).resultado+" = "+lc.listaCuad.get(i).argumento1+" + "+lc.listaCuad.get(i).argumento2+"\n");
                       break;
                    case "-":
                       bw.write("\tId"+lc.listaCuad.get(i).resultado+" = "+lc.listaCuad.get(i).argumento1+" - "+lc.listaCuad.get(i).argumento2+"\n");
                       break;
                    case "/":
                       bw.write("\tId"+lc.listaCuad.get(i).resultado+" = "+lc.listaCuad.get(i).argumento1+" / "+lc.listaCuad.get(i).argumento2+"\n");
                       break;
                    case "*":
                       if(lc.listaCuad.get(i).argumento2!=null)
                        bw.write("\tId"+lc.listaCuad.get(i).resultado+" = "+lc.listaCuad.get(i).argumento1+" * "+lc.listaCuad.get(i).argumento2+"\n");
                       else //aqui hay algo extraño hay una produccion donde hay null se debe ver si no se multiplica res = res * arg1
                        bw.write("\tId"+lc.listaCuad.get(i).resultado+" = "+lc.listaCuad.get(i).argumento1+"\n");
                       break;
                    case "%":
                       bw.write("\tId"+lc.listaCuad.get(i).resultado+" = "+lc.listaCuad.get(i).argumento1+" % "+lc.listaCuad.get(i).argumento2+"\n");
                       break;
                       
                    case "param":
                       bw.write("\tparam "+lc.listaCuad.get(i).argumento1+"\n");
                       break;
                       
                   default:
                       throw new AssertionError();
               }
           }
        
        bw.close();
        fw.close();
        
    }
       public static LinkedList combinar(LinkedList ll1, LinkedList ll2){
            LinkedList aux = new LinkedList();
            if(ll1 != null)
                aux.addAll(ll1);
            if(ll2 != null)
                aux.addAll(ll2);
            return aux;
       }
    
    
}
