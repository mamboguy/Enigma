package Model.Plugboard;

import Model.Setting.PlugboardSetting;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HistoricalPlugboardTest {

    private HistoricalPlugboard plugboard = new HistoricalPlugboard();

    @Test
    void resetPlugboard() {
        plugboard.resetPlugboard();

        assertEquals(plugboard.convertInput('A'), 0);
        assertEquals(plugboard.convertInput('B'), 1);
        assertEquals(plugboard.convertInput('C'), 2);
        assertEquals(plugboard.convertInput('D'), 3);
        assertEquals(plugboard.convertInput('E'), 4);
        assertEquals(plugboard.convertInput('F'), 5);
        assertEquals(plugboard.convertInput('G'), 6);
        assertEquals(plugboard.convertInput('H'), 7);
        assertEquals(plugboard.convertInput('I'), 8);
        assertEquals(plugboard.convertInput('J'), 9);
        assertEquals(plugboard.convertInput('K'), 10);
        assertEquals(plugboard.convertInput('L'), 11);
        assertEquals(plugboard.convertInput('M'), 12);
        assertEquals(plugboard.convertInput('N'), 13);
        assertEquals(plugboard.convertInput('O'), 14);
        assertEquals(plugboard.convertInput('P'), 15);
        assertEquals(plugboard.convertInput('Q'), 16);
        assertEquals(plugboard.convertInput('R'), 17);
        assertEquals(plugboard.convertInput('S'), 18);
        assertEquals(plugboard.convertInput('T'), 19);
        assertEquals(plugboard.convertInput('U'), 20);
        assertEquals(plugboard.convertInput('V'), 21);
        assertEquals(plugboard.convertInput('W'), 22);
        assertEquals(plugboard.convertInput('X'), 23);
        assertEquals(plugboard.convertInput('Y'), 24);
        assertEquals(plugboard.convertInput('Z'), 25);

        plugboard.steckerPattern(new PlugboardSetting("AB CD LV TK LM OW"));
        plugboard.resetPlugboard();

        assertEquals(plugboard.convertInput('A'), 0);
        assertEquals(plugboard.convertInput('B'), 1);
        assertEquals(plugboard.convertInput('C'), 2);
        assertEquals(plugboard.convertInput('D'), 3);
        assertEquals(plugboard.convertInput('E'), 4);
        assertEquals(plugboard.convertInput('F'), 5);
        assertEquals(plugboard.convertInput('G'), 6);
        assertEquals(plugboard.convertInput('H'), 7);
        assertEquals(plugboard.convertInput('I'), 8);
        assertEquals(plugboard.convertInput('J'), 9);
        assertEquals(plugboard.convertInput('K'), 10);
        assertEquals(plugboard.convertInput('L'), 11);
        assertEquals(plugboard.convertInput('M'), 12);
        assertEquals(plugboard.convertInput('N'), 13);
        assertEquals(plugboard.convertInput('O'), 14);
        assertEquals(plugboard.convertInput('P'), 15);
        assertEquals(plugboard.convertInput('Q'), 16);
        assertEquals(plugboard.convertInput('R'), 17);
        assertEquals(plugboard.convertInput('S'), 18);
        assertEquals(plugboard.convertInput('T'), 19);
        assertEquals(plugboard.convertInput('U'), 20);
        assertEquals(plugboard.convertInput('V'), 21);
        assertEquals(plugboard.convertInput('W'), 22);
        assertEquals(plugboard.convertInput('X'), 23);
        assertEquals(plugboard.convertInput('Y'), 24);
        assertEquals(plugboard.convertInput('Z'), 25);
    }

    @Test
    void convertInput() {
        plugboard.steckerPattern(new PlugboardSetting("AE BC ZT OR LQ"));

        assertEquals(plugboard.convertInput('A'), 4);
        assertEquals(plugboard.convertInput('B'), 2);
        assertEquals(plugboard.convertInput('C'), 1);
        assertEquals(plugboard.convertInput('D'), 3);
        assertEquals(plugboard.convertInput('E'), 0);
        assertEquals(plugboard.convertInput('F'), 5);
        assertEquals(plugboard.convertInput('G'), 6);
        assertEquals(plugboard.convertInput('H'), 7);
        assertEquals(plugboard.convertInput('I'), 8);
        assertEquals(plugboard.convertInput('J'), 9);
        assertEquals(plugboard.convertInput('K'), 10);
        assertEquals(plugboard.convertInput('L'), 16);
        assertEquals(plugboard.convertInput('M'), 12);
        assertEquals(plugboard.convertInput('N'), 13);
        assertEquals(plugboard.convertInput('O'), 17);
        assertEquals(plugboard.convertInput('P'), 15);
        assertEquals(plugboard.convertInput('Q'), 11);
        assertEquals(plugboard.convertInput('R'), 14);
        assertEquals(plugboard.convertInput('S'), 18);
        assertEquals(plugboard.convertInput('T'), 25);
        assertEquals(plugboard.convertInput('U'), 20);
        assertEquals(plugboard.convertInput('V'), 21);
        assertEquals(plugboard.convertInput('W'), 22);
        assertEquals(plugboard.convertInput('X'), 23);
        assertEquals(plugboard.convertInput('Y'), 24);
        assertEquals(plugboard.convertInput('Z'), 19);

        plugboard.steckerPattern(new PlugboardSetting("AI KR XG FY ST WB CL"));

        assertEquals(plugboard.convertInput('A'), 8);
        assertEquals(plugboard.convertInput('B'), 22);
        assertEquals(plugboard.convertInput('C'), 11);
        assertEquals(plugboard.convertInput('D'), 3);
        assertEquals(plugboard.convertInput('E'), 4);
        assertEquals(plugboard.convertInput('F'), 24);
        assertEquals(plugboard.convertInput('G'), 23);
        assertEquals(plugboard.convertInput('H'), 7);
        assertEquals(plugboard.convertInput('I'), 0);
        assertEquals(plugboard.convertInput('J'), 9);
        assertEquals(plugboard.convertInput('K'), 17);
        assertEquals(plugboard.convertInput('L'), 2);
        assertEquals(plugboard.convertInput('M'), 12);
        assertEquals(plugboard.convertInput('N'), 13);
        assertEquals(plugboard.convertInput('O'), 14);
        assertEquals(plugboard.convertInput('P'), 15);
        assertEquals(plugboard.convertInput('Q'), 16);
        assertEquals(plugboard.convertInput('R'), 10);
        assertEquals(plugboard.convertInput('S'), 19);
        assertEquals(plugboard.convertInput('T'), 18);
        assertEquals(plugboard.convertInput('U'), 20);
        assertEquals(plugboard.convertInput('V'), 21);
        assertEquals(plugboard.convertInput('W'), 1);
        assertEquals(plugboard.convertInput('X'), 6);
        assertEquals(plugboard.convertInput('Y'), 5);
        assertEquals(plugboard.convertInput('Z'), 25);
    }

    @Test
    void convertOutput() {
        plugboard.steckerPattern(new PlugboardSetting("AE BC ZT OR LQ"));

        assertEquals(plugboard.convertOutput(0), 'E');
        assertEquals(plugboard.convertOutput(1), 'C');
        assertEquals(plugboard.convertOutput(2), 'B');
        assertEquals(plugboard.convertOutput(3), 'D');
        assertEquals(plugboard.convertOutput(4), 'A');
        assertEquals(plugboard.convertOutput(5), 'F');
        assertEquals(plugboard.convertOutput(6), 'G');
        assertEquals(plugboard.convertOutput(7), 'H');
        assertEquals(plugboard.convertOutput(8), 'I');
        assertEquals(plugboard.convertOutput(9), 'J');
        assertEquals(plugboard.convertOutput(10),'K');
        assertEquals(plugboard.convertOutput(11),'Q');
        assertEquals(plugboard.convertOutput(12),'M');
        assertEquals(plugboard.convertOutput(13),'N');
        assertEquals(plugboard.convertOutput(14),'R');
        assertEquals(plugboard.convertOutput(15),'P');
        assertEquals(plugboard.convertOutput(16),'L');
        assertEquals(plugboard.convertOutput(17),'O');
        assertEquals(plugboard.convertOutput(18),'S');
        assertEquals(plugboard.convertOutput(19),'Z');
        assertEquals(plugboard.convertOutput(20),'U');
        assertEquals(plugboard.convertOutput(21),'V');
        assertEquals(plugboard.convertOutput(22),'W');
        assertEquals(plugboard.convertOutput(23),'X');
        assertEquals(plugboard.convertOutput(24),'Y');
        assertEquals(plugboard.convertOutput(25),'T');

        plugboard.steckerPattern(new PlugboardSetting("AI KR XG FY ST WB CL"));

        assertEquals(plugboard.convertOutput(0), 'I');
        assertEquals(plugboard.convertOutput(1), 'W');
        assertEquals(plugboard.convertOutput(2), 'L');
        assertEquals(plugboard.convertOutput(3), 'D');
        assertEquals(plugboard.convertOutput(4), 'E');
        assertEquals(plugboard.convertOutput(5), 'Y');
        assertEquals(plugboard.convertOutput(6), 'X');
        assertEquals(plugboard.convertOutput(7), 'H');
        assertEquals(plugboard.convertOutput(8), 'A');
        assertEquals(plugboard.convertOutput(9), 'J');
        assertEquals(plugboard.convertOutput(10),'R');
        assertEquals(plugboard.convertOutput(11),'C');
        assertEquals(plugboard.convertOutput(12),'M');
        assertEquals(plugboard.convertOutput(13),'N');
        assertEquals(plugboard.convertOutput(14),'O');
        assertEquals(plugboard.convertOutput(15),'P');
        assertEquals(plugboard.convertOutput(16),'Q');
        assertEquals(plugboard.convertOutput(17),'K');
        assertEquals(plugboard.convertOutput(18),'T');
        assertEquals(plugboard.convertOutput(19),'S');
        assertEquals(plugboard.convertOutput(20),'U');
        assertEquals(plugboard.convertOutput(21),'V');
        assertEquals(plugboard.convertOutput(22),'B');
        assertEquals(plugboard.convertOutput(23),'G');
        assertEquals(plugboard.convertOutput(24),'F');
        assertEquals(plugboard.convertOutput(25),'Z');

        plugboard.resetPlugboard();

        assertEquals(plugboard.convertOutput(0), 'A');
        assertEquals(plugboard.convertOutput(1), 'B');
        assertEquals(plugboard.convertOutput(2), 'C');
        assertEquals(plugboard.convertOutput(3), 'D');
        assertEquals(plugboard.convertOutput(4), 'E');
        assertEquals(plugboard.convertOutput(5), 'F');
        assertEquals(plugboard.convertOutput(6), 'G');
        assertEquals(plugboard.convertOutput(7), 'H');
        assertEquals(plugboard.convertOutput(8), 'I');
        assertEquals(plugboard.convertOutput(9), 'J');
        assertEquals(plugboard.convertOutput(10),'K');
        assertEquals(plugboard.convertOutput(11),'L');
        assertEquals(plugboard.convertOutput(12),'M');
        assertEquals(plugboard.convertOutput(13),'N');
        assertEquals(plugboard.convertOutput(14),'O');
        assertEquals(plugboard.convertOutput(15),'P');
        assertEquals(plugboard.convertOutput(16),'Q');
        assertEquals(plugboard.convertOutput(17),'R');
        assertEquals(plugboard.convertOutput(18),'S');
        assertEquals(plugboard.convertOutput(19),'T');
        assertEquals(plugboard.convertOutput(20),'U');
        assertEquals(plugboard.convertOutput(21),'V');
        assertEquals(plugboard.convertOutput(22),'W');
        assertEquals(plugboard.convertOutput(23),'X');
        assertEquals(plugboard.convertOutput(24),'Y');
        assertEquals(plugboard.convertOutput(25),'Z');
    }

    @Test
    void steckerPattern() {
        //Tested in other tests
    }
}