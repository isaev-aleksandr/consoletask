package ru.isaev.consoletask.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.isaev.consoletask.model.Task;

@Repository
public interface TaskRep extends CrudRepository<Task, Long> {

    List<Task> findAll();

    Task findByTaskId(Long id);

    void deleteByTaskId(Long id);
}
