package com.company;

import java.util.ArrayList;
import java.util.List;

public class TaskManagementSystem {
    private List<Project> projects;

    public TaskManagementSystem() {
        this.projects = new ArrayList<>();
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void addProject(String projectName) {
        projects.add(new Project(projectName));
    }

    public void addTaskToProject(Project project, String taskName) {
        project.addTask(taskName);
    }
}