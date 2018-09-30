package Model.Reflectors;

import Model.Rotors.HistoricalRotor;

/**
 * Date Created Nov 18, 2017
 *
 * @author Michael C
 */
public class HistoricalReflector extends HistoricalRotor implements IReflector {

    /**
     * Constructor for reflectors
     * @param name - The reflector's name
     * @param wiringSequence - The reflector's wiring sequence
     */
    public HistoricalReflector(String name, String wiringSequence, String notchLocations) {
        super(name, wiringSequence, "");
    }

    /**
     * Returns the reflected output pin for a given input pin
     * @param input - the input pin
     * @return - the reflected output pin
     */
    @Override
    public int getReflection(int input) {
        return super.getRightOutput(input);
    }

    /**
     * Gets the reflector's name
     * @return - the reflector name
     */
    @Override
    public String getReflectorName() {
        return super.getRotorName();
    }
}
