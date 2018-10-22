package Model.Enigma;

import Model.Reflectors.HistoricalReflector;
import Model.Reflectors.IReflector;
import Model.Rotors.HistoricalRotor;
import Model.Rotors.IRotor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RotorAssemblyTest {

    private RotorAssembly test;
    private IRotor rotor1 = new HistoricalRotor("I", "EKMFLGDQVZNTOWYHXUSPAIBRCJ", "Q");
    private IRotor rotor2 = new HistoricalRotor("VI", "JPGVOUMFYQBENHZRDKASXLICTW", "MZ");
    private IRotor nullRotor = null;
    private IReflector reflector1 = new HistoricalReflector("UKW-A", "EJMZALYXVBWFCRQUONTSPIKHGD", "");
    private IReflector reflector2 = new HistoricalReflector("UKW-B-Thin", "ENKQAUYWJICOPBLMDXZVFTHRGS", "");
    private IReflector nullReflector = null;

    @BeforeEach
    void setUp() {
        test = new RotorAssembly(3);
    }

    @Test
    void changeReflector() {
        //Change rotor already in machine

        //Change rotor that is available
        test.changeReflector(reflector1);
        assertEquals(test.getReflector(), reflector1);

        test.changeReflector(reflector2);
        assertEquals(test.getReflector(), reflector2);

        //Change reflector that is null
        assertFalse(test.changeReflector(nullReflector));
    }

    @Test
    void getReflector() {
        //Test initialization
        assertEquals(test.getReflector(), reflector1);
    }

    @Test
    void addNewSlot() {
    }

    @Test
    void addNewSlot1() {
    }

    @Test
    void removeSlot() {
    }

    @Test
    void getRotor() {
    }

    @Test
    void changeRotor() {
        //Change rotor already in machine

        //Change rotor that is available

        //Change rotor that is invalid
    }

    @Test
    void stepAssembly() {
    }

    @Test
    void processCharacter() {
    }

    @Test
    void keyComponent() {
    }
}