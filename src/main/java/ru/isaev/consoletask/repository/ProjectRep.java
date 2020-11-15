package ru.isaev.consoletask.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.isaev.consoletask.model.Project;

@Repository
public interface ProjectRep extends CrudRepository<Project, Long> {

    List<Project> findAll();

    void deleteByProjectId(Long id);

}
