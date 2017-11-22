
import Controller.BasicGUIController;
import Model.Enigma.Enigma;
import java.util.Scanner;

/**
 * Date Created Nov 19, 2017
 *
 * @author Michael C
 */
public class main {

    public static void main(String[] args) {
        Enigma e = new Enigma();

        BasicGUIController a = new BasicGUIController();

//	textInput(e);
    }

    /**
     * textInput prompts the user in the console for the enigma settings and
     * message.
     */
    public static void textInput(Enigma e) {

        Scanner s = new Scanner(System.in);
        String temp;

        System.out.print("Input what rotors to use \"(ex:I II III)\": ");
        temp = s.nextLine();
        e.changeRotors(temp);

        System.out.print("Input what reflector to use \"(ex:UKW-B)\": ");
        temp = s.nextLine();
        e.changeReflector(temp);

        System.out.print("What pairs are steckered? ");
        temp = s.nextLine();
        e.steckerPairs(temp);

        System.out.print("Input label offsets for rotors \"(ex:BOL)\": ");
        temp = s.nextLine();
        e.changeLabels(temp);

        System.out.print("Input rotor offset \"(ex:CHG)\": ");
        temp = s.nextLine();
        e.changeRotorStart(temp);

        System.out.print("Input message for encoding: ");
        temp = s.nextLine();
        e.inputMessage(temp);
    }
}
