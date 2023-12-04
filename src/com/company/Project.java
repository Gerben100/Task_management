package com.company;

import java.util.ArrayList;
import java.util.List;

public class Project {
    private String name;
    private List<Task> tasks;

    public Project(String name) {
        this.name = name;
        this.tasks = new ArrayList<Task>();
    }

    public String getName() {
        return name;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void addTask(String taskName) {
        Task task = new Task(taskName);
        tasks.add(task);
    }
}