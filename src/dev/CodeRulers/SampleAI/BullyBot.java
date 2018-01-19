/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.SampleAI;

import dev.CodeRulers.ruler.*;
import dev.CodeRulers.entity.*;
import dev.CodeRulers.world.World;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author Luke Klassen
 */
public class BullyBot extends AbstractRuler {
    
    //represents how much this ruler is being threatened by nearby Knights
    int danger = 0;
    //the ruler ID which is targeted by this AI
    int target;
    Castle attacking;
    //represents the location of this castle in relation to the others, 
    //1 is northeast, 4 northwest
    int corner;
    //The direction in which peasants claim land and knights attack
    int dir;
    //the current number of castles under this ruler
    int numCastles = 1;
    Random rd = new Random();

    @Override
    public void initialize() {
        Castle first = World.getCastles()[0];
        if(first.getX() > 32 && first.getY() < 32){
            corner = 1;
            dir = 6;
        }else if(first.getX() > 32 && first.getY() >= 32){
            corner = 2;
            dir = 8;
        }else if(first.getX() <= 32 && first.getY() >= 32){
            corner = 3;
            dir = 2;
        }else{
            corner = 4;
            dir = 4;
        }
    }
    @Override
    public void orderSubjects() {
        orderPeasants();
        if(getCastles().length < 1){
            capture();
        }
        //if it is within the first 20 turns
        if( true){
            setUp();
        }else if( false){
            capture();
        }    
        if(getCastles().length >1){
            cleanUp();
        }    
    }
    
    private void setUp(){
        Knight[] myK = getKnights();
        for(Knight k : myK){
            if(World.getLandOwner(k.getX(), k.getY()) != rulerID){
                k.move(dir);
            }else{
                k.move(dir + 1);
            }
        }
        if(true){
            assignNewTarget();
        }
    }
    private void capture(){
        Knight[] myK = getKnights();

        for(Knight k : myK){
           k.move (k.getDirectionTo(attacking.getX(), attacking.getY()));
        }
    }
    private void cleanUp(){
       Knight[] myK = getKnights();
       Peasant[] myP = getPeasants();
       Peasant minorTarget = null;
       for(Peasant p : myP){
           if(p.getRuler() == target){
               minorTarget = p;
           }
       }
       if(minorTarget != null){
            for(Knight k: myK){
                k.move(k.getDirectionTo(minorTarget.getX(), minorTarget.getY()));
            }
       }else{
           assignNewTarget();
           //move to capture phase again
       }
    }
    
    private void orderPeasants(){
        Peasant[] myP = getPeasants();
          //te  
        for(Peasant p : myP){
            if(World.getLandOwner(p.getX(), p.getY()) != rulerID){
                p.move(dir);
            }else{
                p.move(dir + rd.nextInt(2)-1);
            }
        }
    }
    private void assignNewTarget(){
        Castle[] myC = getCastles();
        for(Castle c: myC){
            c.createKnights();
        }
        int[][] other = World.getLandOwned();
        int[] rulers = new int[4];
        for(int[] array: other){
            for(int i: array){
                if(i != rulerID)
                rulers[i]++;
            }
        }
        Arrays.sort(rulers);
        target = rulers[1];

        Castle[] allCastles = World.getCastles();
        for(Castle c: allCastles){
            attacking = c;
        }
    }
    
    

    @Override
    public String getSchoolName() {
        return "The FlexQueue Dev. Team";
    }

    @Override
    public String getRulerName() {
        return "Bender";
    }
    
}
