package com.demetrio.codicefiscale.engine;

import java.util.HashMap;
import java.util.Map;

class BirthDateMonthTable {

    private final Map<Integer, Character> table;

    BirthDateMonthTable() {
        table = new HashMap<>();
        table.put(1,'A');
        table.put(2,'B');
        table.put(3,'C');
        table.put(4,'D');
        table.put(5,'E');
        table.put(6,'H');
        table.put(7,'L');
        table.put(8,'M');
        table.put(9,'P');
        table.put(10,'R');
        table.put(11,'S');
        table.put(12,'T');
    }

    Character getChar(int month) {
        return table.get(month);
    }
}
