package Controller.File;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Date Created Nov 26, 2017
 *
 * @author Michael C
 */
public class EnigmaFileManipulation {
    
    private static final String ROTOR_HEADER = "Rotors Used: ";
    private static final String REFELECTOR_HEADER = "Reflector Used: ";
    private static final String LABEL_HEADER = "Labels Used: ";
    private static final String KEY_HEADER = "Keys Used: ";
    private static final String PLUGBOARD_HEADER = "Plugboard Settings Used: ";

    public static String[] openKeyFile(File path) {

        String rotors = "";
        String reflector = "";
        String labels = "";
        String keys = "";
        String plugBoardSetting = "";

        try {
            //Create a file reader
            BufferedReader fileReader = new BufferedReader(new FileReader(path));

            //Read the file in order:  Rotors, Reflectors, Labels, Keys and Plugboard
            rotors = fileReader.readLine();
            rotors = rotors.replaceAll(ROTOR_HEADER, "");

            reflector = fileReader.readLine();
            reflector = reflector.replaceAll(REFELECTOR_HEADER, "");

            labels = fileReader.readLine();
            labels = labels.replaceAll(LABEL_HEADER, "");

            keys = fileReader.readLine();
            keys = keys.replaceAll(KEY_HEADER, "");

            plugBoardSetting = fileReader.readLine();
            plugBoardSetting = plugBoardSetting.replaceAll(PLUGBOARD_HEADER, "");

            //Close the file
            fileReader.close();

        } catch (Exception e) {
            System.out.println("File creation failed");
        }

        //todo - create new file for managing keying info
        int count = rotors.split(" ").length;

        String[] rotorSplit = rotors.split(" ");

        String[] settings = new String[count * 3 + 2];

        int j = 0;
        for (int i = 0; i < count; i++) {
            settings[j] = rotorSplit[i];
            j++;
        }

        for (int i = count - 1; i >= 0; i--) {
            settings[j] = "" + labels.charAt(i);
            j++;
        }

        for (int i = count - 1; i >= 0; i--) {
            settings[j] = "" + keys.charAt(i);
            j++;
        }

        settings[j] = reflector;
        j++;
        settings[j] = plugBoardSetting;

        return settings;
    }

    public static void saveKeyFile(File path, String[] keySettings) {

        File newFile = new File(path.toString().replaceAll(".ekf", "") + ".ekf");

        //todo - create new file for managing keying info
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

        try {
            //Create the new file
            newFile.createNewFile();

            //Create a fileWriter for writing into the file
            BufferedWriter fileWriter = new BufferedWriter(new FileWriter(newFile));

            //Write the file in the following order: Rotor, Reflector, Label, Key and Plugboard
            fileWriter.write(ROTOR_HEADER + rotors);
            fileWriter.newLine();

            fileWriter.write(REFELECTOR_HEADER + keySettings[keySettings.length - 2]);
            fileWriter.newLine();
            
            fileWriter.write(LABEL_HEADER + labels);
            fileWriter.newLine();

            fileWriter.write(KEY_HEADER + keys);
            fileWriter.newLine();

            fileWriter.write(PLUGBOARD_HEADER + keySettings[keySettings.length - 1]);
            fileWriter.newLine();

            //Close the file
            fileWriter.close();

        } catch (Exception e) {
            System.out.println("File creation failed");
        }
    }

}
