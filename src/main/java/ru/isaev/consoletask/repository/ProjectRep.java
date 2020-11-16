package ru.isaev.consoletask.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.isaev.consoletask.model.Person;
import ru.isaev.consoletask.model.Project;

@Repository
public interface ProjectRep extends CrudRepository<Project, Long> {

    Set<Project> findAll();

    Project findByProjectId(Long id);

    void deleteByProjectId(Long id);

}
