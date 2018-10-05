package Model.Rotors;

import Model.Enigma.Storages.ComponentStorage;
import java.io.File;
import java.util.Scanner;

/**
 *
 * @author Mamboguy
 */
public class RotorFileReader {

    private static final String ROTOR_SOURCE_PATH = "Enigma/src/Model/Rotors/historical_rotors.txt";

    // TODO: 10/2/2018 Write IRotor file
    // TODO: 10/2/2018 Validate IRotor file

    public static ComponentStorage readRotorFile(String path) {
        
        //TODO - Remove me when implement reading other files
        path = ROTOR_SOURCE_PATH;

        ComponentStorage rotorList = new ComponentStorage();

        try {
            //Create a scanner from the rotor file
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

                    //Add the new rotor to the list
                    //rotorList.add(new HistoricalRotorDeprecated(name, sequence, notch, usage));
                    rotorList.addComponent(new HistoricalRotor(name, sequence, notch));
                }
            }
        } catch (Exception e) {
            System.out.println("error = " + e);
        }

        return rotorList;
    }

}
