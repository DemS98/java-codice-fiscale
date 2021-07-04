# java-codice-fiscale

**java-codice-fiscale** is a Java library for generating italian codice fiscale.

Its API consists in one class, `CodiceFiscale`, which exposes the `CodiceFiscale#ofLazyInit()` method for 
initializing the singleton engine and creating the `CodiceFiscaleBuilder`. Through this builder,
the code can be generated.

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

To generate the codice fiscale, use the builder

```java
import com.demetrio.codicefiscale.exception.BirthPlaceHomonymyException;
import com.demetrio.codicefiscale.exception.CodiceCatastaleNotFoundException;
import com.demetrio.codicefiscale.exception.InvalidPersonException;
import com.demetrio.codicefiscale.model.Sex;

import java.time.LocalDate;

public class Test {
    public static void main(String[] args) {
        try {
            // this can throw homonymy exception if there are more than one comuni with that name
            String cf = CodiceFiscale.ofLazyInit()
                    .withName("Mario")
                    .withSurname("Rossi")
                    .withBirthDate(LocalDate.of(1998, 11, 22))
                    .withBirthPlace("Milano")
                    .withSex(Sex.M)
                    .generate();
            System.out.println(cf);
        } catch (BirthPlaceHomonymyException | CodiceCatastaleNotFoundException | InvalidPersonException e) {
            e.printStackTrace();
        }

        try {
            // this cannot throw homonymy exception because the pair (comune,provincia) is unique
            // the birth date is constructed without LocalDate
            String cf = CodiceFiscale.ofLazyInit()
                    .withName("Mario")
                    .withSurname("Rossi")
                    .withBirthDay(11)
                    .withBirthMonth(10)
                    .withBirthYear(1998)
                    .withBirthPlace("Milano")
                    .withBirthPlaceProvince("MI")
                    .withSex(Sex.M)
                    .generate();
            System.out.println(cf);
        } catch (CodiceCatastaleNotFoundException | InvalidPersonException e) {
            e.printStackTrace();
        }
    }
}
```

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

This library doesn't manage the omocodie (when the generated codice fiscale is relative to two or more persons) and
doesn't support foreign countries

# Author

Alessandro Chiariello