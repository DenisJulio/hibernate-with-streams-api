package com.denisjulio;

import com.denisjulio.entities.Person;
import com.google.inject.Inject;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import java.util.List;

@AllArgsConstructor(onConstructor_={@Inject})
public class Repository {

    private final EntityManager entityManager;

    public void savePerson(Person person) {
        entityManager.getTransaction().begin();
        entityManager.persist(person);
        entityManager.getTransaction().commit();
    }

    public List<Person> getAllPersons() {
        return entityManager.createQuery("select p from Person p", Person.class).getResultList();
    }
}
