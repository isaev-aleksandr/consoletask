package ru.isaev.consoletask.business;

import java.util.List;
import java.util.Set;

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

    public Project findProjectByProjectID(Long id){
        return projectRep.findByProjectId(id);
    }

    public Person findPersonByPersonID(Long id){
        return personRep.findByPersonId(id);
    }
    public Task findTaskByTaskID(Long id){
        return taskRep.findByTaskId(id);
    }

    public Set<Project> findAllProjects() {
        return projectRep.findAll();
    }

    public Set<Person> findAllPersons() {
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
        Person person = personRep.findByPersonId(id);
        Set<Project> projects = projectRep.findAll();
        for (Project project : projects) {
            project.getPersonSet().remove(person);
            person.getProjectSet().remove(project);
        }
        entityManager.createNativeQuery("DELETE PROJECT_PERSON where USER_ID = :user_id")
                .setParameter("user_id", id).executeUpdate();
        entityManager.createNativeQuery("DELETE TASKS where PERSON_ID = :person_id")
                .setParameter("person_id", id).executeUpdate();
        personRep.deleteByPersonId(id);
    }

    @Transactional
    public void deleteTask(Long id) {
        taskRep.deleteByTaskId(id);
    }

    public void assignPersonToProject(long[] assignPersonToProject) {
        Person person = personRep.findByPersonId(assignPersonToProject[0]);
        Project project = projectRep.findByProjectId(assignPersonToProject[1]);
        project.getPersonSet().add(person);
        saveProject(project);
    }

    public void assignTaskToPerson(long[] assignTaskToPerson) {
        Task task = taskRep.findByTaskId(assignTaskToPerson[0]);
        Person person = personRep.findByPersonId(assignTaskToPerson[1]);
        task.setPerson(person);
        saveTask(task);
    }

    public void report(long[] personProject) {
        Project project = projectRep.findByProjectId(personProject[1]);
        Person person = personRep.findByPersonId(personProject[0]);
        for (Person p : project.getPersonSet()) {
            if (p.getPersonId() == person.getPersonId()) {
                System.out.println("On project " + project.getName() + " " + person.getName() + " has tasks:");
                for (Task task : p.getTaskSet()) {
                    System.out.println(task.getName());
                }
            }
        }
    }
}
