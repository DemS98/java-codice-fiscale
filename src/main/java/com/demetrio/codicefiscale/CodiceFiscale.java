package com.demetrio.codicefiscale;

import com.demetrio.codicefiscale.engine.CodiceFiscaleGenerator;
import com.demetrio.codicefiscale.model.Person;
import com.demetrio.codicefiscale.model.Sex;
import com.demetrio.codicefiscale.exception.BirthPlaceHomonymyException;
import com.demetrio.codicefiscale.exception.CodiceCatastaleNotFoundException;
import com.demetrio.codicefiscale.exception.InvalidNameException;

import java.time.LocalDate;

/**
 * This static class represents the API of the library.<br/>
 * It exposes three methods: one for initializing the engine and the others for
 * generating the codice fiscale. The {@link CodiceFiscale#init()} method
 * call is not required because the engine will be initialized anyway with
 * {@link CodiceFiscale#generate(String, String, LocalDate, String, Sex)} and
 * {@link CodiceFiscale#generate(String, String, LocalDate, String, String, Sex)} methods.
 * @author Alessandro Chiariello
 * @version 0.1
 */
public class CodiceFiscale {

    private static CodiceFiscaleGenerator generator;

    private CodiceFiscale() {}


    /**
     * Initialize the codice fiscale engine<br/>
     * Through this method, the <i>codici_catastali.csv</i> file, containing codes for each comune,
     * will be loaded to memory.
     */
    public static void init() {
        if (generator == null)
            generator = new CodiceFiscaleGenerator();
    }

    /**
     * Generate the codice fiscale relative to the given personal data.<br/>
     * This method can throw {@link BirthPlaceHomonymyException}
     * @param name the name
     * @param surname the surname
     * @param birthDate the date of birth
     * @param birthPlace the place where the person is born (only italian comuni)
     * @param sex the sex ({@link Sex#M} or {@link Sex#F})
     * @return the codice fiscale
     * @throws BirthPlaceHomonymyException if the birthPlace has homonyms
     * @throws CodiceCatastaleNotFoundException if the birthPlace (italian comune) doesn't exists
     * @throws InvalidNameException if the name or surname is not valid
     */
    public static String generate(String name, String surname, LocalDate birthDate, String birthPlace, Sex sex) {
        init();
        Person person = new Person();
        person.setName(name);
        person.setSurname(surname);
        person.setBirthDate(birthDate);
        person.setBirthPlace(birthPlace);
        person.setSex(sex);
        return generator.generate(person);
    }

    /**
     * Generate the codice fiscale relative to the given personal data.<br/>
     * This method cannot throw {@link BirthPlaceHomonymyException}
     * @param name the name
     * @param surname the surname
     * @param birthDate the date of birth
     * @param birthPlace the place where the person is born (only italian comuni)
     * @param birthPlaceProvince the province of the birth place (only italina provincia)
     * @param sex the sex ({@link Sex#M} or {@link Sex#F})
     * @return the codice fiscale
     * @throws CodiceCatastaleNotFoundException if the birthPlace and province (italian comune and provincia) don't exist
     * @throws InvalidNameException if the name or surname is not valid
     */
    public static String generate(String name, String surname, LocalDate birthDate, String birthPlace, String birthPlaceProvince,
                                  Sex sex) {
        init();
        Person person = new Person();
        person.setName(name);
        person.setSurname(surname);
        person.setBirthDate(birthDate);
        person.setBirthPlace(birthPlace);
        person.setBirthPlaceProvince(birthPlaceProvince);
        person.setSex(sex);
        return generator.generate(person);
    }
}
