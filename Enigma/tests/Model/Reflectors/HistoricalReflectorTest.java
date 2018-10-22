package Model.Reflectors;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HistoricalReflectorTest {

        private HistoricalReflector reflector1 = new HistoricalReflector("UKW-A", "EJMZALYXVBWFCRQUONTSPIKHGD", "");
        private HistoricalReflector reflector2 = new HistoricalReflector("UKW-B-Thin", "ENKQAUYWJICOPBLMDXZVFTHRGS", "");

    @Test
    void getReflection() {
        assertEquals(reflector1.getReflection(0), 4);
        assertEquals(reflector1.getReflection(1), 9);

        assertEquals(reflector2.getReflection(0), 4);
        assertEquals(reflector2.getReflection(1), 13);
    }

    @Test
    void getReflectorName() {
        assertEquals(reflector1.getReflectorName(), "UKW-A");
        assertEquals(reflector2.getReflectorName(), "UKW-B-Thin");
    }
}