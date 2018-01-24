/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.SampleAI;

import dev.CodeRulers.entity.Castle;
import dev.CodeRulers.entity.Knight;
import dev.CodeRulers.entity.Peasant;
import dev.CodeRulers.game.CodeRulers;
import dev.CodeRulers.ruler.AbstractRuler;
import dev.CodeRulers.world.World;
import java.awt.Color;
import java.util.Arrays;

/**
 * I am using this class for graphical testing. I also added a game-breaking bug
 *
 * @author Sean Zhang
 */
public class SeanBot extends AbstractRuler {

    @Override
    public void orderSubjects() {
        if (this.getCastles().length == 1 && this.getKnights().length<20) {
            for (Castle castle : this.getCastles()) {
                for (Knight knight : this.getKnights()) {
                    for (Castle otherCastle : World.getAllCastles()) {
                        knight.capture(otherCastle);
                    }
                    
                    for(Knight knightss:this.getOtherKnights()){
                        knight.capture(knightss);
                    }
                    
                    for(Peasant p : this.getOtherPeasants()) {
                        knight.capture(p);
                    }
                    
                    move(knight,knight.getDirectionTo(castle.getX(), castle.getY()));
                }
            }
        } else if(this.getCastles().length==1 && this.getKnights().length>=20) {
            for(Castle castle: this.getCastles()) {
                for (int i=0;i<20;i++) {
                    for (Castle otherCastle : World.getAllCastles()) {
                        this.getKnights()[i].capture(otherCastle);
                    }
                    
                    for(Knight knight:this.getOtherKnights()){
                        this.getKnights()[i].capture(knight);
                    }
                    
                    for(Peasant p : this.getOtherPeasants()) {
                        this.getKnights()[i].capture(p);
                    }
                    
                    move(this.getKnights()[i],this.getKnights()[i].getDirectionTo(castle.getX(), castle.getY()));
                }
            }
            
            for(int i=20;i<this.getKnights().length;i++) {
                int avgx=0,avgy=0;
                    for(Knight k : this.getKnights()) {
                        avgx+=k.getX();
                        avgy+=k.getY();
                    }
                    
                    avgx/=this.getKnights().length;
                    avgy/=this.getKnights().length;
                    
                    
                    int smallestDistance=100000;
                    
                    Castle targetCastle=null;
                    for(Castle c :this.getOtherCastles()) {
                        if(c.getDistanceTo(avgx, avgy)<=smallestDistance) {
                            targetCastle=c;
                            smallestDistance=c.getDistanceTo(avgx, avgy);
                        }
                    }
                    
                    move(this.getKnights()[i],this.getKnights()[i].getDirectionTo(targetCastle.getX(), targetCastle.getY()));
            }
        } else if(this.getCastles().length==0) {
            for (int i=0;i<this.getCastles().length;i++) {
                    for (Castle otherCastle : World.getAllCastles()) {
                        this.getKnights()[i].capture(otherCastle);
                    }
                    
                    for(Knight knight:this.getOtherKnights()){
                        this.getKnights()[i].capture(knight);
                    }
                    
                    for(Peasant p : this.getOtherPeasants()) {
                        this.getKnights()[i].capture(p);
                    }
                    
                    int avgx=0,avgy=0;
                    for(Knight k : this.getKnights()) {
                        avgx+=k.getX();
                        avgy+=k.getY();
                    }
                    
                    avgx/=this.getKnights().length;
                    avgy/=this.getKnights().length;
                    
                    
                    int smallestDistance=100000;
                    
                    Castle targetCastle=null;
                    for(Castle c :this.getOtherCastles()) {
                        if(c.getDistanceTo(avgx, avgy)<=smallestDistance) {
                            targetCastle=c;
                            smallestDistance=c.getDistanceTo(avgx, avgy);
                        }
                    }
                    
                    move(this.getKnights()[i],this.getKnights()[i].getDirectionTo(targetCastle.getX(), targetCastle.getY()));
                }
        }

        this.getOtherKnights();
        this.getOtherPeasants();
        this.getOtherCastles();

        this.getPeasants();
        this.getCastles();
        this.getKnights();
    }

    @Override
    public void initialize() {
        //Message telling the user that this object has been intialized.
        System.out.println("Ruler Sean Zhang been initialized");

        //this sets the profileURL of this AI. All you have to do is to provide
        //an internet link to the image.
        profileURL = ("https://avatars1.githubusercontent.com/u/20467017?s=460&v=4");

        //this is the preferred color for my AI. This color will be the main 
        //color scheme displayed in the GUI ` for this AI.
        setColor(new Color(139, 91, 183, 178));
    }

    @Override
    public String getSchoolName() {
        return "Newmarket High School";
    }

    @Override
    public String getRulerName() {
        return "Sean Zhang";
    }

}
