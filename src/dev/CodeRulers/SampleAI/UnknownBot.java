/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.SampleAI;

import dev.CodeRulers.entity.Castle;
import dev.CodeRulers.entity.Knight;
import dev.CodeRulers.entity.Peasant;
import dev.CodeRulers.entity.Entity;
import dev.CodeRulers.ruler.AbstractRuler;
import dev.CodeRulers.world.World;

/**
 *
 * @author 072935638
 */
public class UnknownBot extends AbstractRuler {

    Castle[] castles;
    Knight[] knights;
    Peasant[] peasants;

    Castle[] enemyCastles;
    Knight[] enemyKnights;
    Peasant[] enemyPeasants;

    @Override
    public void orderSubjects() {
    }

    @Override
    public void initialize() {
        castles = getCastles();
        knights = getKnights();
        peasants = getPeasants();

        enemyCastles = getOtherCastles();
        enemyKnights = getOtherKnights();
        enemyPeasants = getOtherPeasants();
    }

    @Override
    public String getSchoolName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getRulerName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void moveKnights() {
        
    }

    public void captureNearbyEntities() {
        for (Knight knight : knights) {
            //Your knights attack enemy knights
            for (Knight enemyKnight : enemyKnights) {
                //If the enemy knight is nearby
                if (knight.getDistanceTo(enemyKnight.getX(), enemyKnight.getY()) == 1) {
                    //Attack the enemy if you have more strength
                    if (knight.getStrength() > enemyKnight.getStrength()) {
                        knight.capture(enemyKnight);
                    } else {
                        knight.move(knight.getDirectionTo(2 * knight.getX() - enemyKnight.getX(), 2 * knight.getY() - enemyKnight.getY()));
                    }
                }
            }

            for (Peasant enemyPeasant : enemyPeasants) {
                //If the enemy peasant is besdie you, capture it
                if (knight.getDistanceTo(enemyPeasant.getX(), enemyPeasant.getY()) == 1) {
                    knight.capture(enemyPeasant);
                } else if (knight.getDistanceTo(enemyPeasant.getX(), enemyPeasant.getY()) < 4) {//If the enemy peasant is nearby, then move towards it
                    knight.move(knight.getDirectionTo(enemyPeasant.getX(), enemyPeasant.getY()));
                }
            }

            for (Castle enemyCastle : enemyCastles) {
                //Attack the castle if the knight can attack it
                if (knight.getDistanceTo(enemyCastle.getX(), enemyCastle.getY()) == 1) {
                    knight.capture(enemyCastle);
                } else if (knight.getDistanceTo(enemyCastle.getX(), enemyCastle.getY()) < 4) {//If the knight is close to the enemy castle, then move towards it
                    knight.move(knight.getDirectionTo(enemyCastle.getX(), enemyCastle.getY()));
                }
            }
        }
    }

    //Finds if one of our knights is nearby
    private boolean knightNearby(Entity entity, int dist) {
        for (Knight knight : knights) {
            if (entity.getDistanceTo(knight.getX(), knight.getY()) <= dist) {
                return true;
            }
        }

        return false;
    }
}
