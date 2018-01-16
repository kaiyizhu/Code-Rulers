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
public class Castle extends Entity{
    
    public Castle(int x, int y, int ruler) {
        super(x, y, ruler);
    }

    @Override
    public boolean hasAction() {
        return false;
    }
    
    @Override
    public void move(int dir){
        //do nothing.
    }
}
