/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Reflectors;

import Model.Rotors.Rotor;

/**
 * Date Created Nov 18, 2017
 *
 * @author Michael C
 */
public class Reflector {

    private final String name;
    private final String wiringSequence;
    private boolean[] usage = new boolean[3]; //Kriegsmarine, Luftwaffe, Wehrmacht

    public Reflector(String name, String wiringSequence, int usage) {
        this.name = name;
        this.wiringSequence = wiringSequence;

        //Initialize usage to all false
        for (int i = 0; i < this.usage.length; i++) {
            this.usage[i] = false;
        }

        //Set usage flags
        if (usage >= 100) {
            this.usage[Rotor.KRIEGSMARINE] = true;
            usage -= 100;
        }

        if (usage >= 10) {
            this.usage[Rotor.LUFTWAFFE] = true;
            usage -= 10;
        }

        if (usage == 1) {
            this.usage[Rotor.WEHRMACHT] = true;
        }
    }
    
    public int getReflection(int input){
        return (wiringSequence.charAt(input) - 65);
    }

    public String getReflectorName() {
        return name;
    }

    @Override
    public String toString() {
        return "Reflector{" + "name=" + name + ", wiringSequence= " + wiringSequence +
               ", Kriegsmarine " + usage[0] + ", Luftwaffe = " + usage[1] + 
               ", Wehrmacht = " + usage[2] + "}";
    }
}
