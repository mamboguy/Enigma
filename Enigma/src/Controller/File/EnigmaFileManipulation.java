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

    public static String[] openKeyFile(File path) {

        String rotors = "";
        String reflector = "";
        String labels = "";
        String keys = "";
        String plugBoardSetting = "";

        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(path));

            rotors = fileReader.readLine();
            rotors = rotors.replaceAll("Rotors Used: ", "");

            reflector = fileReader.readLine();
            reflector = reflector.replaceAll("Reflector Used: ", "");

            labels = fileReader.readLine();
            labels = labels.replaceAll("Labels Used: ", "");

            keys = fileReader.readLine();
            keys = keys.replaceAll("Keys Used: ", "");

            plugBoardSetting = fileReader.readLine();
            plugBoardSetting = plugBoardSetting.replaceAll("Plugboard Settings Used: ", "");

            fileReader.close();

        } catch (Exception e) {
            System.out.println("File creation failed");
        }

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
