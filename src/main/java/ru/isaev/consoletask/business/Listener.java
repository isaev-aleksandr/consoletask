package ru.isaev.consoletask.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.isaev.consoletask.model.Person;
import ru.isaev.consoletask.model.Project;
import ru.isaev.consoletask.model.Task;

import java.util.Scanner;

@Component
public class Listener {

    @Autowired
    private Service service;

    private Scanner in = new Scanner(System.in);
    private String input;

    // TODO: Output
    private String selectOptions = "Enter: create/show/delete/assign/report";
    private String selectTarget = "Enter: project/person/task";

    // TODO: Selected optional
    private boolean create = false;
    private boolean show = false;
    private boolean delete = false;
    private boolean assign = false;
    private boolean report = false;

    // TODO: Selected target
    private boolean project = false;
    private boolean person = false;
    private boolean task = false;
    private boolean personToProject = false;
    private boolean taskToPerson = false;

    public void up() {
        service.testData();
        service.loadTestData();
        System.out.println(selectOptions);
        getInput();

    }

    private void getInput() {
        if (in.hasNextInt()) {
            input = String.valueOf(in.nextInt());
        } else {
            input = in.nextLine();
        }
        handler();
    }

    private void handler() {

        switch (input) {
            case ("create"):
                create = true;
                System.out.println(selectTarget);
                break;
            case ("show"):
                show = true;
                System.out.println(selectTarget);
                break;
            case ("delete"):
                delete = true;
                System.out.println(selectTarget);
                break;
            case ("assign"):
                assign = true;
                System.out.println("Enter: person to project/task to person");
                break;
            case ("report"):
                report = true;
                System.out.println("Enter: project_id/person for task report");
                break;
            case ("project"):
                if (show) {
                    for (Project project : service.findAllProjects()) {
                        System.out.println(project.toString());
                    }
                    show = false;
                    System.out.println(selectOptions);
                } else {
                    if (delete) {
                        project = true;
                        System.out.println("Enter project ID");
                    } else {
                        if (create) {
                            project = true;
                            System.out.println("Enter project name");
                        } else {
                            System.out.println(selectOptions);
                        }
                    }
                }
                break;
            case ("person"):
                if (show) {
                    for (Person person : service.findAllPersons()) {
                        System.out.println(person.toString());
                    }
                    show = false;
                    System.out.println(selectOptions);
                } else {
                    if (delete) {
                        person = true;
                        System.out.println("Enter person ID");
                    } else {
                        if (create) {
                            person = true;
                            System.out.println("Enter person name");
                        } else {
                            System.out.println(selectOptions);
                        }
                    }
                }
                break;
            case ("task"):
                if (show) {
                    for (Task task : service.findAllTasks()) {
                        System.out.println(task.toString());
                    }
                    show = false;
                    System.out.println(selectOptions);
                } else {
                    if (delete) {
                        task = true;
                        System.out.println("Enter task ID");
                    } else {
                        if (create) {
                            task = true;
                            System.out.println("Enter task name");
                        } else {
                            System.out.println(selectOptions);
                        }
                    }
                }
                break;
            case ("person to project"):
                if (assign) {
                    personToProject = true;
                    System.out.println("Enter: person_id/project_id");
                } else {
                    System.out.println(selectOptions);
                }
                break;
            case ("task to person"):
                if (assign) {
                    taskToPerson = true;
                    System.out.println("Enter: task_id/person_id");
                } else {
                    System.out.println(selectOptions);
                }
                break;
            default:
                if (create && project) {
                    Project createdProject = new Project();
                    createdProject.setName(input);
                    service.saveProject(createdProject);
                    System.out.println("Project created");
                    for (Project project : service.findAllProjects()) {
                        System.out.println(project.toString());
                    }
                    create = false;
                    project = false;
                }
                if (create && person) {
                    Person createdPerson = new Person();
                    createdPerson.setName(input);
                    service.savePerson(createdPerson);
                    System.out.println("Person created");
                    for (Person person : service.findAllPersons()) {
                        System.out.println(person.toString());
                    }
                    create = false;
                    person = false;
                }
                if (create && task) {
                    Task createdTask = new Task();
                    createdTask.setName(input);
                    service.saveTask(createdTask);
                    System.out.println("Task created");
                    for (Task task : service.findAllTasks()) {
                        System.out.println(task.toString());
                    }
                    create = false;
                    task = false;
                }
                if (delete && project) {
                    try {
                        Long ProjectId = Long.parseLong(input);
                        service.deleteProject(ProjectId);
                        System.out.println("Project deleted");
                        for (Project project : service.findAllProjects()) {
                            System.out.println(project.toString());
                        }
                        delete = false;
                        project = false;
                    } catch (NumberFormatException e) {
                        System.out.println("expected number");
                    }

                }
                if (delete && person) {
                    try {
                        Long PersonId = Long.parseLong(input);
                        service.deletePerson(PersonId);
                        System.out.println("Person deleted");
                        for (Person person : service.findAllPersons()) {
                            System.out.println(person.toString());
                        }
                        delete = false;
                        person = false;
                    } catch (NumberFormatException e) {
                        System.out.println("expected number");
                    }
                }
                if (delete && task) {
                    try {
                        Long TaskId = Long.parseLong(input);
                        service.deleteTask(TaskId);
                        System.out.println("Task deleted");
                        for (Task task : service.findAllTasks()) {
                            System.out.println(task.toString());
                        }
                        delete = false;
                        task = false;
                    } catch (NumberFormatException e) {
                        System.out.println("expected number");
                    }
                }
                if (assign && personToProject) {
                    try {
                        try {
                            long[] parseInput = getParseInput(input);
                            service.assignPersonToProject(parseInput);
                            System.out.println("person " + parseInput[0] +
                                    " assigned to the project " + parseInput[1]);
                            assign = false;
                            personToProject = false;
                        } catch (ArrayIndexOutOfBoundsException e) {
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("expected format 2/3");
                    }
                }
                if (assign && taskToPerson) {
                    try {
                        try {
                            long[] parseInput = getParseInput(input);
                            service.assignTaskToPerson(parseInput);
                            assign = false;
                            taskToPerson = false;
                        } catch (ArrayIndexOutOfBoundsException e) {
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("expected format 2/3");
                    }
                }
                if (report) {
                    try {
                        try {
                            long[] parseInput = getParseInput(input);
                            service.report(parseInput);
                            report = false;
                        } catch (ArrayIndexOutOfBoundsException e) {
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("expected format 2/3");
                    }
                }
                if (!create && !show && !delete && !assign && !report) {
                    System.out.println(selectOptions);
                }

                break;
        }
        getInput();
    }

    private long[] getParseInput(String inputToParse) {
        String[] subString = inputToParse.split("/");
        try {
            long[] subLong = {Long.parseLong(subString[0]), Long.parseLong(subString[1])};
            return subLong;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("expected format 2/3");
            throw e;
        }
    }


}
