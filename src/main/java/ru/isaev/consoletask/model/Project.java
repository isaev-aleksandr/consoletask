package ru.isaev.consoletask.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PROJECTS")
public class Project {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "project_id", nullable = false)
    private long projectId;

    @Column(name = "project_name")
    private String name;


//    @ManyToMany(fetch = FetchType.EAGER)
    @ManyToMany(targetEntity = Person.class,cascade = CascadeType.ALL )
    @JoinTable(name = "project_person",
            joinColumns = {@JoinColumn(name = "project_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<Person> personList = new ArrayList<>();

    public List<Person> getPersonList() {
        return personList;
    }

    public void setPersonList(List<Person> personList) {
        this.personList = personList;
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
                ", persons=" + setPersonsToString(personList) + "}";
    }

    private String setPersonsToString(List<Person> persons){
        StringBuilder builder = new StringBuilder();
        for (Person person : persons){
            builder.append(" {id = " + person.getPersonId() + " name = " + person.getName() + "}");
        }
        return builder.toString();
    }
}
