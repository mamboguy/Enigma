package Model.Reflectors;

import Model.Enigma.Enigma;

/**
 * Date Created Nov 18, 2017
 *
 * @author Michael C
 */
public class Reflector {

    //Name of the rotor
    private final String name;

    //The reflector's wiring sequence
    private final String wiringSequence;

    //Kriegsmarine, Luftwaffe, Wehrmacht
    private boolean[] usage = new boolean[3];

    /**
     * Constructor for reflectors
     * @param name - The reflector's name
     * @param wiringSequence - The reflector's wiring sequence
     * @param usage - what services the reflector was used by
     */
    public Reflector(String name, String wiringSequence, int usage) {
        
        //Set the name and wiring sequence
        this.name = name;
        this.wiringSequence = wiringSequence;

        //Get the usage flags
        this.usage = Enigma.getUsageStats(usage);
    }

    /**
     * Returns the reflected output pin for a given input pin
     * @param input - the input pin
     * @return - the reflected output pin
     */
    public int getReflection(int input) {
        return (wiringSequence.charAt(input) - 65);
    }

    /**
     * Gets the reflector's name
     * @return - the reflector name
     */
    public String getReflectorName() {
        return name;
    }

    @Override
    public String toString() {
        return "Reflector{" + "name=" + name + ", wiringSequence= " + wiringSequence
                + ", Kriegsmarine " + usage[0] + ", Luftwaffe = " + usage[1]
                + ", Wehrmacht = " + usage[2] + "}";
    }
}
