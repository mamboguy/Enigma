package Controller.File;

import java.io.File;

/**
 * Date Created Nov 26, 2017
 *
 * @author Michael C
 */
public class EnigmaFileSaver {

    public static void saveKeyFile(File path, String[] keySettings) {

        for (int i = 0; i < keySettings.length; i++) {
            System.out.println("Settings[i] = " + keySettings[i]);
        }
    }

}
