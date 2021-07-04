package com.demetrio.codicefiscale;

import com.demetrio.codicefiscale.exception.BirthPlaceHomonymyException;
import com.demetrio.codicefiscale.exception.CodiceCatastaleNotFoundException;
import com.demetrio.codicefiscale.exception.InvalidPersonException;
import com.demetrio.codicefiscale.model.Sex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CodiceFiscaleInteractiveTest {

    public static void main(String[] args) {
        try(BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in))) {
            boolean exit = false;
            String name, surnmame, birthPlace, birthPlaceProvince;
            LocalDate birthDate;
            Sex sex;

            while (!exit) {
                System.out.println("---------Codice Fiscale Interactive Test-----------");
                System.out.println();
                System.out.print("Name: ");
                name = scanner.readLine();
                System.out.print("Surname: ");
                surnmame = scanner.readLine();
                System.out.print("BirthDate: ");
                birthDate = LocalDate.parse(scanner.readLine(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                System.out.print("BirthPlace: ");
                birthPlace = scanner.readLine();
                System.out.print("BirthPlaceProvince: ");
                birthPlaceProvince = scanner.readLine();
                if (birthPlaceProvince.isEmpty())
                    birthPlaceProvince = null;
                System.out.print("Sex: ");
                sex = Sex.valueOf(scanner.readLine());

                System.out.println("Codice Fiscale: " + CodiceFiscale.ofLazyInit()
                    .withName(name)
                    .withSurname(surnmame)
                    .withBirthDate(birthDate)
                    .withBirthPlace(birthPlace)
                    .withBirthPlaceProvince(birthPlaceProvince)
                    .withSex(sex)
                    .generate());
                System.out.println();
            }
        } catch (IOException | BirthPlaceHomonymyException | CodiceCatastaleNotFoundException | InvalidPersonException e) {
            e.printStackTrace();
        }
    }
}
