package com.demetrio.codicefiscale.engine;

import com.demetrio.codicefiscale.exception.BirthPlaceHomonymyException;
import com.demetrio.codicefiscale.exception.CodiceCatastaleNotFoundException;
import com.demetrio.codicefiscale.exception.InvalidNameException;
import com.demetrio.codicefiscale.model.Person;
import com.demetrio.codicefiscale.model.Sex;

import java.util.Optional;

public class CodiceFiscaleGenerator {

    private static final String VOWELS = "AEIOUÀÁÈÉÌÒÙ";

    private final CodiciCatastali ci;

    /**
     * Construct an instance, initializing the engine.<br/>
     * Through this method, the <i>codici_catastali.csv</i> file, containing codes for each comune,
     * will be loaded to memory.
     */
    public CodiceFiscaleGenerator() {
        ci = CodiciCatastali.getInstance();
    }

    /**
     * Generate the codice fiscale relative to the given {@code person}
     * @param person the person
     * @return the codice fiscale
     * @throws BirthPlaceHomonymyException if the birthPlace has homonyms (only if {@link Person#getBirthPlaceProvince()}
     *         return {@code null})
     * @throws CodiceCatastaleNotFoundException if the birthPlace and province (italian comune and provincia) don't exist
     * @throws InvalidNameException if the name or surname is not valid
     */
    public String generate(Person person) {
        String surnamePart = getSurnameLetters(person.getSurname());
        String namePart = getNameLetters(person.getName());
        String yearMonthPart = "" + (person.getBirthDate().getYear() % 100) +
                new BirthDateMonthTable().getChar(person.getBirthDate().getMonthValue());
        String daySexVal = person.getBirthDate().getDayOfMonth() + (person.getSex() == Sex.F ? 40 : 0) + "";
        String daySexPart = ("00" + daySexVal).substring(daySexVal.length());
        String birthPlacePart = Optional.ofNullable(person.getBirthPlaceProvince() != null ?
                ci.getCode(person.getBirthPlace(), person.getBirthPlaceProvince()) :
                ci.getCode(person.getBirthPlace()))
                    .orElseThrow(CodiceCatastaleNotFoundException::new);
        return getCodiceFiscale(surnamePart + namePart + yearMonthPart + daySexPart + birthPlacePart);
    }

    private String getSurnameLetters(String surname) {
        StringBuilder letters = new StringBuilder();
        StringBuilder vowels = new StringBuilder();
        int length = surname.length();

        // for each character in surname or until three consonants
        for(int i=0;i<length && letters.length() < 3;i++) {
            char ch = surname.charAt(i);
            if (Character.isLetter(ch)) {
                ch = Character.toUpperCase(ch);
                // if it's a consonant
                if (VOWELS.indexOf(ch) == -1) {
                    letters.append(ch);
                // otherwise if it's a vowel
                } else {
                    vowels.append(toAsciiVowel(ch));
                }
            } else if (ch != ' ' && ch != '\'') {
                throw new InvalidNameException("Surname is not valid. Found invalid char \"" + ch + "\"");
            }
        }

        // if consonants weren't enough
        if (letters.length() < 3) {
            length = vowels.length();
            // add vowels
            for(int i=0;i<length && letters.length() < 3; i++) {
                letters.append(vowels.charAt(i));
            }

            // if vowels weren't enough, add 'X' character
            if (letters.length() < 3) {
                if (letters.length() < 2)
                    throw new InvalidNameException("Surname has " + letters.length() + " letters");
                letters.append("X");
            }
        }

        return letters.toString();
    }

    private String getNameLetters(String name) {
        StringBuilder letters = new StringBuilder();
        StringBuilder vowels = new StringBuilder();
        int length = name.length();

        // for each character in surname or until four consonants
        for(int i=0;i<length && letters.length() < 4;i++) {
            char ch = name.charAt(i);
            if (Character.isLetter(ch)) {
                ch = Character.toUpperCase(ch);
                // if it's a consonant
                if (VOWELS.indexOf(ch) == -1) {
                    letters.append(ch);
                    // otherwise if it's a vowel
                } else {
                    vowels.append(toAsciiVowel(ch));
                }
            } else if (ch != ' ') {
                throw new InvalidNameException("Name is not valid. Found invalid char \"" + ch + "\"");
            }
        }

        if (letters.length() == 4)
            letters.deleteCharAt(1);
        else if (letters.length() < 3) {
            length = vowels.length();
            // add vowels
            for(int i=0;i<length && letters.length() < 3; i++) {
                letters.append(vowels.charAt(i));
            }

            // if vowels weren't enough, add 'X' character
            while (letters.length() < 3) {
                if (letters.length() < 2)
                    throw new InvalidNameException("Name has " + letters.length() + " letters");
                letters.append("X");
            }
        }

        return letters.toString();
    }

    private String getCodiceFiscale(String withoutControlCharacter) {
        int sum = 0;
        int length = withoutControlCharacter.length();
        ControlInternalNumber cin = new ControlInternalNumber();
        for(int i=0;i<length;i++) {
            char ch = withoutControlCharacter.charAt(i);
            // the algorithm count from 1 (like databases)
            sum += (i + 1) % 2 == 0 ? cin.getEvenValue(ch) : cin.getOddValue(ch);
        }
        return withoutControlCharacter + cin.getControlCharacter(sum % 26);
    }

    private char toAsciiVowel(char vowel) {
        switch (vowel) {
            case 'À': case 'Á':
                return 'A';
            case 'È': case 'É':
                return 'E';
            case 'Ì':
                return 'I';
            case 'Ò':
                return 'O';
            case 'Ù':
                return 'U';
            default:
                return vowel;
        }
    }
}
