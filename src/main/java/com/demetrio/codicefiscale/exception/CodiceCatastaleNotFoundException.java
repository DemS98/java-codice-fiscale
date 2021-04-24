package com.demetrio.codicefiscale.exception;

public class CodiceCatastaleNotFoundException extends RuntimeException {
    public CodiceCatastaleNotFoundException() {
        super("Codice catastale not found");
    }
}
