package com.denisjulio;

import com.denisjulio.entities.Person;
import com.denisjulio.infrastructure.AppInfrastructure;
import com.denisjulio.infrastructure.Repository;

import java.time.LocalDate;

import static com.denisjulio.infrastructure.AppInfrastructure.injector;


public class App {
    public static void main(String[] args) {
        AppInfrastructure.plug();

        var p = new Person("Darlen Martins", LocalDate.of(1994, 3, 18));

        var repo = injector().getInstance(Repository.class);
        repo.savePerson(p);

        var persons = repo.getAllPersons();
        System.out.println(persons);
    }
}
