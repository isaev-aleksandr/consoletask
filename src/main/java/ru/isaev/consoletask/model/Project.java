package ru.isaev.consoletask.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PROJECTS")
public class Project {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "project_id", nullable = false)
    private long projectId;

    @Column(name = "project_name")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "project_person",
            joinColumns = {@JoinColumn(name = "project_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<Person> personSet = new HashSet<>();

    public Set<Person> getPersonSet() {
        return personSet;
    }

    public void setPersonSet(Set<Person> personSet) {
        this.personSet = personSet;
    }

    public Project() {

    }

    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Project{" +
                "projectId=" + projectId +
                ", name='" + name + '\'' +
                ", persons=" + setPersonsToString(personSet) + "}";
    }

    private String setPersonsToString(Set<Person> persons){
        StringBuilder builder = new StringBuilder();
        for (Person person : persons){
            builder.append(" {id = " + person.getPersonId() + " name = " + person.getName() + "}");
        }
        return builder.toString();
    }
}
