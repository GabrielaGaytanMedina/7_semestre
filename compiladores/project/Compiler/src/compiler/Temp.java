/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiler;

/**
 *
 * @author Jorge
 */
public class Temp {
    Tipo temp;
    Simbolo simb;
    int dir;
    public Temp(int num){
        this.temp=new Tipo("Temp",null,null,8);
        this.simb= new Simbolo("Temp"+num,this.temp,8,"temp",null);
        this.dir = num;
    }
}
