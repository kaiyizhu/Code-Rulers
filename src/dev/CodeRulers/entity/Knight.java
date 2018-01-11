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
    
    public Knight(int x, int y, int ruler, int ID,int[] thing) {
        super(x, y, ruler, ID);
        this.thing = thing;
    }
    
}
