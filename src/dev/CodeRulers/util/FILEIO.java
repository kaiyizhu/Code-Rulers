/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dev.CodeRulers.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Static Class for Inputting and Outputting files in Java (Including encryption
 * features).
 *
 * @author Sean and Only Sean
 */
public class FILEIO {

    /**
     * Prints string to a file specified. Can append or overwrite depending on
     * the append boolean
     *
     * @param fName the fileName that exists inside the Res (resources) folder
     * in the project.
     * @param s the string that is being written
     * @param append whether or not the user wants to append the file or
     * overwrite the data.
     */
    public static void printF(String fName, String s, Boolean append) {
        //creates a new file
        File f = new File("src/Res/" + fName);
        //creates a printWriter varaible
        PrintWriter p;

        //catches exceptions related to IO exception
        try {
            //creates a new printwriter that prints to file f and can append depending
            //on the boolean argument given.
            p = new PrintWriter(new FileWriter(f, append));
            //prints the string to the file
            p.println(s);
            //closes the printWriter.
            p.close();
        } catch (Exception e) {
            Logger.getLogger(FILEIO.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    /**
     * Finds where the given string resides in the file and returns that string.
     *
     * @param fName the file name of the thing
     * @param s the name of the string that you are trying to find
     * @param detail the specific detail: 0) First Name 1) Last Name 2) Username.. etc
     * @param delim the delimiter to split the line
     * @return
     */
    public static String[] findAndReturn(String fName, String s, int detail, String delim) {
        //creates a new instance of file with the given path of the file.
        File f = new File("src/Res/" + fName);
        //creates a scanner variable
        Scanner input;
        int count = -1;

        //catches exceptions related to FileNotFoundException
        try {
            //creates a new instance of Scanner in the input variable
            input = new Scanner(f);

            //adds every line of the file into the output string.
            while (input.hasNext()) {
                count++;
                String[] s2 = input.nextLine().split(delim);
                if (s.equals(s2[detail])) {
                    
                    //closes the scanner
                    input.close();
                    return s2;
                }
            }

        } catch (FileNotFoundException ex) {
        }

        return null;
    }

    /**
     * Creates a new file in the Res (resources) folder.
     *
     * @param fName the filename
     */
    public static void printF(String fName) {
        //creates a new file in the Res (resources) folder.
        File f = new File("src/Res/" + fName);
    }

    /**
     * Encrypts the String given in the argument by ciphering it with a key and
     * converting it to base 64.
     *
     * @param s the string that is to be encrypted
     * @return the encrypted string
     * @throws Exception
     */
    public static String encrypt(String s) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        //give helper function the password
        md.update(s.getBytes());

        //perform encryption
        return new String(md.digest());

    }

}
