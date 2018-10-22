package Model.Enigma;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MessageTest {

    private Message messagePlainTextOnly = new Message("This is a test");
    private Message messageBothTexts = new Message("Test number two","aoeu aoeuid aoe");
    private Message messageBothTexts2 = new Message("mambo number five", "qjkxb qjkxbm qjkx");

    @Test
    void getPlainText() {
        assertEquals(messagePlainTextOnly.getPlainText(), "This is a test");
        assertEquals(messageBothTexts.getPlainText(), "Test number two");
        assertEquals(messageBothTexts2.getPlainText(), "mambo number five");
    }

    @Test
    void setPlainText() {
        messagePlainTextOnly.setPlainText("Test");
        messageBothTexts.setPlainText("for each loop");
        messageBothTexts2.setPlainText("gundam wing");


        assertEquals(messagePlainTextOnly.getPlainText(), "Test");
        assertEquals(messageBothTexts.getPlainText(), "for each loop");
        assertEquals(messageBothTexts2.getPlainText(), "gundam wing");
    }

    @Test
    void getCipherText() {
        assertNull(messagePlainTextOnly.getCipherText());
        assertEquals(messageBothTexts.getCipherText(), "aoeu aoeuid aoe");
        assertEquals(messageBothTexts2.getCipherText(), "qjkxb qjkxbm qjkx");
    }

    @Test
    void setCipherText() {
        messagePlainTextOnly.setCipherText("Test");
        messageBothTexts.setCipherText("for each loop");
        messageBothTexts2.setCipherText("gundam wing");


        assertEquals(messagePlainTextOnly.getCipherText(), "Test");
        assertEquals(messageBothTexts.getCipherText(), "for each loop");
        assertEquals(messageBothTexts2.getCipherText(), "gundam wing");
    }

    @Test
    void getSpacedPlainText() {
        messagePlainTextOnly.setPlainText("AOEUAOEUAOEU");

        assertEquals(messagePlainTextOnly.getSpacedPlainText(4), "AOEU AOEU AOEU");
        assertEquals(messagePlainTextOnly.getSpacedPlainText(2), "AO EU AO EU AO EU");
        assertEquals(messagePlainTextOnly.getSpacedPlainText(1), "A O E U A O E U A O E U");
        assertEquals(messagePlainTextOnly.getSpacedPlainText(0), "AOEUAOEUAOEU");
    }

    @Test
    void getSpacedCipherText() {
        messagePlainTextOnly.setCipherText("AOEUAOEUAOEU");

        assertEquals(messagePlainTextOnly.getSpacedCipherText(4), "AOEU AOEU AOEU");
        assertEquals(messagePlainTextOnly.getSpacedCipherText(2), "AO EU AO EU AO EU");
        assertEquals(messagePlainTextOnly.getSpacedCipherText(1), "A O E U A O E U A O E U");
    }
}