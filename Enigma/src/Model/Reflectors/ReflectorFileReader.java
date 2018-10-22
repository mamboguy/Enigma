package Model.Reflectors;

import Model.Storages.ComponentFactory;
import java.io.File;
import java.util.Scanner;

/**
 * Date Created Nov 18, 2017
 *
 * @author Michael C
 */
public class ReflectorFileReader {

    private static final String REFLECTOR_SOURCE_PATH = "Enigma/src/Model/Reflectors/historical_reflectors.txt";

    public static void readReflectorFile(){
        readReflectorFile(REFLECTOR_SOURCE_PATH);
    }

    public static void readReflectorFile(String path){
        //TODO - Remove me when implement reading other files
        path = REFLECTOR_SOURCE_PATH;

        try {
            //Create a new scanner from the reflector file
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
                    ComponentFactory.getInstance().addComponent(new HistoricalReflector(name, sequence, ""));
                }
            }
        } catch (Exception e) {
            System.out.println("error = " + e);
        }
    }
}