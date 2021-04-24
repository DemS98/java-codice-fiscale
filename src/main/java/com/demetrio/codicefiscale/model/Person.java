package com.demetrio.codicefiscale.model;

import java.time.LocalDate;
import java.util.Objects;

public class Person {

    private String name;

    private String surname;

    private LocalDate birthDate;

    private String birthPlace;

    private String birthPlaceProvince;

    private Sex sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getBirthPlaceProvince() {
        return birthPlaceProvince;
    }

    public void setBirthPlaceProvince(String birthPlaceProvince) {
        this.birthPlaceProvince = birthPlaceProvince;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(name, person.name) && Objects.equals(surname, person.surname) && Objects.equals(birthDate, person.birthDate) && Objects.equals(birthPlace, person.birthPlace) && Objects.equals(birthPlaceProvince, person.birthPlaceProvince) && sex == person.sex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, birthDate, birthPlace, birthPlaceProvince, sex);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", birthDate=" + birthDate +
                ", birthPlace='" + birthPlace + '\'' +
                ", birthPlaceProvince='" + birthPlaceProvince + '\'' +
                ", sex=" + sex +
                '}';
    }
}
