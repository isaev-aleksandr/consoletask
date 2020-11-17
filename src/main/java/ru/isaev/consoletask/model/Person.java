package ru.isaev.consoletask.model;

import javax.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PERSONS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)

public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id", nullable = false)
    private long personId;

    @Column(name = "person_name")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "project_person",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "project_id")})
    private Set<Project> projectSet = new HashSet<>();

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "person")
    private Set<Task> taskSet = new HashSet<>();

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

    public Set<Project> getProjectSet() {
        return projectSet;
    }

    public void setProjectSet(Set<Project> projectSet) {
        this.projectSet = projectSet;
    }

    public Set<Task> getTaskSet() {
        return taskSet;
    }

    public void setTaskSet(Set<Task> taskList) {
        this.taskSet = taskSet;
    }

    @Override
    public String toString() {
        return "Person{" +
                "personId=" + personId +
                ", name='" + name + '\'' +
                ", projects =" + setProjectsToString(projectSet) +
                "}";
    }

    private String setProjectsToString(Set<Project> projects) {
        StringBuilder builder = new StringBuilder();
        for (Project project : projects) {
            builder.append(" {id = " + project.getProjectId() + " name = " + project.getName() + "}");
        }
        return builder.toString();
    }
}
