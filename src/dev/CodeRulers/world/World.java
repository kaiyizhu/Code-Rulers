/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.world;

import dev.CodeRulers.entity.Castle;
import dev.CodeRulers.entity.Entity;
import dev.CodeRulers.entity.Knight;
import dev.CodeRulers.entity.Peasant;
import dev.CodeRulers.game.CodeRulers;
import dev.CodeRulers.util.IMAGE;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Random;

/**
 * This Class contains all the entities on the world.
 *
 * @author Sean Zhang
 */
public class World {

    //this is the xOffset and yOffset that allows for the capability of panning
    //in the world.
    private static int xOffset = 0, yOffset = 0;

    //scale factor for world so that we can zoom in and out.
    private static double scaleFactor;

    //image for the background of the world
    public static BufferedImage worldMap;

    //the game object
    private static CodeRulers r;

    //these are all the knights that exist in the game.
    private static Knight[] knights;
    //these are all the peasants that exist in the game.
    private static Peasant[] peasants;
    //these are all the castles that exist in the game.
    private static Castle[] castles;

    //these are all the land owned by the ruler.
    private static int[][] landOwned;
    
    /**
     * The Constructor for the World Class. This constructor initializes all the
     * entities and land tiles in the world.
     *
     * @param r the Game object that is passed in for access to the ruler
     * objects.
     */
    public World(CodeRulers r) {
        //creates an empty array of null knights.
        knights = new Knight[0];
        //creates an empty array of null peasants.
        peasants = new Peasant[0];
        //creates an empty array of null castles.
        castles = new Castle[0];

        //this is a 2D array of all the tile possesions of the rulers. A -1
        //represents that no ruler owns that tile. A number above -1 represents
        //ownership by a ruler.
        landOwned = new int[64][64];

        //stores the reference of the game object in a variable local to this class.
        this.r = r;

        //the scale factor of the map initally starts out as 1 (default)
        scaleFactor = 1;

        //this method intializes all the entities in the world.
        initGame();
    }

    /**
     * This method initializes all the entities in the world.
     */
    private static void initGame() {

        /*  this for loop goes through every array inside the 2D arrayList,
            landOwned, and fills it with -1, signifying that no one has
            ownership of the land.
         */
        for (int[] subLandOwned : landOwned) {
            Arrays.fill(subLandOwned, -1);
        }

        //creates a random object to later use to randomly assign the (x , y)
        //coordinates of the entites in the game.
        Random rand = new Random();

        /*  
            Expands the arrays by how many AIs there will be. Since each AI starts
            with 10 knights, the size of the knights array to start with will be
            the size of the ruler array multiplied by 10.
         */
        knights = Arrays.copyOf(knights, 10 * r.getRulerArray().length);

        /*
            Expands the arrays by how many AIs there will be. Since each AI starts
            with 10 peasants, the size of the peasants array to start with will 
            be the size of the ruler array multiplied by 10.
         */
        peasants = Arrays.copyOf(peasants, 10 * r.getRulerArray().length);

        /*
            Expands the arrays by how many AIs there will be. Since each AI starts
            with 1 castle, the size of the castles array to start with will be
            the size of the ruler array.
         */
        castles = Arrays.copyOf(castles, r.getRulerArray().length);

        //count for keeping track on the total number of entites added to the array.
        int count = 0;

        /*
            This for-loop goes through all the rulers in the ruler array. It then 
            initializes 10 knights and peasants in random x,y coordinates in the 
            world and 1 castle in a random x,y coordinate in the world. Each tile
            an entity is on to begin with will be assigned ownership to the ruler
            in control of that entity.
         */
        for (int i = 0; i < r.getRulerArray().length; i++) {
            //this for loop iterates 10 times to create 10 peasants and knights.
            for (int j = 0; j < 10; j++) {
                //finds a random integer between 0 and 64 for both x and y to assign
                //to an entity in the world. 
                int rndX = rand.nextInt(64), rndY = rand.nextInt(64);

                //until the random allocation finds an empty spot to claim, it
                //will continue to randomly find an empty tile to assign.
                while (landOwned[rndX][rndY] != -1) {
                    rndX = rand.nextInt(64);
                    rndY = rand.nextInt(64);
                }

                /*
                    creates a new peasant in the next vacant spot in the peasants
                    array. The land that the peasant is on is then assinged possesion
                    by the ruler controlling that peasant.
                 */
                peasants[count] = new Peasant(rndX, rndY, i);
                landOwned[peasants[count].getX()][peasants[count].getY()] = i;

                //until the random allocation finds an empty spot to claim, it
                //will continue to randomly find an empty tile to assign.
                while (landOwned[rndX][rndY] != -1) {
                    rndX = rand.nextInt(64);
                    rndY = rand.nextInt(64);
                }

                /*
                    creates a new knight in the next vacant spot in the knights
                    array. The land that the knight is on is then assinged possesion
                    by the ruler controlling that knight.
                 */
                knights[count] = new Knight(rndX, rndY, i);
                landOwned[knights[count].getX()][knights[count].getY()] = i;

                //add one to the size of the new peasant and knight array.
                count++;
            }

            //finds a random integer between 0 and 64 for both x and y to assign
            //to an entity in the world. 
            int rndX = rand.nextInt(64), rndY = rand.nextInt(64);

            //until the random allocation finds an empty spot to claim, it
            //will continue to randomly find an empty tile to assign.
            while (landOwned[rndX][rndY] != -1) {
                rndX = rand.nextInt(64);
                rndY = rand.nextInt(64);
            }

            /*
                creates a new castle in the next vacant spot in the castle
                array. The land that the castle is on is then assinged possesion
                by the ruler controlling that castle.
             */
            castles[i] = new Castle(rndX, rndY, i);
            landOwned[castles[i].getX()][castles[i].getY()] = i;
        }
    }

    /**
     * Get method that gets the 2D map of which ruler owns which tile.
     *
     * @return a 2D array containing data of the 64x64 tiles on the map.
     */
    public static int[][] getLandOwned() {
        return landOwned;
    }
    
    /**
     * 
     * @param knights 
     */
    public static void setKnights(Knight[] knights) {
        World.knights = knights;
    }

    public static void setPeasants(Peasant[] peasants) {
        World.peasants = peasants;
    }

    public static Knight[] getAllKnights() {
        return knights;
    }

    public static Peasant[] getAllPeasants() {
        return peasants;
    }

    public static Castle[] getAllCastles() {
        return castles;
    }

    public static Entity getEntityAt(int x, int y) {
        if (x > 63 || y > 63 || x < 0 || y < 0) {
            System.out.println("Coordinates out of bounds.");
            return null;
        }

        for (int i = 0; i < knights.length; i++) {
            if (knights[i].getX() == x && knights[i].getY() == y) {
                return knights[i];
            }
        }

        for (int i = 0; i < peasants.length; i++) {
            if (peasants[i].getX() == x && peasants[i].getY() == y) {
                return peasants[i];
            }
        }

        for (int i = 0; i < castles.length; i++) {
            if (castles[i].getX() == x && castles[i].getY() == y) {
                return castles[i];
            }
        }

        System.out.println("No Entity Found.");
        return null;
    }

    public static int getLandOwner(int x, int y) {
        if(x < 0 || x > 63 || y < 0 || y > 63) {
            return -1;
        }
        
        return landOwned[x][y];
    }

    public static BufferedImage mapResized;
    private static double lastScaleFactor = scaleFactor;

    public static void render(Graphics g, CodeRulers r) {

        if (worldMap == null) {
            worldMap = IMAGE.getResizedImage(IMAGE.getBufferedImage("src/resources/images/codeRulersTerrain.png"), (int) (768 * scaleFactor), (int) (768 * scaleFactor));
        }
        if (mapResized == null) {
            BufferedImage mapResized = worldMap;
        }

        if (lastScaleFactor != scaleFactor) {
            mapResized = IMAGE.getResizedImage(worldMap, (int) (768 * scaleFactor), (int) (768 * scaleFactor));
        }

        g.drawImage(mapResized, xOffset, yOffset, null);

        for (int i = 0; i < landOwned[0].length; i++) {
            for (int j = 0; j < landOwned.length; j++) {
                if (landOwned[i][j] == -1) {

                } else {
                    Color c = r.getRulerArray()[landOwned[i][j]].getColor();
                    g.setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), 150));
                    g.fillRect((int) (i * 12 * scaleFactor) + xOffset, (int) (j * 12 * scaleFactor) + yOffset, (int) (12 * scaleFactor), (int) (12 * scaleFactor));
                }

            }
        }

        for (Peasant p : peasants) {
            p.drawEntity(g, scaleFactor, xOffset, yOffset);
        }

        for (Knight k : knights) {
            k.drawEntity(g, scaleFactor, xOffset, yOffset);
        }

        for (Castle c : castles) {
            c.drawEntity(g, scaleFactor, xOffset, yOffset);
        }

        lastScaleFactor = scaleFactor;
    }

    /**
     * Gets the number of tiles owned by the ruler with the given ID.
     *
     * @return The amount of land owned by this ruler
     * @param rulerID the ID of the ruler who's count you need
     */
    public static int getLandCount(int rulerID) {
        //initailize an integer to store the number of tiles
        int count = 0;
        //sort through it, increment integer with every tile that has this ID
        for (int[] i : landOwned) {
            for (int j : i) {
                if (j == rulerID) {
                    count++;
                }
            }
        }
        return count;
    }

    public static double getScaleFactor() {
        return scaleFactor;
    }

    public static void setScaleFactor(double scaleFactor) {
        World.scaleFactor = scaleFactor;
    }

    public static int getxOffset() {
        return xOffset;
    }

    public static void setxOffset(int xOffset) {
        World.xOffset = xOffset;
    }

    public static int getyOffset() {
        return yOffset;
    }

    public static void setyOffset(int yOffset) {
        World.yOffset = yOffset;
    }

}
