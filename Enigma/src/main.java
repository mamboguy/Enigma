import Controller.SubFrames.IRotorController;
import Model.Reflectors.ReflectorFileReader;
import Model.Rotors.RotorFileReader;
import View.BasicGUI.MainGUIFrame;

import javax.swing.*;

/**
 * Date Created Nov 19, 2017
 *
 * @author Michael C
 */
public class main {

    public static void main(String[] args) {
        RotorFileReader.readRotorFile();
        ReflectorFileReader.readReflectorFile();

        IRotorController test = new IRotorController(1);
        IRotorController test2 = new IRotorController(2);
        IRotorController test3 = new IRotorController(3);

        MainGUIFrame mainGUIFrame = new MainGUIFrame();

        mainGUIFrame.add(test.getView());
        mainGUIFrame.add(new JPanel());
        mainGUIFrame.add(test2.getView());
        mainGUIFrame.add(new JPanel());
        mainGUIFrame.add(test3.getView());

        mainGUIFrame.repaint();
    }
}
