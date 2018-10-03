package Model.Rotors;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NameTest {

    private Name name1 = new Name("I");
    private Name name2 = new Name("VI");
    private Name name3 = new Name("I");

    @Test
    void getName() {
        assertEquals(name1.getName(), "I");
        assertEquals(name2.getName(), "VI");
        assertEquals(name3.getName(), "I");
    }

    @Test
    void setName() {
        name1.setName("II");

        assertEquals(name1.getName(), "II");
    }

    @Test
    void equals() {
        assertFalse(name1.equals(name2));
        assertTrue(name1.equals(name3));
        assertFalse(name2.equals(name3));
    }
}