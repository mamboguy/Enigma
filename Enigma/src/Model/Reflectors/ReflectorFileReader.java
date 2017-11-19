/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Reflectors;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Date Created Nov 18, 2017
 *
 * @author Michael C
 */
public class ReflectorFileReader {

    private static final String REFLECTOR_SOURCE_PATH = "src/Model/Reflectors/historical_reflectors.txt";
    
    public static ArrayList<Reflector> readReflectorFile(String path){
    //TODO - Remove me when implement reading other files
        path = REFLECTOR_SOURCE_PATH;

        ArrayList<Reflector> reflectorList = new ArrayList();

        try {
            Scanner s = new Scanner(new File(path));

            String line;

            //Check to see if next line exists
            while (s.hasNextLine()) {

                //Pull the next line
                line = s.nextLine();

                if (line.startsWith("#")) {
                    //Ignore comments
                } else {
                    //Create a scanner to read the line
                    Scanner lineReader = new Scanner(line);
                    
                    //Pull each part of the read line
                    String name = lineReader.next();
                    String sequence = lineReader.next();
                    int usage = lineReader.nextInt();

                    //Add the new rotor to the list
                    reflectorList.add(new Reflector(name, sequence, usage));
                }
            }
        } catch (Exception e) {
            System.out.println("error = " + e);
        }

        return reflectorList;
    }

}