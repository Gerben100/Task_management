package com.company;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class TaskManagementSystemGUI extends Application {

    private TaskManagementSystem taskManager;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;

        taskManager = new TaskManagementSystem();

        primaryStage.setTitle("Task Management System");

        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, 400, 300);

        HBox hbox = new HBox();
        hbox.setPadding(new Insets(10, 12, 10, 12));
        hbox.setSpacing(10);

        TextField projectTextField = new TextField();
        Button addProjectButton = new Button("Add Project");
        TextField taskTextField = new TextField();
        Button addButton = new Button("Add Task");
        Button deleteButton = new Button("Delete Task");
        CheckBox completedCheckbox = new CheckBox("Mark as Completed");

        addProjectButton.setOnAction(event -> {
            String projectName = projectTextField.getText();
            if (!projectName.isEmpty()) {
                taskManager.addProject(projectName);
                updateProjectListView();
                projectTextField.clear();
            }
        });

        addButton.setOnAction(event -> {
            Project selectedProject = getSelectedProject();
            if (selectedProject != null) {
                String taskName = taskTextField.getText();
                Task newTask = new Task(taskName);
                selectedProject.getTasks().add(newTask);
                updateTaskListView();
                taskTextField.clear();
            }
        });

        deleteButton.setOnAction(event -> {
            Project selectedProject = getSelectedProject();
            Task selectedTask = getSelectedTask();
            if (selectedProject != null && selectedTask != null) {
                selectedProject.getTasks().remove(selectedTask);
                updateTaskListView();
            }
        });

        completedCheckbox.setOnAction(event -> {
            Task selectedTask = getSelectedTask();
            if (selectedTask != null) {
                selectedTask.setCompleted(completedCheckbox.isSelected());
                updateTaskListView();
            }
        });

        hbox.getChildren().addAll(projectTextField, addProjectButton, taskTextField, addButton, deleteButton, completedCheckbox);
        borderPane.setTop(hbox);

        ListView<Project> projectListView = new ListView<>();
        projectListView.setItems(getProjects());
        projectListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> updateTaskListView()
        );

        borderPane.setLeft(projectListView);

        ListView<Task> taskListView = new ListView<>();
        taskListView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> updateTaskDetailsView()
        );
        borderPane.setCenter(taskListView);

        primaryStage.setScene(scene);
        primaryStage.show();

        // Обновляем отображение списков проектов и задач при запуске
        updateProjectListView();
        updateTaskListView();
    }

    private ObservableList<Project> getProjects() {
        return FXCollections.observableArrayList(taskManager.getProjects());
    }

    private void updateProjectListView() {
        ListView<Project> projectListView = (ListView<Project>) ((BorderPane) primaryStage.getScene().getRoot()).getLeft();
        projectListView.setItems(getProjects());
    }

    private void updateTaskListView() {
        Project selectedProject = getSelectedProject();
        if (selectedProject != null) {
            ObservableList<Task> tasks = FXCollections.observableArrayList(selectedProject.getTasks());
            ListView<Task> taskListView = (ListView<Task>) ((BorderPane) primaryStage.getScene().getRoot()).getCenter();
            taskListView.setItems(tasks);
        }
    }

    private void updateTaskDetailsView() {
        Task selectedTask = getSelectedTask();
        if (selectedTask != null) {
            CheckBox completedCheckbox = (CheckBox) ((HBox) ((BorderPane) primaryStage.getScene().getRoot()).getTop()).getChildren().get(5);
            completedCheckbox.setSelected(selectedTask.isCompleted());
        }
    }

    private Project getSelectedProject() {
        ListView<Project> projectListView = (ListView<Project>) ((BorderPane) primaryStage.getScene().getRoot()).getLeft();
        return projectListView.getSelectionModel().getSelectedItem();
    }

    private Task getSelectedTask() {
        ListView<Task> taskListView = (ListView<Task>) ((BorderPane) primaryStage.getScene().getRoot()).getCenter();
        return taskListView.getSelectionModel().getSelectedItem();
    }
}