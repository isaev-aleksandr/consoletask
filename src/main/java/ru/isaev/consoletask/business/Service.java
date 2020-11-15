package ru.isaev.consoletask.business;


import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import ru.isaev.consoletask.model.Person;
import ru.isaev.consoletask.model.Project;
import ru.isaev.consoletask.model.Task;
import ru.isaev.consoletask.repository.PersonRep;
import ru.isaev.consoletask.repository.ProjectRep;
import ru.isaev.consoletask.repository.TaskRep;

import javax.persistence.EntityManager;


@org.springframework.stereotype.Service
class Service {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ProjectRep projectRep;

    @Autowired
    private PersonRep personRep;

    @Autowired
    private TaskRep taskRep;

    public void testData() {

        Project project = new Project();
        project.setName("Apple");
        projectRep.save(project);

        Project project2 = new Project();
        project2.setName("Android");
        projectRep.save(project2);

        Project project3 = new Project();
        project3.setName("Windows");
        projectRep.save(project3);

        Person person = new Person();
        person.setName("Alex");
        personRep.save(person);

        Person person2 = new Person();
        person2.setName("Bill");
        personRep.save(person2);

        Person person3 = new Person();
        person3.setName("Jhon");
        personRep.save(person3);


        Task task = new Task();
        task.setName("Deploy project");
        task.setPerson(person);
        person.getTaskList().add(task);
        taskRep.save(task);

        Task task2 = new Task();
        task2.setName("Handle the exception");
        task2.setPerson(person);
        person.getTaskList().add(task2);
        taskRep.save(task2);

        Task task3 = new Task();
        task3.setName("Handle the exception");
        task3.setPerson(person2);
        person2.getTaskList().add(task3);
        taskRep.save(task3);

        Task task4 = new Task();
        task4.setName("Handle the exception");
        task4.setPerson(person3);
        person3.getTaskList().add(task4);
        taskRep.save(task4);


        project.getPersonList().add(person);
        project.getPersonList().add(person2);
        project2.getPersonList().add(person);
        project3.getPersonList().add(person3);

        projectRep.save(project);
        projectRep.save(project2);
        projectRep.save(project3);

        System.out.println("test data loaded");
    }

    public List<Project> findAllProjects() {
        return projectRep.findAll();
    }

    public List<Person> findAllPersons() {
        return personRep.findAll();
    }

    public List<Task> findAllTasks() {
        return taskRep.findAll();
    }

    public void saveProject(Project project) {
        projectRep.save(project);
    }

    public void savePerson(Person person) {
        personRep.save(person);
    }

    public void saveTask(Task task) {
        taskRep.save(task);
    }

    @Transactional
    public void deleteProject(Long id) {
        projectRep.deleteByProjectId(id);
    }

    @Transactional
    public void deletePerson(Long id) {
//        Person person = personRep.findByPersonId(id);
//        List<Project> projects = projectRep.findAll();
//        for (Project project: projects){
//            project.getPersonList().remove(person);
//            person.getProjectList().remove(project);
//            projectRep.save(project);
//            personRep.save(person);
//        }
        entityManager.createNativeQuery("DELETE FROM PROJECT_PERSON where USER_ID = 1").executeUpdate();
//        personRep.deleteByPersonId(id);
    }

    @Transactional
    public void deleteTask(Long id) {
        taskRep.deleteByTaskId(id);
    }
}
