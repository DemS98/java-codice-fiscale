package com.demetrio.codicefiscale;

import com.demetrio.codicefiscale.engine.CodiceFiscaleGenerator;
import com.demetrio.codicefiscale.exception.InvalidPersonException;
import com.demetrio.codicefiscale.model.Person;
import com.demetrio.codicefiscale.model.Sex;
import com.demetrio.codicefiscale.exception.BirthPlaceHomonymyException;
import com.demetrio.codicefiscale.exception.CodiceCatastaleNotFoundException;

import java.time.LocalDate;
import java.util.Optional;

/**
 * This static class represents the API of the library.<br/>
 * It exposes one method for initializing the engine lazily and creating
 * the {@link CodiceFiscaleBuilder} for the Codice Fiscale generation.<br/>
 * The engine is a singleton, so it will be initialized only once.
 * @author Alessandro Chiariello
 * @version 0.2
 */
public class CodiceFiscale {

    /**
     * Builder class for generating the Codice Fiscale
     * @author Alessandro Chiariello
     * @version 0.2
     */
    public static class CodiceFiscaleBuilder {

        private final CodiceFiscaleGenerator generator;
        private String name;
        private String surname;
        private LocalDate birthDate;
        private String birthPlace;
        private String birthPlaceProvince;
        private Sex sex;

        private CodiceFiscaleBuilder(CodiceFiscaleGenerator generator) {
            this.generator = generator;
        }

        /**
         * The name of the person (required)
         * @param name the name
         * @return the builder instance
         */
        public CodiceFiscaleBuilder withName(String name) {
            this.name = name;
            return this;
        }

        /**
         * The surname of the person (required)
         * @param surname the surname
         * @return the builder instance
         */
        public CodiceFiscaleBuilder withSurname(String surname) {
            this.surname = surname;
            return this;
        }

        /**
         * The birth date of the person.<br/>
         * The birth date can also be constructed through these methods:
         * <ul>
         *     <li>{@link CodiceFiscaleBuilder#withBirthDay(int)}</li>
         *     <li>{@link CodiceFiscaleBuilder#withBirthMonth(int)}</li>
         *     <li>{@link CodiceFiscaleBuilder#withBirthYear(int)}</li>
         * </ul>
         * In whatever way it is built, the birth date is required.
         * @param birthDate the birth date
         * @return the builder instance
         */
        public CodiceFiscaleBuilder withBirthDate(LocalDate birthDate) {
            this.birthDate = birthDate;
            return this;
        }

        /**
         * The birth day of the person.<br/>
         * If no date method was called, get the actual date and change the day of month,
         * otherwise change only the day of month.
         * @param day the birth day
         * @return the builder instance
         */
        public CodiceFiscaleBuilder withBirthDay(int day) {
            birthDate = Optional.ofNullable(birthDate).orElse(LocalDate.now()).withDayOfMonth(day);
            return this;
        }

        /**
         * The birth month of the person.<br/>
         * If no date method was called, get the actual date and change the month,
         * otherwise change only the month.
         * @param month the birth month
         * @return the builder instance
         */
        public CodiceFiscaleBuilder withBirthMonth(int month) {
            birthDate = Optional.ofNullable(birthDate).orElse(LocalDate.now()).withMonth(month);
            return this;
        }

        /**
         * The birth year of the person.<br/>
         * If no date method was called, get the actual date and change the year,
         * otherwise change only the year.
         * @param year the birth year
         * @return the builder instance
         */
        public CodiceFiscaleBuilder withBirthYear(int year) {
            birthDate = Optional.ofNullable(birthDate).orElse(LocalDate.now()).withYear(year);
            return this;
        }

        /**
         * The birth place of the person (required)
         * @param birthPlace the birth place
         * @return the builder instance
         */
        public CodiceFiscaleBuilder withBirthPlace(String birthPlace) {
            this.birthPlace = birthPlace;
            return this;
        }

        /**
         * The province of the birth place (not required).<br/>
         * Can be useful to avoid {@link BirthPlaceHomonymyException}.
         * @param birthPlaceProvince the birth place province
         * @return the builder instance
         */
        public CodiceFiscaleBuilder withBirthPlaceProvince(String birthPlaceProvince) {
            this.birthPlaceProvince = birthPlaceProvince;
            return this;
        }

        /**
         * The sex of the person
         * @param sex the sex
         * @return the builder instance
         */
        public CodiceFiscaleBuilder withSex(Sex sex) {
            this.sex = sex;
            return this;
        }

        /**
         * Generate the Codice Fiscale with the personal data given through the builder.
         * @return the Codice Fiscale
         * @throws BirthPlaceHomonymyException if the birthPlace has homonyms (when province has not been set)
         * @throws CodiceCatastaleNotFoundException if the birthPlace and province (italian comune and provincia) don't exist
         * @throws InvalidPersonException if the person is not valid (missing surname, birthPlace...)
         */
        public String generate() {
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

    private CodiceFiscale() {}

    /**
     * Initialize the codice fiscale engine and get the {@link CodiceFiscaleBuilder}.<br/>
     * Through this method, the <i>codici_catastali.csv</i> file, containing codes for each comune,
     * will be loaded into memory.
     */
    public static CodiceFiscaleBuilder ofLazyInit() {
        return new CodiceFiscaleBuilder(new CodiceFiscaleGenerator());
    }

}
