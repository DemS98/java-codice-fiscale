# java-codice-fiscale

**java-codice-fiscale** is a Java library for generating italian codice fiscale.

Its API consists in one class, `CodiceFiscale`, which exposes three methods:

1) `CodiceFiscale#init()` for initializing the engine
2) `CodiceFiscale#generate(String name, String surname, LocalDate birthDate, String birthPlace, Sex sex)` for
    generating the codice fiscale, without specifying the province. This method can throw `BirthPlaceHomonymyException`
3) `CodiceFiscale#generate(String name, String surname, LocalDate birthDate, String birthPlace, String birthPlaceProvince, Sex sex)` for
    generating the codice fiscale. This method cannot throw `BirthPlaceHomonymyException`

## Installation

Clone this repo and, in the root folder, run

```shell
mvn clean package
```

to generate the jar.

To install in the local repository, run

```shell
mvn clean install
```

## Usage

To generate the codice fiscale, call one of the *generate* methods

```java
import com.demetrio.codicefiscale.CodiceFiscale;
import com.demetrio.codicefiscale.exception.BirthPlaceHomonymyException;
import com.demetrio.codicefiscale.exception.CodiceCatastaleNotFoundException;
import com.demetrio.codicefiscale.exception.InvalidNameException;

public class Test {
    public static void main(String[] args) {
        try {
            // this can throw homonymy exception if there are more than one comuni with that name
            String cf = CodiceFiscale.generate("Mario", "Rossi", LocalDate.of(1998, 11, 22),
                    "Milano", Sex.M);
            System.out.println(cf);
        } catch (BirthPlaceHomonymyException | CodiceCatastaleNotFoundException | InvalidNameException e) {
            e.printStackTrace();
        }

        try {
            // this cannot throw homonymy exception because the pair (comune,provincia) is unique
            String cf = CodiceFiscale.generate("Mario", "Rossi", LocalDate.of(1998, 11, 22),
                    "Milano", "MI", Sex.M);
            System.out.println(cf);
        } catch (CodiceCatastaleNotFoundException | InvalidNameException e) {
            e.printStackTrace();
        }
    }
}
```

The first call will initialize the engine, so it will take more time.\
If you want an eager initialization, you can call the `CodiceFiscale#init()` method.

Moreover, you can also get the codice fiscale directly from the `CodiceFiscaleGenerator` class,
like in this example

```java
import com.demetrio.codicefiscale.CodiceFiscale;
import com.demetrio.codicefiscale.engine.CodiceFiscaleGenerator;
import com.demetrio.codicefiscale.exception.CodiceCatastaleNotFoundException;
import com.demetrio.codicefiscale.exception.InvalidNameException;
import com.demetrio.codicefiscale.model.Person;
import com.demetrio.codicefiscale.model.Sex;

import java.time.LocalDate;

public class Test {
    public static void main(String[] args) {
        try {
            Person person = new Person();
            person.setName("Mario");
            person.setSurname("Rossi");
            person.setBirthDate(LocalDate.of(1998, 11, 22));
            person.setBirthPlace("Milano");
            person.setBirthPlaceProvince("MI");
            person.setSex(Sex.M);
            String cf = new CodiceFiscaleGenerator().generate(person);
            System.out.println(cf);
        } catch (CodiceCatastaleNotFoundException | InvalidNameException e) {
            e.printStackTrace();
        }
    }
}
```

but it's preferable the `CodiceFiscale` class as it is more concise

### Note

This library doesn't manage the omocodie (when the generated codice fiscale is relative to two or more persons)

# Author

Alessandro Chiariello