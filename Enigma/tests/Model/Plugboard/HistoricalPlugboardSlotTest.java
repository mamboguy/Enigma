package Model.Plugboard;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HistoricalPlugboardSlotTest {

    private HistoricalPlugboardSlot slotA = new HistoricalPlugboardSlot('A');
    private HistoricalPlugboardSlot slotB = new HistoricalPlugboardSlot('B');
    private HistoricalPlugboardSlot slotC = new HistoricalPlugboardSlot('C');
    private HistoricalPlugboardSlot slotD = new HistoricalPlugboardSlot('D');

    @Test
    void getPinInput() {
        assertEquals(slotA.getPinInput(), 0);
        assertEquals(slotB.getPinInput(), 1);
        assertEquals(slotC.getPinInput(), 2);
        assertEquals(slotD.getPinInput(), 3);
    }

    @Test
    void steckerPair() {
        slotA.steckerPair(slotB);
        slotC.steckerPair(slotD);

        assertEquals(slotA.getPinInput(), 1);
        assertEquals(slotB.getPinInput(), 0);
        assertEquals(slotC.getPinInput(), 3);
        assertEquals(slotD.getPinInput(), 2);

        //Restecker a pair where the pairing part had an original pairing
        slotA.resetPairing();
        slotB.resetPairing();
        slotC.resetPairing();
        slotD.resetPairing();
        slotA.steckerPair(slotB);
        slotA.steckerPair(slotC);

        assertEquals(slotA.getPinInput(), 2);
        assertEquals(slotB.getPinInput(), 1);
        assertEquals(slotC.getPinInput(), 0);
        assertEquals(slotD.getPinInput(), 3);

        //Restecker a pair where the part being paired had an original pairing
        slotA.resetPairing();
        slotB.resetPairing();
        slotC.resetPairing();
        slotD.resetPairing();
        slotC.steckerPair(slotB);
        slotA.steckerPair(slotC);

        assertEquals(slotA.getPinInput(), 2);
        assertEquals(slotB.getPinInput(), 1);
        assertEquals(slotC.getPinInput(), 0);
        assertEquals(slotD.getPinInput(), 3);

        //Restecker a pair where both parts of new pair had original pairings
        slotA.resetPairing();
        slotB.resetPairing();
        slotC.resetPairing();
        slotD.resetPairing();
        slotA.steckerPair(slotB);
        slotC.steckerPair(slotD);
        slotA.steckerPair(slotD);

        assertEquals(slotA.getPinInput(), 3);
        assertEquals(slotB.getPinInput(), 1);
        assertEquals(slotC.getPinInput(), 2);
        assertEquals(slotD.getPinInput(), 0);
    }

    @Test
    void resetPairing() {
        slotC.steckerPair(slotD);

        assertEquals(slotC.getPinInput(), 3);
        assertEquals(slotD.getPinInput(), 2);

        slotC.resetPairing();

        assertEquals(slotC.getPinInput(), 2);
        assertEquals(slotD.getPinInput(), 3);
    }

    @Test
    void getPermanentChar() {
        assertEquals(slotA.getPermanentChar(), 'A');
        assertEquals(slotB.getPermanentChar(), 'B');
        assertEquals(slotC.getPermanentChar(), 'C');
        assertEquals(slotD.getPermanentChar(), 'D');
    }

    @Test
    void getPermanentPin() {
        assertEquals(slotA.getPermanentPin(), 0);
        assertEquals(slotB.getPermanentPin(), 1);
        assertEquals(slotC.getPermanentPin(), 2);
        assertEquals(slotD.getPermanentPin(), 3);
    }
}