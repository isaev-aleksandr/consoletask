package ru.isaev.consoletask.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PERSONS")@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

public class Person {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "person_id", nullable = false)
    private long personId;

    @Column(name = "person_name")
    private String name;


    @ManyToMany(targetEntity = Project.class,cascade = CascadeType.ALL )
//    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "project_person",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id")})
    private List<Project> projectList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "person")
    private List<Task> taskList = new ArrayList<>();

    public Person() {
    }


    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Project> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<Project> projectList) {
        this.projectList = projectList;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", name='" + name + '\'' +
                ", projects =" + setProjectsToString(projectList) +
                "}";
    }

    private String setProjectsToString(List<Project> projects){
        StringBuilder builder = new StringBuilder();
        for (Project project : projects){
            builder.append(" {id = " + project.getProjectId() + " name = " + project.getName() + "}");
        }
        return builder.toString();
    }
}
