package Model.Storages;

import Model.Reflectors.HistoricalReflector;
import Model.Reflectors.ReflectorFileReader;
import Model.Rotors.HistoricalRotor;
import Model.Rotors.RotorFileReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ComponentStorageTest {

    private IEnigmaComponent reflector1 = new HistoricalReflector("TestReflector1", "aoeu", "a");
    private IEnigmaComponent rotor1 = new HistoricalRotor("TestReflector1", "aoeu", "a");

    private ComponentFactory storage1 = ComponentFactory.getInstance();

    @BeforeAll
    void setup(){
        ReflectorFileReader.readReflectorFile();
        RotorFileReader.readRotorFile();
    }

    @Test
    void addComponent() {
        ComponentFactory.getInstance().addComponent(reflector1);
        ComponentFactory.getInstance().addComponent(rotor1);

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
    void compareStrings() {
        assertTrue(storage1.testCompareStrings("III", "I"));
        assertTrue(storage1.testCompareStrings("III", "II"));
        assertTrue(storage1.testCompareStrings("II", "I"));
        assertFalse(storage1.testCompareStrings("I", "IV"));
        assertFalse(storage1.testCompareStrings("I", "I"));
        assertFalse(storage1.testCompareStrings("I", "III"));
        assertFalse(storage1.testCompareStrings("II", "III"));
        assertFalse(storage1.testCompareStrings("I", "II"));
    }
}