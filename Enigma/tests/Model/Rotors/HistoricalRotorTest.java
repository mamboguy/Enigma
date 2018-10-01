package Model.Rotors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HistoricalRotorTest {

    private HistoricalRotor rotor1 = new HistoricalRotor("I", "EKMFLGDQVZNTOWYHXUSPAIBRCJ", "Q");
    private HistoricalRotor rotor2 = new HistoricalRotor("VI", "JPGVOUMFYQBENHZRDKASXLICTW", "MZ");

    @BeforeEach
    void setUp() {

    }

    @Test
    void getLeftOutput() {
        //Initial - Top
        assertEquals(rotor1.getLeftOutput(0), 4);
        assertEquals(rotor2.getLeftOutput(0), 9);

        //Initial - Pin 14
        assertEquals(rotor1.getLeftOutput(14), 24);
        assertEquals(rotor2.getLeftOutput(14), 25);

        //Step Both Rotors
        rotor1.stepRotorForward();
        rotor2.stepRotorForward();

        //Stepped Once - Top
        assertEquals(rotor1.getLeftOutput(0), 9);
        assertEquals(rotor2.getLeftOutput(0), -12);

        //Stepped Once - Pin 14
        assertEquals(rotor1.getLeftOutput(14), 6);
        assertEquals(rotor2.getLeftOutput(14), 16);

        rotor1.stepRotorBackward();
        rotor2.stepRotorBackward();

        rotor1.setLabelPosition('B');
        rotor2.setLabelPosition('B');

        rotor1.setKeyPosition('A');
        rotor2.setKeyPosition('A');

        //Initial - Top
        assertEquals(rotor1.getLeftOutput(0), 10);
        assertEquals(rotor2.getLeftOutput(0), -3);

        //Initial - Pin 14
        assertEquals(rotor1.getLeftOutput(14), 23);
        assertEquals(rotor2.getLeftOutput(14), 8);

    }

    @Test
    void getRightOutput() {

        // <editor-fold defaultstate="collapsed" desc="Test Initial Setup">

        //Initial - Top
        assertEquals(rotor1.getRightOutput(0), -6);
        assertEquals(rotor2.getRightOutput(0), -8);

        //Initial - Pin 14
        assertEquals(rotor1.getRightOutput(14), 12);
        assertEquals(rotor2.getRightOutput(14), 4);

        //Step Both Rotors
        rotor1.stepRotorForward();
        rotor2.stepRotorForward();

        //Stepped Once - Top
        assertEquals(rotor1.getRightOutput(0), -5);
        assertEquals(rotor2.getRightOutput(0), 9);

        //Stepped Once - Pin 14
        assertEquals(rotor1.getRightOutput(14), 18);
        assertEquals(rotor2.getRightOutput(14), 0);
        // </editor-fold>

        // <editor-fold defaultstate="collapsed" desc="Test after changing label and key">
        rotor1.stepRotorBackward();
        rotor2.stepRotorBackward();

        rotor1.setLabelPosition('B');
        rotor2.setLabelPosition('B');

        rotor1.setKeyPosition('A');
        rotor2.setKeyPosition('A');

        //New Label + Key - Initial
        assertEquals(rotor1.getRightOutput(0), 10);
        assertEquals(rotor2.getRightOutput(0), -11);

        //New Label + Key - Pin 14
        assertEquals(rotor1.getRightOutput(14), 11);
        assertEquals(rotor2.getRightOutput(14), 13);

        //Step Both Rotors
        rotor1.stepRotorForward();
        rotor2.stepRotorForward();

        //Stepped Once - Top
        assertEquals(rotor1.getRightOutput(0), -6);
        assertEquals(rotor2.getRightOutput(0), -8);

        //Stepped Once - Pin 14
        assertEquals(rotor1.getRightOutput(14), 12);
        assertEquals(rotor2.getRightOutput(14), 4);
        // </editor-fold>
    }

    @Test
    void getKeyPosition() {
        assertEquals(rotor1.getKeyPosition(), 'A');
        assertEquals(rotor2.getKeyPosition(), 'A');

        rotor1.setKeyPosition('C');
        rotor2.setKeyPosition('F');

        assertEquals(rotor1.getKeyPosition(), 'C');
        assertEquals(rotor2.getKeyPosition(), 'F');

        rotor1.setKeyPosition('L');
        rotor2.setKeyPosition('B');

        assertEquals(rotor1.getKeyPosition(), 'L');
        assertEquals(rotor2.getKeyPosition(), 'B');

        rotor1.setKeyPosition('L');
        rotor2.setKeyPosition('B');

        assertEquals(rotor1.getKeyPosition(), 'L');
        assertEquals(rotor2.getKeyPosition(), 'B');
    }

    @Test
    void willStepNextUse() {
        rotor1.setLabelPosition('A');
        rotor1.setKeyPosition('A');

        for (int i = 0; i < rotor1.getSize(); i++) {

            rotor1.stepRotorForward();

            if (rotor1.getKeyPosition() == 'Q') {
                assertEquals(rotor1.willStepNextUse(), true);
            } else {
                assertEquals(rotor1.willStepNextUse(), false);
            }
        }

        rotor2.setLabelPosition('A');
        rotor2.setKeyPosition('A');

        for (int i = 0; i < rotor2.getSize(); i++) {

            rotor2.stepRotorForward();

            if (rotor2.getKeyPosition() == 'M' || rotor2.getKeyPosition() == 'Z') {
                assertEquals(rotor2.willStepNextUse(), true);
            } else {
                assertEquals(rotor2.willStepNextUse(), false);
            }
        }

    }

    @Test
    void getLabelPosition() {
        assertEquals(rotor1.getLabelPosition(), 'A');
        assertEquals(rotor2.getLabelPosition(), 'A');

        rotor1.setLabelPosition('C');
        rotor2.setLabelPosition('F');

        assertEquals(rotor1.getLabelPosition(), 'C');
        assertEquals(rotor2.getLabelPosition(), 'F');
    }

    @Test
    void setKeyPosition() {
        rotor1.setKeyPosition('C');
        assertEquals(rotor1.getKeyPosition(), 'C');

        rotor2.setKeyPosition('K');
        assertEquals(rotor2.getKeyPosition(), 'K');
    }

    @Test
    void setLabelPosition() {
        rotor1.setLabelPosition('L');
        rotor2.setLabelPosition('Z');

        assertEquals(rotor1.getLabelPosition(), 'L');
        assertEquals(rotor2.getLabelPosition(), 'Z');
    }

    @Test
    void stepRotor() {
        rotor1.stepRotor();

        assertEquals(rotor1.getKeyPosition(), 'B');

        rotor1.stepRotor();

        assertEquals(rotor1.getKeyPosition(), 'C');
    }

    @Test
    void stepRotorForward() {
        rotor1.stepRotorForward();

        assertEquals(rotor1.getKeyPosition(), 'B');

        rotor1.stepRotorForward();

        assertEquals(rotor1.getKeyPosition(), 'C');
    }

    @Test
    void stepRotorBackward() {
        rotor1.stepRotorBackward();

        assertEquals(rotor1.getKeyPosition(), 'Z');

        rotor1.stepRotorBackward();

        assertEquals(rotor1.getKeyPosition(), 'Y');
    }

    @Test
    void getRotorName() {

        assertEquals(rotor1.getRotorName(), "I");
        assertNotEquals(rotor1.getRotorName(), "VI");

        assertEquals(rotor2.getRotorName(), "VI");
        assertNotEquals(rotor2.getRotorName(), "I");

    }
}