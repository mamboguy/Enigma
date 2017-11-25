package Model.Rotors;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Mamboguy
 */
public class RotorFileReader {

    private static final String ROTOR_SOURCE_PATH = "src/Model/Rotors/historical_rotors.txt";

    public static ArrayList<Rotor> readRotorFile(String path) {
        
        //TODO - Remove me when implement reading other files
        path = ROTOR_SOURCE_PATH;

        ArrayList<Rotor> rotorList = new ArrayList();

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
                    String notch = lineReader.next();
                    int usage = lineReader.nextInt();

                    //Add the new rotor to the list
                    rotorList.add(new Rotor(name, sequence, notch, usage));
                }
            }
        } catch (Exception e) {
            System.out.println("error = " + e);
        }

        return rotorList;
    }

}
