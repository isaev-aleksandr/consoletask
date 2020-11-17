package ru.isaev.consoletask.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.isaev.consoletask.model.Person;

@Repository
public interface PersonRep extends CrudRepository<Person, Long> {

    Set<Person> findAll();

    Person findByPersonId(Long id);

    void deleteByPersonId(Long id);

}
