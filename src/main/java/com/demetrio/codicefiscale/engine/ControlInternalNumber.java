package com.demetrio.codicefiscale.engine;

import java.util.HashMap;
import java.util.Map;

class ControlInternalNumber {

    private final Map<Character, Integer> odd;
    private final Map<Character, Integer> even;
    private final char[] remainder;

    ControlInternalNumber() {
        odd = new HashMap<>();
        odd.put('0',1);
        odd.put('1',0);
        odd.put('2',5);
        odd.put('3',7);
        odd.put('4',9);
        odd.put('5',13);
        odd.put('6',15);
        odd.put('7',17);
        odd.put('8',19);
        odd.put('9',21);
        odd.put('A',1);
        odd.put('B',0);
        odd.put('C',5);
        odd.put('D',7);
        odd.put('E',9);
        odd.put('F',13);
        odd.put('G',15);
        odd.put('H',17);
        odd.put('I',19);
        odd.put('J',21);
        odd.put('K',2);
        odd.put('L',4);
        odd.put('M',18);
        odd.put('N',20);
        odd.put('O',11);
        odd.put('P',3);
        odd.put('Q',6);
        odd.put('R',8);
        odd.put('S',12);
        odd.put('T',14);
        odd.put('U',16);
        odd.put('V',10);
        odd.put('W',22);
        odd.put('X',25);
        odd.put('Y',24);
        odd.put('Z',23);

        even = new HashMap<>();
        even.put('0',0);
        even.put('1',1);
        even.put('2',2);
        even.put('3',3);
        even.put('4',4);
        even.put('5',5);
        even.put('6',6);
        even.put('7',7);
        even.put('8',8);
        even.put('9',9);
        even.put('A',0);
        even.put('B',1);
        even.put('C',2);
        even.put('D',3);
        even.put('E',4);
        even.put('F',5);
        even.put('G',6);
        even.put('H',7);
        even.put('I',8);
        even.put('J',9);
        even.put('K',10);
        even.put('L',11);
        even.put('M',12);
        even.put('N',13);
        even.put('O',14);
        even.put('P',15);
        even.put('Q',16);
        even.put('R',17);
        even.put('S',18);
        even.put('T',19);
        even.put('U',20);
        even.put('V',21);
        even.put('W',22);
        even.put('X',23);
        even.put('Y',24);
        even.put('Z',25);

        remainder = new char[26];
        remainder[0] = 'A';
        remainder[1] = 'B';
        remainder[2] = 'C';
        remainder[3] = 'D';
        remainder[4] = 'E';
        remainder[5] = 'F';
        remainder[6] = 'G';
        remainder[7] = 'H';
        remainder[8] = 'I';
        remainder[9] = 'J';
        remainder[10] = 'K';
        remainder[11] = 'L';
        remainder[12] = 'M';
        remainder[13] = 'N';
        remainder[14] = 'O';
        remainder[15] = 'P';
        remainder[16] = 'Q';
        remainder[17] = 'R';
        remainder[18] = 'S';
        remainder[19] = 'T';
        remainder[20] = 'U';
        remainder[21] = 'V';
        remainder[22] = 'W';
        remainder[23] = 'X';
        remainder[24] = 'Y';
        remainder[25] = 'Z';
    }

    Integer getOddValue(Character ch) {
        return odd.get(ch);
    }

    Integer getEvenValue(Character ch) {
        return even.get(ch);
    }

    char getControlCharacter(Integer value) {
        return remainder[value];
    }
}
