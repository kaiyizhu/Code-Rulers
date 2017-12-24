/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.graphics;

import dev.CodeRulers.entity.Entity;
import dev.CodeRulers.game.CodeRulers;
import dev.CodeRulers.game.ObjectHandler;

/**
 *
 * @author Sean Zhang
 */
public class GameCamera {
    private ObjectHandler h;
    private float xOffset, yOffset;
    
    public GameCamera(ObjectHandler h, float xOffset,float yOffset) {
        this.h=h;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }
    
    //move method. This will move the camera by the xAmt and yAmt 
    //specified in the parameter
    public void move(float xAmt,float yAmt) {
        
        //adds amt to the xOffset and yOffset
        xOffset += xAmt;
        yOffset += yAmt;
                
    }
    
    public void centerOnEntity(Entity e) {
        //sets the xOffset and yOffset to be the center of the world in relation 
        //to where the player is in the world (x,y)
        xOffset = e.getX() - h.getGame().getWidth()/2+e.getWidth()/2;
        yOffset = e.getY() - h.getGame().getHeight()/2+e.getHeight()/2;
    }
    
    //getters and setters
    public float getxOffset() {
        return xOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }
}
