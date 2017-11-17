/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enigma;

/**
 *
 * @author Michael C
 */
import java.util.Scanner;

public class Enigma {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        //TODO - Implement Logger
        Rotor[] s = RotorFileReader.readRotorFile();

    }

//        Rotor r = new Rotor("Test", "BCDEFGHIJKLMNOPQRSTUVWXYZA", 'A', 'T');
//        
//        System.out.println("key position = " + r.getKeyPosition());
//        
//        for (int i = 0; i < 26; i++) {
//            System.out.println("Left pinout for rotor at " + i + " = " + r.getLeftChar(i));
//            System.out.println("");
//        }
//        
//        for (int i = 0; i < 10; i++) {
//            System.out.println("---------");
//        }
//        
//        for (int i = 0; i < 26; i++) {
//            System.out.println("Right pinout for rotor at " + i + " = " + r.getRightChar(i));
//            System.out.println("");
//        }
//        
//        for (int i = 0; i < 10; i++) {
//            System.out.println("----------");
//        }
//        
//        for (int i = 0; i < 26; i++) {
//            System.out.println("Rotor stepping " + i);
//            System.out.println("Caused step? = " + r.stepRotor());
//        }
//        
//        
//        Rotor s = new Rotor("Test", "OGNCLTYIBDUMHAKESFJRXWZVPQ", 'C', 'A');
//        
//        System.out.println("key position = " + s.getKeyPosition());
//        
//        for (int i = 0; i < 26; i++) {
//            System.out.println("Left pinout for rotor at " + i + " = " + s.getLeftChar(i));
//            System.out.println("");
//        }
//        
//        for (int i = 0; i < 10; i++) {
//            System.out.println("---------");
//        }
//        
//        for (int i = 0; i < 26; i++) {
//            System.out.println("Right pinout for rotor at " + i + " = " + s.getRightChar(i));
//            System.out.println("");
//        }
//        
//        for (int i = 0; i < 10; i++) {
//            System.out.println("----------");
//        }
//        
//        for (int i = 0; i < 26; i++) {
//            System.out.println("Rotor stepping " + i);
//            System.out.println("Caused step? = " + s.stepRotor());
//        }
}
