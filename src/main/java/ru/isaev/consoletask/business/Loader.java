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
    private TaskRep taskRep;

    @Autowired
    private PersonRep personRep;

    public void loadTestData() {
        System.out.println("loading test data...");
        loadPersons();
        loadProjects();
        loadTasks();
        System.out.println("test data loaded");
    }

    private void loadProjects() {
        String filepath = "src/main/resources/projects.xml";
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
        String filepath = "src/main/resources/persons.xml";
        File xmlFile = new File(filepath);
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        try {
            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlFile);
//            doc.getDocumentElement().normalize();
//            System.out.println("Корневой элемент: " + doc.getDocumentElement().getNodeName());
            // получаем узлы с именем Language
            // теперь XML полностью загружен в память
            // в виде объекта Document
            NodeList nodeList = doc.getElementsByTagName("Person");

            // создадим из него список объектов Language
//            List<Language> langList = new ArrayList<Language>();
            for (int i = 0; i < nodeList.getLength(); i++) {
//                langList.add(getLanguage(nodeList.item(i)));
                service.savePerson(getPerson(nodeList.item(i)));
            }

            // печатаем в консоль информацию по каждому объекту Language
//            for (Person person : langList) {
//                System.out.println(lang.toString());
//            }
        } catch (Exception exc) {
            exc.printStackTrace();
        }
    }

    private void loadTasks() {
        String filepath = "src/main/resources/tasks.xml";
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

    private long[] getProjectPerson(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            try {
                Element element = (Element) node;
                long[] subLong = {Long.parseLong(getTagValue("project_id", element)),
                        Long.parseLong(getTagValue("person_id", element))};
                return subLong;
            } catch (ArrayIndexOutOfBoundsException e) {
                throw e;
            }

        }
        return new long []{1L,1L};
    }

    private static String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = (Node) nodeList.item(0);
        return node.getNodeValue();
    }
}
