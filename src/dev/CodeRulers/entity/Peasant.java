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
public class Peasant extends Entity{
    
    public Peasant(int x, int y, int ruler) {
        super(x, y, ruler);
        
    }

    @Override
    public boolean hasAction(){
        return hasAction;
    }

    @Override
    public void move(int dir) {
        if(changePos(dir)){
            //claim this peice of land
        }else{
            System.out.println("Illegal Movement attempted by ruler " + ruler +
                    " and Peasant at " + x + " , " + y );
        }
        
    }
}
