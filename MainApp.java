package com.example.placementmanagementmodule;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.sql.*;

public class MainApp extends Application {

    private Connection connection;

    public static void main(String[] args) {

        launch(args);
    }


    @Override
    public void start(Stage primaryStage) {
        connectDatabase();
        primaryStage.setTitle("Placement Management Module");

        // Login Page
        VBox loginBox = new VBox(10);
        loginBox.setPadding(new Insets(10));
        loginBox.setAlignment(Pos.CENTER);
        Label lb = new Label("Please Login");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        Button loginButton = new Button("Login as Admin");
        Button studentGuestButton = new Button("Login as Student");
        Button login1 = new Button("Login Page");
        Button login2 = new Button("Login Page");
        loginBox.getChildren().addAll(lb, usernameField, passwordField, loginButton,new Label("or"), studentGuestButton);
        Scene loginScene = new Scene(loginBox, 400, 250);


        // Admin Page
        VBox adminBox = new VBox(10);
        adminBox.setPadding(new Insets(10));
        adminBox.setAlignment(Pos.CENTER);
        Button addPostButton = new Button("Add New Post");
        Button updatePostButton = new Button("Update Post");
        Button seeAppliedStudentButton = new Button("Jobs applied by students");
        Button addStudentButton = new Button("Add Student");
        Button updateStudentButton = new Button("Update Student");
        Button homeA1 = new Button("Home");
        Button homeA2 = new Button("Home");
        Button homeA3 = new Button("Home");
        Button homeA4 = new Button("Home");
        adminBox.getChildren().addAll(addPostButton, updatePostButton,seeAppliedStudentButton, addStudentButton, updateStudentButton,login1);
        Scene adminScene = new Scene(adminBox, 400, 250);
        homeA1.setOnAction(e -> primaryStage.setScene(adminScene));
        homeA2.setOnAction(e -> primaryStage.setScene(adminScene));
        homeA3.setOnAction(e -> primaryStage.setScene(adminScene));
        homeA4.setOnAction(e -> primaryStage.setScene(adminScene));
        login1.setOnAction(e -> primaryStage.setScene(loginScene));


        // Student/Guest Page
        VBox studentBox = new VBox(10);
        studentBox.setPadding(new Insets(10));
        studentBox.setAlignment(Pos.CENTER);
        Button seeJobPostsButton = new Button("See Job Posts");
        Button applyForJobsButton = new Button("Apply for Jobs");
        Button resultsButton = new Button("Results");
        Button homeS1 = new Button("Home");
        Button homeS2 = new Button("Home");
        Button homeS3 = new Button("Home");
        studentBox.getChildren().addAll(seeJobPostsButton, applyForJobsButton, resultsButton,login2);
        Scene studentScene = new Scene(studentBox, 400, 250);

        homeS1.setOnAction(e -> primaryStage.setScene(studentScene));
        homeS2.setOnAction(e -> primaryStage.setScene(studentScene));
        homeS3.setOnAction(e -> primaryStage.setScene(studentScene));
        login2.setOnAction(e -> primaryStage.setScene(loginScene));

        loginButton.setOnAction(e -> {
            if (validateAdmin(usernameField.getText(), passwordField.getText(),"admin")) {
                primaryStage.setScene(adminScene);
            } else {
                showAlert(Alert.AlertType.ERROR, "Invalid Credentials");
            }
            usernameField.setText("");
            passwordField.setText("");
        });




        studentGuestButton.setOnAction(e -> {
            if(validateStudent(usernameField.getText(),passwordField.getText(),"student")){
                primaryStage.setScene(studentScene);
            }
            else{
                showAlert(Alert.AlertType.ERROR, "Invalid Credentials");
            }
            usernameField.setText("");
            passwordField.setText("");

        });

        primaryStage.setScene(loginScene);
        primaryStage.show();

        // Add New Post Page
        VBox addPostBox = new VBox(10);
        addPostBox.setPadding(new Insets(10));
        addPostBox.setAlignment(Pos.CENTER_LEFT);

        TextField postIdField = new TextField();
        postIdField.setPromptText("PostId");
        TextField locationField = new TextField();
        locationField.setPromptText("Location");
        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Description");
        TextArea requirementArea = new TextArea();
        requirementArea.setPromptText("Required qualifications for the post");
        DatePicker deadlinePicker = new DatePicker();
        deadlinePicker.setPromptText("Deadline");
        Button postButton = new Button("Post");
        addPostBox.getChildren().addAll(new Label("Enter details of the post."),postIdField, locationField, descriptionArea, requirementArea, deadlinePicker, postButton,homeA1);
        Scene addPostScene = new Scene(addPostBox, 400, 400);

        addPostButton.setOnAction(e -> {
            primaryStage.setScene(addPostScene);
            postIdField.setText("");
            locationField.setText("");
            descriptionArea.setText("");
            requirementArea.setText("");
            deadlinePicker.setValue(null);
        });

        postButton.setOnAction(e -> {
            addNewPost(Integer.parseInt(postIdField.getText()), locationField.getText(), descriptionArea.getText(), requirementArea.getText(), Date.valueOf(deadlinePicker.getValue()));
            showAlert(Alert.AlertType.INFORMATION, "Post Added Successfully");
            primaryStage.setScene(addPostScene);
            postIdField.setText("");
            locationField.setText("");
            descriptionArea.setText("");
            requirementArea.setText("");
            deadlinePicker.setValue(null);

        });


        // Update Post Page
        VBox updatePostBox = new VBox(10);
        updatePostBox.setPadding(new Insets(10));
        updatePostBox.setAlignment(Pos.CENTER_LEFT);
        TextField updatePostIdField = new TextField();
        updatePostIdField.setPromptText("PostId");
        TextField updateLocationField = new TextField();
        updateLocationField.setPromptText("Location");
        TextArea updateDescriptionArea = new TextArea();
        updateDescriptionArea.setPromptText("Description");
        TextArea updateRequirementArea = new TextArea();
        updateRequirementArea.setPromptText("Requirement");
        DatePicker updateDeadlinePicker = new DatePicker();
        updateDeadlinePicker.setPromptText("Deadline");
        Button updateButton = new Button("Update");
        Label l2 = new Label("OR Enter the Post ID to delete");
        TextField deletePostIdField = new TextField();
        deletePostIdField.setPromptText("PostId");
        Button deleteButton = new Button("Delete");
        updatePostBox.getChildren().addAll(new Label("Update or delete the post."),updatePostIdField, updateLocationField, updateDescriptionArea, updateRequirementArea, updateDeadlinePicker, updateButton, l2,deletePostIdField, deleteButton,homeA2);
        Scene updatePostScene = new Scene(updatePostBox, 400, 400);

        updatePostButton.setOnAction(e -> {
            primaryStage.setScene(updatePostScene);
            updatePostIdField.setText("");
            updateLocationField.setText("");
            updateDescriptionArea.setText("");
            updateRequirementArea.setText("");
            updateDeadlinePicker.setValue(null);
            deletePostIdField.setText("");
        });

        updateButton.setOnAction(e -> {
            updatePost(Integer.parseInt(updatePostIdField.getText()), updateLocationField.getText(), updateDescriptionArea.getText(), updateRequirementArea.getText(), Date.valueOf(updateDeadlinePicker.getValue()));
            showAlert(Alert.AlertType.INFORMATION, "Post Updated Successfully");
            primaryStage.setScene(updatePostScene);
            updatePostIdField.setText("");
            updateLocationField.setText("");
            updateDescriptionArea.setText("");
            updateRequirementArea.setText("");
            updateDeadlinePicker.setValue(null);

        });

        deleteButton.setOnAction(e -> {
            deletePost(Integer.parseInt(deletePostIdField.getText()));
            showAlert(Alert.AlertType.INFORMATION, "Post Deleted Successfully");
            deletePostIdField.setText("");
        });

        // See job applied students
        VBox seeJobAppliedStudents = new VBox(10);
        seeJobAppliedStudents.setPadding(new Insets(10));
        seeJobAppliedStudents.setAlignment(Pos.CENTER_LEFT);
        ListView<String> jobAppliedList = new ListView<>();
        Button homeA5 = new Button("Home");
        seeJobAppliedStudents.getChildren().addAll(new Label("Details of job applied students"),jobAppliedList,homeA5);
        Scene seeAppliedStudent = new Scene(seeJobAppliedStudents,400,400);

        seeAppliedStudentButton.setOnAction(e ->{
            jobAppliedList.getItems().clear();
            seeJobAppliedStudentsList(jobAppliedList);
            primaryStage.setScene(seeAppliedStudent);
        });
        homeA5.setOnAction(e -> primaryStage.setScene(adminScene));



        // Add Student Page
        VBox addStudentBox = new VBox(10);
        addStudentBox.setPadding(new Insets(10));
        addStudentBox.setAlignment(Pos.CENTER_LEFT);
        TextField studentIdField = new TextField();
        studentIdField.setPromptText("StudentId");
        TextField studentNameField = new TextField();
        studentNameField.setPromptText("StudentName");
        TextField jobIdField = new TextField();
        jobIdField.setPromptText("JobId");
        DatePicker joinDatePicker = new DatePicker();
        joinDatePicker.setPromptText("Join date");
        Button addStudentBtn = new Button("Add");
        addStudentBox.getChildren().addAll(new Label("Enter details of student"),studentIdField, studentNameField, jobIdField, joinDatePicker, addStudentBtn,homeA3);
        Scene addStudentScene = new Scene(addStudentBox, 400, 400);

        addStudentButton.setOnAction(e -> {
            primaryStage.setScene(addStudentScene);
            studentIdField.setText("");
            studentNameField.setText("");
            jobIdField.setText("");
            joinDatePicker.setValue(null);
        });

        addStudentBtn.setOnAction(e -> {
            addStudent(Integer.parseInt(studentIdField.getText()), studentNameField.getText(), Integer.parseInt(jobIdField.getText()), Date.valueOf(joinDatePicker.getValue()));
            showAlert(Alert.AlertType.INFORMATION, "Student Added Successfully");
            primaryStage.setScene(addStudentScene);
            studentIdField.setText("");
            studentNameField.setText("");
            jobIdField.setText("");
            joinDatePicker.setValue(null);
        });


        // Update Student Page
        VBox updateStudentBox = new VBox(10);
        updateStudentBox.setPadding(new Insets(10));
        updateStudentBox.setAlignment(Pos.CENTER_LEFT);
        TextField updateStudentIdField = new TextField();
        updateStudentIdField.setPromptText("StudentId");
        TextField updateStudentNameField = new TextField();
        updateStudentNameField.setPromptText("StudentName");
        TextField updateJobIdField = new TextField();
        updateJobIdField.setPromptText("JobId");
        DatePicker updateJoinDatePicker = new DatePicker();
        updateJoinDatePicker.setValue(null);
        Button updateStudentBtn = new Button("Update");
        TextField deleteStudentIdField = new TextField();
        Label l3 = new Label("OR Enter student id to delete");
        deleteStudentIdField.setPromptText("StudentId");
        Button deleteStudentBtn = new Button("Delete");
        updateStudentBox.getChildren().addAll(new Label("Update/Delete student details"),updateStudentIdField, updateStudentNameField, updateJobIdField, updateJoinDatePicker, updateStudentBtn,l3, deleteStudentIdField, deleteStudentBtn,homeA4);
        Scene updateStudentScene = new Scene(updateStudentBox, 400, 400);

        updateStudentButton.setOnAction(e -> {
            primaryStage.setScene(updateStudentScene);
            updateStudentIdField.setText("");
            updateStudentNameField.setText("");
            updateJobIdField.setText("");
            updateJoinDatePicker.setValue(null);
            deleteStudentIdField.setText("");
        });

        updateStudentBtn.setOnAction(e -> {
            updateStudent(Integer.parseInt(updateStudentIdField.getText()), updateStudentNameField.getText(), Integer.parseInt(updateJobIdField.getText()), Date.valueOf(updateJoinDatePicker.getValue()));
            showAlert(Alert.AlertType.INFORMATION, "Student Updated Successfully");
            primaryStage.setScene(updateStudentScene);
            updateStudentIdField.setText("");
            updateStudentNameField.setText("");
            updateJobIdField.setText("");
            deleteStudentIdField.setText("");
        });

        deleteStudentBtn.setOnAction(e -> {
            deleteStudent(Integer.parseInt(deleteStudentIdField.getText()));
            showAlert(Alert.AlertType.INFORMATION, "Student Deleted Successfully");
            updateJoinDatePicker.setValue(null);
        });


        // See Job Posts Page
        VBox seeJobPostsBox = new VBox(10);
        seeJobPostsBox.setPadding(new Insets(10));
        seeJobPostsBox.setAlignment(Pos.CENTER_LEFT);
        ListView<String> jobPostsList = new ListView<>();
        Button applyButton = new Button("Apply");

        seeJobPostsBox.getChildren().addAll(new Label("See available job posts"),jobPostsList, applyButton,homeS1);
        Scene seeJobPostsScene = new Scene(seeJobPostsBox, 400, 400);

        seeJobPostsButton.setOnAction(e -> {
            jobPostsList.getItems().clear();
            seeJobPosts(jobPostsList);
            primaryStage.setScene(seeJobPostsScene);
        });

        // Apply for Jobs Page
        VBox applyForJobsBox = new VBox(10);
        applyForJobsBox.setPadding(new Insets(10));
        applyForJobsBox.setAlignment(Pos.CENTER_LEFT);

        TextField applyPostIdField = new TextField();
        applyPostIdField.setPromptText("PostId");
        TextField applyStudentIdField = new TextField();
        applyStudentIdField.setPromptText("StudentId");
        TextField applyStudentNameField = new TextField();
        applyStudentNameField.setPromptText("StudentName");
        TextArea resumeArea = new TextArea();
        resumeArea.setPromptText("Resume");
        Button applyJobButton = new Button("Apply");

        applyForJobsBox.getChildren().addAll(new Label("Enter details to apply"),applyPostIdField, applyStudentIdField, applyStudentNameField, resumeArea, applyJobButton,homeS2);
        Scene applyForJobsScene = new Scene(applyForJobsBox, 400, 400);

        applyForJobsButton.setOnAction(e -> {
            primaryStage.setScene(applyForJobsScene);
            applyPostIdField.setText("");
            applyStudentIdField.setText("");
            applyStudentNameField.setText("");
            resumeArea.setText("");
        });
        applyButton.setOnAction(e -> {
            primaryStage.setScene(applyForJobsScene);
            applyPostIdField.setText("");
            applyStudentIdField.setText("");
            applyStudentNameField.setText("");
            resumeArea.setText("");
        });

        applyJobButton.setOnAction(e -> {
            applyForJob(Integer.parseInt(applyPostIdField.getText()), Integer.parseInt(applyStudentIdField.getText()), applyStudentNameField.getText(), resumeArea.getText());
            showAlert(Alert.AlertType.INFORMATION, "Applied Successfully");
            applyPostIdField.setText("");
            applyStudentIdField.setText("");
            applyStudentNameField.setText("");
            resumeArea.setText("");
        });

        // Results Page
        VBox resultsBox = new VBox(10);
        resultsBox.setPadding(new Insets(10));
        resultsBox.setAlignment(Pos.CENTER_LEFT);
        ListView<String> resultsList = new ListView<>();
        resultsBox.getChildren().addAll(new Label("Selected Students"),resultsList,homeS3);
        Scene resultsScene = new Scene(resultsBox, 400, 400);

        resultsButton.setOnAction(e -> {
            resultsList.getItems().clear();
            seeResults(resultsList);
            primaryStage.setScene(resultsScene);
        });
    }

    private void connectDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/placement_management", "root", "xxxxxxx");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean validateAdmin(String username, String password,String userType) {
        String query = "SELECT COUNT(*) FROM admin_credentials WHERE username = ? AND password = ? AND usertype = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, userType);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean validateStudent(String username, String password, String userType) {
        String query = "SELECT COUNT(*) FROM admin_credentials WHERE username = ? AND password = ? AND usertype = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, userType);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    private void addNewPost(int postId, String location, String description, String requirement, Date deadline) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO job_posts VALUES (?, ?, ?, ?, ?)");
            stmt.setInt(1, postId);
            stmt.setString(2, location);
            stmt.setString(3, description);
            stmt.setString(4, requirement);
            stmt.setDate(5, deadline);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updatePost(int postId, String location, String description, String requirement, Date deadline) {
        try {
            PreparedStatement stmt = connection.prepareStatement("UPDATE job_posts SET Location=?, Description=?, Requirement=?, Deadline=? WHERE PostId=?");
            stmt.setString(1, location);
            stmt.setString(2, description);
            stmt.setString(3, requirement);
            stmt.setDate(4, deadline);
            stmt.setInt(5, postId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deletePost(int postId) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM job_posts WHERE PostId=?");
            stmt.setInt(1, postId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addStudent(int studentId, String studentName, int jobId, Date joinDate) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO admin_selected_students VALUES (?, ?, ?, ?)");
            stmt.setInt(1, studentId);
            stmt.setString(2, studentName);
            stmt.setInt(3, jobId);
            stmt.setDate(4, joinDate);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateStudent(int studentId, String studentName, int jobId, Date joinDate) {
        try {
            PreparedStatement stmt = connection.prepareStatement("UPDATE admin_selected_students SET StudentName=?, JobId=?, JoinDate=? WHERE StudentId=?");
            stmt.setString(1, studentName);
            stmt.setInt(2, jobId);
            stmt.setDate(3, joinDate);
            stmt.setInt(4, studentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteStudent(int studentId) {
        try {
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM admin_selected_students WHERE StudentId=?");
            stmt.setInt(1, studentId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void seeJobPosts(ListView<String> jobPostsList) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM job_posts ORDER BY PostId");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String postDetails = "PostId: " + rs.getInt("PostId") + ", Location: " + rs.getString("Location") + ", Description: " + rs.getString("Description") + ", Requirement: " + rs.getString("Requirement") + ", Deadline: " + rs.getDate("Deadline");
                jobPostsList.getItems().add(postDetails);// Populate jobPostsList ListView here
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void applyForJob(int postId, int studentId, String studentName, String resume) {
        try {
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO applied_students VALUES (?, ?, ?, ?)");
            stmt.setInt(1, postId);
            stmt.setInt(2, studentId);
            stmt.setString(3, studentName);
            stmt.setString(4, resume);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void seeResults(ListView<String> resultsList) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM admin_selected_students ORDER BY StudentId");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String resultDetails = "PostId: " + rs.getInt("JobId") + ", StudentId: " + rs.getInt("StudentId") + ", StudentName: " + rs.getString("StudentName") + ", JoinDate: " + rs.getDate("JoinDate");
                resultsList.getItems().add(resultDetails);// Populate resultsList ListView here
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void seeJobAppliedStudentsList(ListView<String> jobAppliedList) {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM applied_students ORDER BY PostId");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String postDetails = "PostId: " + rs.getInt("PostId")  + ", StudentId: " + rs.getString("StudentId") + ", studentName: " + rs.getString("StudentName") + ", Resume: " + rs.getString("Resume");
                jobAppliedList.getItems().add(postDetails);// Populate jobPostsList ListView here
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

