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

    //Creates entities
    private void createEntities() {
        for (Castle castle : castles) {
            //creates peasants if there isn't enough
            if(peasants.length < 15) {
                castle.createPeasants();
            } else {
                castle.createKnights();
            }
        }
    }

    private void movePeasants() {
        for (Peasant peasant : peasants) {
            //peasant.move(findDir(peasant.getClosestUnownedTile(peasant)[0], peasant.getClosestUnownedTile(peasant)[1]));
            
            //Search for uncaptured tile around the peasant
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    //Stop searching if the Land Tile is outside of the bounds
                    if(!(peasant.getX() + x <= 63 && peasant.getX() + x >= 0 && peasant.getY() + y >= 0 && peasant.getY() + y <= 63)) {
                        break;
                    }
                    
                    //If the land owner is not ours, then move onto it
                    if (World.getLandOwner(peasant.getX() + x, peasant.getY() + y) != rulerID) {
                        peasant.move(findDir(x, y));
                    }
                }
            }
            
            //If the peasant can still move, then move towards the bottom right
            if(peasant.hasAction()) {
                peasant.move(4);
            }
        }
    }

    private int findDir(int x, int y) {
        //Calculates the direction of the tile
        double angle = Math.toDegrees(Math.asin(-y / Math.sqrt(x * x + y * y))) + 360;

        if (x < 0) {
            angle = 180 + 360 - angle;
        }
        angle %= 360;

        return (11 - (int) Math.round(angle / 45)) % 8;
    }
    
    /**
     * Gets the closest tile that is not owned by this ruler
     * @return an array of integers, the array's first number ([0]) is the x coordinate and the second number ([1]) is the y-coordinate
     * It will return null if there are no more unowned tiles
     */
    private int[] getClosestUnownedTile(Entity e) {
        //Checks until all tiles are checked
        for(int i = 1; i < 63; i ++) {
            for(int x = -i; x < i; x ++) {
                //Checks if the tile is within boundaries
                if(x + e.getX() < 0 || x + e.getX() > 63 || e.getY() + i < 0 || e.getY() + i > 63 || e.getY() - i < 0 || e.getY() + i > 63) {
                    break;
                }
                //Finds the landowner and if it is owned by this ruler
                if(World.getLandOwner(x + e.getX(), e.getY() + i) != e.getRuler()) {
                    int[] a = {e.getX() + x, e.getY() + i};
                    return a;
                } else if (World.getLandOwner(x + e.getX(), e.getY() - i) != e.getRuler()) {
                    int[] a = {e.getX() + x, e.getY() - i};
                    return a;
                }
            }
            
            for(int y = -i; y < i; y ++) {
                //Checks if the tile is within boundaries
                if(y + e.getY() < 0 || y + e.getY() > 63 || e.getX() + i < 0 || e.getX() + i > 63 || e.getX() - i < 0 || e.getX() + i > 63) {
                    break;
                }
                //Finds the landowner and if it is owned by this ruler
                if(World.getLandOwner(e.getX() + i, e.getY() + y) != e.getRuler()) {
                    int[] a = {e.getX() + i, e.getY() + y};
                    return a;
                } else if (World.getLandOwner(e.getX() - i, e.getY() + y) != e.getRuler()) {
                    int[] a = {e.getX() - i, e.getY() + y};
                    return a;
                }
            }
        }
        
        return null;
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
