package ru.isaev.consoletask.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.isaev.consoletask.model.Person;

@Repository
public interface PersonRep extends CrudRepository<Person, Long> {

    Set<Person> findAll();

    Person findByPersonId(Long id);

    @Transactional
    void deleteByPersonId(Long id);

}
