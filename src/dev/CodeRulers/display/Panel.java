/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.display;

//Import statements
import dev.CodeRulers.game.CodeRulers;
import dev.CodeRulers.ruler.AbstractRuler;
import dev.CodeRulers.util.IMAGE;
import dev.CodeRulers.world.World;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Arrays;

/**
 * This class will be responsible for handling all drawing to the screen and
 * input from the user.
 *
 * @author Sean Zhang
 */
public class Panel extends javax.swing.JPanel {

    //these two variables are used to determine the previous position of the
    //cursor to calculate the difference between x and y values between the original
    //position of the cursor and the new position of the cursor.
    private int initX, initY;

    //this is the CodeRulers game object. This is needed to access the AI objects passed into the game class.
    CodeRulers r;

    //this is the bufferedImage that will be drawn to the screen as the background for the side panel.
    BufferedImage sidePanelImage;

    //these images are for the icons at the bottom.
    BufferedImage startButton;
    BufferedImage pauseButton;
    BufferedImage restartButton;

    /*  These two fonts are the main two fonts that will be used in our program.
        One will be used as a header (for bigger headings)
        And the other will be used for less important things
     */
    Font subHeader = new Font("Myriad", Font.PLAIN, 11);
    Font header = new Font("Myriad", Font.BOLD, 16);

    //Here are quick dimensions for our panel. Since the panel cannot be resized,
    //these dimensions do not change.
    private int sidePanelWidth = 256;
    private int panelWidth = 1024;
    private int panelHeight = 768;

    //when drawing to the sidePanel, the two integer variables, xCoord and yCoord
    //will determine where a component goes (in the sidePanel)
    private int xCoord;
    private int yCoord;

    //the iconOffset is used later to offset where things are drawn to enable 
    //the scrolling feature in this program.
    private int iconOffset = 50;

    //int turnLimit this is the number of turns that the game has cycled through.
    public int turnsTaken = 0;

    /**
     * The constructor for the Panel Class. Creates new form Panel.
     *
     * @param r This is the codeRulers object that is passed in. In this
     * program, it is passed through to the display and then to this class.
     */
    public Panel(CodeRulers r) {
        //initializes graphical components related to the Netbeasn GUI builder.
        initComponents();

        //stores the reference to the codeRulers object to a CodeRulers variable in this class.
        this.r = r;

        //sets the size of the JPanel to 768x1024
        this.setSize(768, 1024);

        //initializes the side panel image and blurs it using the custom gaussian blur algorithm with a blur radius of 20 pixels.
        //It is then resized to the correct size to fit in the dimensions of the side panel.
        sidePanelImage = IMAGE.getResizedImage(IMAGE.getBlurredImage(IMAGE.getBufferedImage("src/resources/images/sidePanelImage.jpg"), 20), sidePanelWidth, panelHeight);

        //initializes the three images
        startButton = IMAGE.getResizedImage(IMAGE.getBufferedImage("src/resources/images/startButton.png"), 25, 25);
        pauseButton = IMAGE.getResizedImage(IMAGE.getBufferedImage("src/resources/images/pauseButton.png"), 25, 25);
        restartButton = IMAGE.getResizedImage(IMAGE.getBufferedImage("src/resources/images/restartButton.png"), 25, 25);

    }

    @Override
    public void paintComponent(Graphics g) {
        //calls the paintComponent method in the superClass. This essentially
        //clears the screen (I think)
        super.paintComponent(g);

        //render the world layer graphics first. This statement calls the static
        //render method in the World class.
        World.render(g, r);

        //draw the sidePanelImage. The coordinates are determined by the dimensions
        //of this JPanel. The origin of the image is in the top left of the image.
        g.drawImage(sidePanelImage, panelWidth - sidePanelWidth, 0, null);

        //a counter so that we know which ruler in the array we are on. (Enhanced for-loop)
        int count = 0;

        /*  This enhanced for-loop loops through all the rulers in the ruler array
            stored in the codeRulers class. Then, it draws every attribute related
            to the ruler. 
         */
        AbstractRuler[] rulerArr = Arrays.copyOf(r.getRulerArray(),r.getRulerArray().length);
        
        Arrays.sort(rulerArr);
        
        for (AbstractRuler ruler : rulerArr) {
            //this gets the x coordinate of where the player box should go.
            yCoord = count * panelHeight / 12 + 20 + ((count) * 12) + iconOffset;
            xCoord = panelWidth - sidePanelWidth;

            //sets it to the color of the Ruler's Choice. The default is white.
            g.setColor(ruler.getColor());

            //the rounded rectangle box that contains all the details.
            g.fillRoundRect(xCoord + 6, yCoord, sidePanelWidth - 18, panelHeight / 12, 10, 10);

            /*  This is responsible for resizing and drawing the AI profile picture.
                It calculates the relative coordinates based on where xCoord and yCoord
                are and draws it, resized relative to the height of the sidePanel and 
                height of the box containing the image and details.
             */
            g.drawImage(IMAGE.getResizedImage(ruler.getProfileImage(),
                    panelHeight / 14 - 12, panelHeight / 14 - 12),
                    xCoord + 12, yCoord + 6, null);

            //sets the font to the font object, header. This is done in preparation of drawing 
            //the ruler's name to the screen.
            g.setFont(header);

            //sets the color of the text to black, making sure that all names are
            //one consistent color.
            g.setColor(Color.BLACK);

            /*  draws the name of the ruler. This is obtained by calling the getRulerName
                in the classes that extend the AbstractRuler class. 
                The position of the string is calculated relative to the position of xCoord
                and yCoord. If the string is too long in characters, the program will cut off
                the end of the string.
             */
            if (ruler.getRulerName().length() > 15) {
                g.drawString(ruler.getRulerName().substring(0, 15), xCoord + panelHeight / 12 - 3, yCoord + g.getFontMetrics(header).getHeight());
            } else {
                g.drawString(ruler.getRulerName(), xCoord + panelHeight / 12 - 3, yCoord + g.getFontMetrics(header).getHeight());
            }

            //sets the font to the font object, subHeader. This is done in
            //preparation of drawing the details about the ruler to the screen.
            g.setFont(subHeader);

            //This draws the school name of the AI to the screen. The position
            //of this string is determined relative to where xCoord and yCoord
            //are. 
            g.drawString(ruler.getSchoolName(), xCoord + panelHeight / 12 - 2, yCoord + (g.getFontMetrics(header).getHeight()) + g.getFontMetrics(subHeader).getHeight());

            g.setFont(new Font("Myriad",Font.BOLD,11));
            
            //draws ruler ID number
            g.drawString("ID# " + ruler.getRulerID(), panelWidth-g.getFontMetrics(new Font("Myriad",Font.BOLD,11)).stringWidth("ID# " + ruler.getRulerID())-15
                    , yCoord+ g.getFontMetrics(new Font("Myriad",Font.BOLD,11)).getHeight());
            
            
            g.setFont(subHeader);
            //This draws the points of the AI followed by the amount of land the 
            //AI owns, spaced apart by 5 spaces. Every AI starts out with 
            //21 tiles of land by default.
            g.drawString("Points: " + (ruler.getPoints())
                    + "     Land: " + World.getLandCount(ruler.getRulerID()), xCoord + panelHeight / 12 - 2,
                    yCoord + (g.getFontMetrics(header).getHeight()) + 2 * g.getFontMetrics(subHeader).getHeight() + 3);

            //increases the count by one.
            count++;

            
        }
        

        

        //this section is responsible for drawing the utility bar where the
        //user can add more AIs, stop the game, increase the speed of the game,
        //or start the game. 
        //leaderBoard
        if(iconOffset<40) {
            g.setColor(new Color(157, 144, 165, 200));
            g.fillRect(panelHeight, 0, sidePanelWidth, 60);
        }
        
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial",Font.BOLD,30));
        g.drawString("Leaderboard",panelHeight+10,50);
        //utility bar
        g.setColor(new Color(157, 144, 165, 200));
        g.fillRect(panelHeight, panelHeight - 60, sidePanelWidth, 60);
        g.drawImage(startButton, panelHeight + 5, panelHeight - 60 + 5, null);
        g.drawImage(pauseButton, panelHeight + 5 + 5 + 25, panelHeight - 60 + 5, null);

        
        if (r.isGameEnd()) {
            g.setColor(new Color(0,0,0,120));
            g.fillRect(0, 0, 768, 768);
            
            
            g.fillRoundRect(768/2-100, 768/2-25, 200, 50, 10, 10);
            
            g.setFont(new Font("Consolas",Font.BOLD,20));
            g.setColor(Color.WHITE);
            g.drawString("Game Over", 768/2-g.getFontMetrics(new Font("Consolas",Font.BOLD,20)).stringWidth("Game Over")/2, 768/2+6);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setPreferredSize(new java.awt.Dimension(1024, 768));
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseWheelListener(new java.awt.event.MouseWheelListener() {
            public void mouseWheelMoved(java.awt.event.MouseWheelEvent evt) {
                formMouseWheelMoved(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                formMouseReleased(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    /**
     * This method is called whenever the mouseWheel is moved.
     *
     * @param evt The mouse wheel event.
     */
    private void formMouseWheelMoved(java.awt.event.MouseWheelEvent evt) {//GEN-FIRST:event_formMouseWheelMoved
        /*
            This case is for when the user wants the scroll up (essentially move up the list).
            It will restrict the user to a limited amount of scrolling so that
            the user cannot scroll the boxes off the screen. 
            In addition, this method is only called when the mouse cursor is
            directly on the area eligible to scroll.
         */
        if (evt.getX() > panelHeight && evt.getY() < panelHeight - 60
                && (0 * panelHeight / 12 + 20 + ((0) * 12) + iconOffset) <= 10
                && (evt.getWheelRotation()) < 0) {
            //sets the offset to the amount the user has scrolled up. This means
            //that the offset will decrease.
            iconOffset -= ((double) evt.getWheelRotation() * 70);
        } /*
            This case is for when the user wants to scroll down (essentially move
            the list down). It will restrict the user to a limited amount of
            scrolling so that the user cannot scroll the boxes off the screen.
            In addition, this method is only called when the mouse cursor is directly
            on the area eligible to scroll.
         */ else if (evt.getX() > panelHeight && evt.getY() < panelHeight - 60
                && ((r.getRulerArray().length - 1) * panelHeight / 12 + 20
                + ((r.getRulerArray().length - 1) * 12) + iconOffset)
                >= (panelHeight - 150) && evt.getWheelRotation() > 0) {
            //sets the offest to the amount the user has scrolled down.
            //This means that the offest will increase.
            iconOffset -= ((double) evt.getWheelRotation() * 70);
        } /*
            This case is for when the user wants to zoom out of the world.
            It will restrict the user to a limited amount of zooming
            so that the user cannot zoom too far in and too far out.
            In addition, this method is only called when the mouse cursor is 
            directly on the area eligible to scroll.
         */ else if (evt.getX() < panelHeight && World.getScaleFactor() > 0.9
                && evt.getWheelRotation() > 0) {
            //sets the scale factor based on how much the wheel has moved.
            World.setScaleFactor(World.getScaleFactor() - ((double) evt.getWheelRotation() / 20));
        } /*
            This case is for when the user wants to zoom into the world.
            It will restrict the user to a limited amount of zooming 
            so that the user cannot zoom too far in and too far out.
            In addition, this method is only called when the mouse cursor is
            directly on the area eligible to scroll.
         */ else if (evt.getX() < panelHeight && World.getScaleFactor() < 1.85
                && evt.getWheelRotation() < 0) {
            //sets the scale factor based on how much the wheel has moved.
            World.setScaleFactor(World.getScaleFactor() - ((double) evt.getWheelRotation() / 20));
        }

        //refreshes the screen to display the changes made to the GUI.
        repaint();
    }//GEN-LAST:event_formMouseWheelMoved

    /**
     * This method is called when the mouse is dragged across the screen.
     *
     * @param evt the mouse event.
     */
    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        //sets the offest relative to the position of the old cursor position
        //and new cursor position.
        if (evt.getX() < panelHeight) {
            World.setxOffset(World.getxOffset() + evt.getX() - initX);
            World.setyOffset(World.getyOffset() + evt.getY() - initY);

            //sets the original coordinates to the current coordinates.
            initX = evt.getX();
            initY = evt.getY();
        }
        //refreshes the screen to show the changes made to the world.
        repaint();
    }//GEN-LAST:event_formMouseDragged

    /**
     * Called when a mouse button is pressed.
     *
     * @param evt the mouse event.
     */
    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        //gets the intial x and y coordinates of the mouse.
        if (evt.getX() < panelHeight) {
            initX = evt.getX();
            initY = evt.getY();
        } else {
            if (evt.getX() > panelHeight + 5 && evt.getX() < panelHeight + 25 + 5 && evt.getY() > panelHeight - 60 + 5 && evt.getY() < panelHeight - 60 + 25 + 5) {
                r.getTimer().start();
            } else if (evt.getX() > panelHeight + 5 + 25 + 5 && evt.getX() < panelHeight + 25 + 5 + 5 + 25 && evt.getY() > panelHeight - 60 + 5 && evt.getY() < panelHeight - 60 + 25 + 5) {
                r.getTimer().stop();
            }

        }

    }//GEN-LAST:event_formMousePressed

    /**
     * Called when the mouse button is released.
     *
     * @param evt the mouse event.
     */
    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        /*  If the position of the map deviates from the original position of the 
            map, the offset will be reset to a closer offset. This essentially 
            restricts the user from panning too far out in all directions.
         */

        //this is for preventing the user from panning too far right.
        if (World.getxOffset() > 70) {
            World.setxOffset(71);
        }

        //this is for preventing the user from panning too far down.
        if (World.getyOffset() > 70) {
            World.setyOffset(71);
        }

        //this is for preventing the user from panning too far left.
        if (World.getxOffset() < (768 - World.mapResized.getWidth() - 70)) {
            World.setxOffset((768 - World.mapResized.getWidth() - 70) - 4);
        }

        //this is for preventing the user from panning too far up.
        if (World.getyOffset() < (768 - World.mapResized.getHeight() - 70)) {
            World.setyOffset((768 - World.mapResized.getHeight() - 70) - 4);
        }

        //refreshes the screen so that changes become effective.
        repaint();
    }//GEN-LAST:event_formMouseReleased

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
