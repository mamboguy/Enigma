
import Model.Enigma.Enigma;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Date Created Nov 19, 2017
 *
 * @author Michael C
 */
public class main {

    public static void main(String[] args) {
        Enigma e = new Enigma();
        Scanner s = new Scanner(System.in);
        String temp;
        
        System.out.print("Input label offsets for rotors \"ex:BOL)\" ");
        temp = s.nextLine();
        e.changeLabels(temp);
        
        System.out.print("Input message for encoding: ");
        temp = s.nextLine();        
        e.inputMessage(temp);
        
    }

}
