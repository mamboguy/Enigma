package Model.Enigma.Storages;

import Model.Reflectors.HistoricalReflector;
import Model.Reflectors.ReflectorFileReader;
import Model.Rotors.HistoricalRotor;
import Model.Rotors.RotorFileReader;
import org.junit.jupiter.api.Test;

import java.sql.Ref;

import static org.junit.jupiter.api.Assertions.*;

class ComponentStorageTest {

    private ComponentStorage storage1 = RotorFileReader.readRotorFile();

    private IEnigmaComponent reflector1 = new HistoricalReflector("TestReflector1", "aoeu", "a");
    private IEnigmaComponent rotor1 = new HistoricalRotor("TestReflector1", "aoeu", "a");

    @Test
    void addComponent() {
        storage1.addComponent(reflector1);
        storage1.addComponent(rotor1);

        assertTrue(storage1.hasComponent(rotor1.getName()));
        assertTrue(storage1.hasComponent(reflector1.getName()));
    }

    @Test
    void getComponent() {

    }

    @Test
    void hasComponent() {
        assertTrue(storage1.hasComponent("I"));
        assertTrue(storage1.hasComponent("II"));
        assertTrue(storage1.hasComponent("III"));
        assertTrue(storage1.hasComponent("IV"));
        assertTrue(storage1.hasComponent("V"));
        assertTrue(storage1.hasComponent("VI"));
        assertTrue(storage1.hasComponent("VII"));
        assertTrue(storage1.hasComponent("VIII"));
    }

    @Test
    void getAllNames() {
        String[] temp = new String[]{"I", "II", "III", "IV", "V", "VI", "VII", "VIII"};

        storage1.print();

        assertEquals(storage1.getAllNames(), temp);
    }
}