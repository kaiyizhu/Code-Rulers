/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.util;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author Sean Zhang
 */
public class IMAGE {
    /**
     * Accesses the image specified in the resources/images folder.
     * @param name the name of the image
     * @return The specified image from the folder
     */
    public static BufferedImage getBufferedImage(String name) {
        //accesses the image specified in the resources/images folder
        try {
            return (ImageIO.read(new File( name)));
        } catch (IOException ex) {
            return null;
        }
    }
    
    /**
     * Accesses the image specified from the web.
     * @param name the name of the image
     * @return The Image from the web
     */
    public static BufferedImage getBufferedImageURL(String name) {
        //accesses the image specified in the resources/images folder
        try {
            return (ImageIO.read(new URL(name)));
        } catch (IOException ex) {
            return null;
        }
    }
    
    /**
     * This returns a bufferedImage that is blurred and scaled to size
     * @param bi the image that needs to be blurred
     * @param radius the radius of the blur (how much you want it blurred).
     * @return the retouched buffered image.
     */
    public static BufferedImage getBlurredImage(BufferedImage bi, int radius) {

        //this is for calculating the blur radius
        int size = radius * 2 + 1;
        float weight = 1.0f / (size * size);
        float[] data = new float[size * size];
        for (int i = 0; i < data.length; i++) {
            data[i] = weight;
        }

        //creates an image kernel
        Kernel kernel = new Kernel(size, size, data);
        
        //blurs the image, leaving the edges of the blur non-blurred. This will be cropped out using
        //getSubImage.
        ConvolveOp op = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
        return op.filter(bi, null).getSubimage(radius, radius, bi.getWidth()-(2*radius), bi.getHeight()-(2*radius));
    }
    
    /**
     * This returns a buffered image that has been scaled to the appropriate size for this jPanel.
     * @param bi the bufferedImage to be scaled
     * @param width the width of the new image
     * @param height the height of the new image
     * @return the resized bufferedImage
     */
    public static BufferedImage getResizedImage(BufferedImage bi, int width, int height) {

        //creates the new image which is scaled to the size appropriate for blurring and cropping and fitting on the jPanel.
        BufferedImage scaledImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        
        //calculates how much is needed to scale the image.
        float scale = (float)Math.max(scaledImage.getWidth()/(float)bi.getWidth(), scaledImage.getHeight()/(float)bi.getHeight());
        
        //creates an instance of AffineTransform. This is then used in another object to scale the image by the calculated scale factor.
        AffineTransform at = new AffineTransform();
        at.scale(scale, scale);
        
        //this will be used to scale and smooth out the image.
        AffineTransformOp scaleOp= new AffineTransformOp(at, AffineTransformOp.TYPE_BICUBIC);
        
        //creates a new image that is scaled then smoothed out using antialiasing (bicubic)
        scaledImage = scaleOp.filter(bi, scaledImage);
        
        return scaledImage;
    }
}
