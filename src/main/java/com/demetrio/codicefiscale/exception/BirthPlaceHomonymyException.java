package com.demetrio.codicefiscale.exception;

public class BirthPlaceHomonymyException extends RuntimeException {

    public BirthPlaceHomonymyException() {
        super("Specified born place has homonyms");
    }
}
