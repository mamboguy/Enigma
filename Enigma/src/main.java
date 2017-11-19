
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
        System.out.print("Input message for encoding: ");
        String temp = s.nextLine();
        
        temp = temp.toUpperCase();
        temp = temp.replaceAll(" ", "");
        
        e.inputMessage(temp);
    }

}
