package dev.CodeRulers.display;

//import statements:
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * @author Sean Zhang
 */

public class Display {
    //creates the JFrame window for our game
    private JFrame frame;
    //creates the panel for us to draw our graphics on our game
    private JPanel panel;
    
    //this is the logo for the game
    private ImageIcon logo;
    
    //Jframe variables:
    private String title; //title of the jframe
    private int width,height; //width and height of jframe
    
    /**
     * The Constructor for the Display Class. This will contain all of the
     * variables responsible for the window of the game.
     * @param title the title of the program/JFrame. This title will be shown as the name of the program when the program is run.
     * @param width this is the width of the program window.
     * @param height this is the height of the program window.
     * @param imageName this is the directory where the logo image is stored.
     */
    public Display(String title, int width, int height,String imageName){
        //sets the JFrame varaibles
        this.title=title;
        this.width=width;
        this.height=height;
        //this sets logo ImageIcon to the image found in the path below
        logo = new ImageIcon(getClass().getResource("/res/logo/"+imageName));
        //calls method createDisplay
        createDisplay();
    }
    
    /**
     * This method is responsible for creating the JFrame and configuring all
     * of the desired window properties.
     */
    private void createDisplay() {
        //initializes the jframe with string title
        frame = new JFrame(title);
        //sets size of parameter with the width and height variables
        frame.setSize(width,height);
        //exits the program when it is closed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //prevents user from resizing it
        frame.setResizable(false);
        //sets the location to the center of the screen
        frame.setLocationRelativeTo(null);
        //sets icon
        frame.setIconImage(logo.getImage());
        //when initializing a jframe, it is default to invisible
        //so we have to set it to visible
        frame.setVisible(true);
        
        //creates new JPanel
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(width,height));
        panel.setMaximumSize(new Dimension(width,height));
        panel.setMinimumSize(new Dimension(width,height));
        panel.setFocusable(false);
        frame.add(panel);
        frame.pack();
    }
    
    /**
     * Gives the user the panel of the display
     * @return the JPanel in this instance of the display object.
     */
    public JPanel getPanel() {
        return panel;
    }
    
    /**
     * Gives the user the frame/window of the display
     * @return the JFrame in this instance of the display object.
     */
    public JFrame getFrame() {
        return frame;
    }
    
   
}