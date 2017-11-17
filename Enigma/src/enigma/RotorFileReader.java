/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enigma;

import java.io.File;
import java.util.Scanner;

/**
 *
 * @author Mamboguy
 */
public class RotorFileReader {

    private static final String ROTOR_SOURCE_PATH = "src/enigma/historical_rotors.txt";

    public static Rotor[] readRotorFile() {

        try {
            Scanner s = new Scanner(new File(ROTOR_SOURCE_PATH));

            System.out.println("Test");

            String temp;

            while (s.hasNextLine()) {

                temp = s.nextLine();

                if (temp.contains("#")) {
                    //Ignore comments

                } else {
                    //TODO - Implement file parser
                }
                
            }

        } catch (Exception e) {
            System.out.println("error");
        }

        return null;
    }

}
