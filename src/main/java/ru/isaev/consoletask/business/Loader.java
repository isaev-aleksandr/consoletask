package ru.isaev.consoletask.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.isaev.consoletask.model.Person;
import ru.isaev.consoletask.model.Project;
import ru.isaev.consoletask.model.Task;
import ru.isaev.consoletask.repository.PersonRep;
import ru.isaev.consoletask.repository.TaskRep;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

@Component
public class Loader {

    @Autowired
    private Service service;

    @Autowired
    private PersonRep personRep;

    public void loadTestData() {
        System.out.println("loading test data...");
        loadPersons();
        loadProjects();
        loadTasks();
        loadProjectPerson();
        System.out.println("test data loaded");
    }

    private void loadProjects() {
        String filepath = "projects.xml";
        File xmlFile = new File(filepath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            NodeList nodeList = doc.getElementsByTagName("project");
            for (int i = 0; i < nodeList.getLength(); i++) {
                service.saveProject(getProject(nodeList.item(i)));
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private void loadPersons() {
        String filepath = "persons.xml";
        File xmlFile = new File(filepath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            NodeList nodeList = doc.getElementsByTagName("Person");
            for (int i = 0; i < nodeList.getLength(); i++) {
                service.savePerson(getPerson(nodeList.item(i)));
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private void loadTasks() {
        String filepath = "tasks.xml";
        File xmlFile = new File(filepath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);

            NodeList nodeList = doc.getElementsByTagName("task");

            for (int i = 0; i < nodeList.getLength(); i++) {
                service.saveTask(getTask(nodeList.item(i)));
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private void loadProjectPerson() {
        String filepath = "projects_persons.xml";
        File xmlFile = new File(filepath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
            NodeList nodeList = doc.getElementsByTagName("project_person");

            for (int i = 0; i < nodeList.getLength(); i++) {
                service.assignPersonToProject(getPersonProject(nodeList.item(i)));
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private static Person getPerson(Node node) {
        Person person = new Person();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            person.setName(getTagValue("name", element));
        }
        return person;
    }

    private static Project getProject(Node node) {
        Project project = new Project();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            project.setName(getTagValue("name", element));
        }
        return project;
    }

    private Task getTask(Node node) {
        Task task = new Task();
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;
            task.setName(getTagValue("name", element));
            task.setPerson(personRep.findByPersonId(
                    Long.parseLong(getTagValue("person_id", element))));
        }
        return task;
    }

    private long[] getPersonProject(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            try {
                Element element = (Element) node;
                long[] subLong = {Long.parseLong(getTagValue("person_id", element)),
                        Long.parseLong(getTagValue("project_id", element))};
                return subLong;
            } catch (ArrayIndexOutOfBoundsException e) {
                throw e;
            }
        }
        return new long[]{1L, 1L};
    }
    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }
}
