package Controller.File;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Date Created Nov 26, 2017
 *
 * @author Michael C
 */
public class EnigmaFileSaver {

    public static void saveKeyFile(File path, String[] keySettings) {
        
        File newFile = new File(path.toString().replaceAll(".ekf", "") + ".ekf");

        String rotors = "";
        String labels = "";
        String keys = "";

        int rotorCount = (keySettings.length - 2) / 3;

        for (int i = 0; i < rotorCount; i++) {
            rotors = keySettings[i] + " " + rotors;
        }

        rotors = rotors.trim();

        for (int i = rotorCount; i < rotorCount * 2; i++) {
            labels = keySettings[i] + labels;
        }

        for (int i = rotorCount * 2; i < rotorCount * 3; i++) {
            keys = keySettings[i] + keys;
        }
        
        System.out.println("rotors = " + rotors);
        System.out.println("reflector = " + keySettings[keySettings.length - 2]);
        System.out.println("labels = " + labels);
        System.out.println("keys = " + keys);
        System.out.println("plugboard = " + keySettings[keySettings.length - 1]);

        try {
            newFile.createNewFile();

            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(newFile));

            fileWriter.write("Rotors Used: ");
            fileWriter.write(rotors);
            fileWriter.newLine();

            fileWriter.write("Reflector Used: ");
            fileWriter.write(keySettings[keySettings.length - 2]);
            fileWriter.newLine();

            fileWriter.write("Labels Used: ");
            fileWriter.write(labels);
            fileWriter.newLine();

            fileWriter.write("Keys Used: ");
            fileWriter.write(keys);
            fileWriter.newLine();

            fileWriter.write("Plugboard Settings Used: ");
            fileWriter.write(keySettings[keySettings.length - 1]);
            fileWriter.newLine();
            
            fileWriter.close();

        } catch (Exception e) {
            System.out.println("File creation failed");
        }
    }

}
