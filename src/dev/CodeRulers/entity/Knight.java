/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.entity;

/**
 *
 * @author seanz
 */
public class Knight extends Entity{
    int[] thing;
    
    public Knight(int x, int y, int ruler,int[] thing) {
        super(x, y, ruler);
        this.thing = thing;
        
    }

    @Override
    public boolean hasAction(){
        return hasAction;
    }
    
    @Override
    public void move(int dir){
        //if the piece can be move correctly in that direction
        if(changePos(dir)){
            //do nothing. Its a knight
        }else{
            System.out.println("Illegal Movement attempted by " + ruler +
                    " and Knight at " + x + " , " + y );
        }    
    }
}
