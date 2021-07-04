package com.demetrio.codicefiscale.engine;

import com.demetrio.codicefiscale.exception.InvalidPersonException;
import com.demetrio.codicefiscale.model.Person;

class PersonChecker {

    public static void checkPerson(Person person) {
        if (person.getName() == null || person.getName().isEmpty()) {
            throw new InvalidPersonException("Name is not present");
        }

        if (person.getSurname() == null || person.getSurname().isEmpty()) {
            throw new InvalidPersonException("Surname is not present");
        }

        if (person.getBirthDate() == null) {
            throw new InvalidPersonException("Birth date is not present");
        }

        if (person.getBirthPlace() == null || person.getBirthPlace().isEmpty()) {
            throw new InvalidPersonException("Birth place is not present");
        }

        if (person.getSex() == null) {
            throw new InvalidPersonException("Sex is not present");
        }
    }
}
