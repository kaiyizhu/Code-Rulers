/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.SampleAI;

import dev.CodeRulers.entity.Castle;
import dev.CodeRulers.entity.Entity;
import dev.CodeRulers.entity.Knight;
import dev.CodeRulers.entity.Peasant;
import dev.CodeRulers.ruler.AbstractRuler;
import dev.CodeRulers.world.World;

/**
 *
 * @author 072935638
 */
public class UnknownBot extends AbstractRuler {

    Knight[] knights;
    Peasant[] peasants;
    Castle[] castles;

    Knight[] eKnights;
    Peasant[] ePeasants;
    Castle[] eCastles;

    boolean creatingKnights = true;

    @Override
    public void orderSubjects() {
        knights = this.getKnights();
        peasants = this.getPeasants();
        castles = this.getCastles();

        eKnights = this.getOtherKnights();
        ePeasants = this.getOtherPeasants();
        eCastles = this.getOtherCastles();

        createEntities();
        moveKnights();
        movePeasants();
    }

    private void moveKnights() {
        Castle closestCastle = new Castle(150, 150, 150);

        //Find closest castle to attack
        for (Castle castle : eCastles) {
            if (castles[0].getDistanceTo(castle.getX(), castle.getY()) <= castles[0].getDistanceTo(closestCastle.getX(), closestCastle.getY())) {
                closestCastle = castle;
            }
        }

        //Move towards the castle
        for (Knight knight : knights) {
            //Capture any capturable entities
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    Entity entity = World.getEntityAt(knight.getX() + x, knight.getY() + y);

                    //If the nearby entity is a knight and it has lower strength than this knight, then attack it
                    if (entity instanceof Knight) {
                        if (((Knight) entity).getStrength() < knight.getStrength()) {
                            knight.capture(entity);
                        }
                    } else if (entity instanceof Peasant || entity instanceof Castle) {
                        knight.capture(entity);
                    }
                }
            }
            knight.move(knight.getDirectionTo(closestCastle.getX(), closestCastle.getY()));
        }
    }

    private void createEntities() {
        for (Castle castle : castles) {
            if (creatingKnights) {
                castle.createKnights();
                creatingKnights = false;
            } else {
                castle.createPeasants();
                creatingKnights = true;
            }
        }
    }

    private void movePeasants() {
        for (Peasant peasant : peasants) {
            //Search for uncaptured tile around the peasant
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    //Stop searching if the Land Tile is outside of the bounds
                    if(peasant.getX() + x <= 63 && peasant.getX() + x >= 0 && peasant.getY() + y >= 0 && peasant.getY() + y <= 63) {
                        break;
                    }
                    
                    //If the land owner is not ours, then move onto it
                    if (World.getLandOwner(peasant.getX() + x, peasant.getY() + y) != rulerID) {
                        peasant.move(findDir(x, y));
                    }
                }
            }
            
            //If the peasant can still move, then move in a random direction
            if(peasant.hasAction()) {
                peasant.move((int)(Math.random() * 8) + 1);
            }
        }
    }

    private int findDir(int x, int y) {
        //Calculates the direction of the tile
        double angle = Math.toDegrees(Math.asin(-y / Math.sqrt(x*x + y*y))) + 360;

        if (x < 0) {
            angle = 180 + 360 - angle;
        }
        angle %= 360;

        return (11 - (int) Math.round(angle / 45)) % 8;
    }

    @Override
    public void initialize() {
    }

    @Override
    public String getSchoolName() {
        return "NHS";
    }

    @Override
    public String getRulerName() {
        return "BestTeam";
    }

}
