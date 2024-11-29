import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class UniqueUserFormJavaFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Main layout
        VBox mainPane = new VBox(15);
        mainPane.setPadding(new Insets(20));

        // Form section
        GridPane formPane = new GridPane();
        formPane.setPadding(new Insets(10));
        formPane.setVgap(12);
        formPane.setHgap(15);

        // Full Name
        Label nameLabel = new Label("Enter Full Name:");
        TextField nameField = new TextField();
        formPane.add(nameLabel, 0, 0);
        formPane.add(nameField, 1, 0);

        // ID
        Label idLabel = new Label("User ID:");
        TextField idField = new TextField();
        formPane.add(idLabel, 0, 1);
        formPane.add(idField, 1, 1);

        // Gender
        Label genderLabel = new Label("Select Gender:");
        RadioButton maleButton = new RadioButton("Male");
        RadioButton femaleButton = new RadioButton("Female");
        RadioButton otherButton = new RadioButton("Other");
        ToggleGroup genderGroup = new ToggleGroup();
        maleButton.setToggleGroup(genderGroup);
        femaleButton.setToggleGroup(genderGroup);
        otherButton.setToggleGroup(genderGroup);
        HBox genderBox = new HBox(10, maleButton, femaleButton, otherButton);
        formPane.add(genderLabel, 0, 2);
        formPane.add(genderBox, 1, 2);

        // Province
        Label provinceLabel = new Label("Province/State:");
        TextField provinceField = new TextField();
        formPane.add(provinceLabel, 0, 3);
        formPane.add(provinceField, 1, 3);

        // Date of Birth
        Label dobLabel = new Label("Birth Date:");
        DatePicker dobPicker = new DatePicker();
        formPane.add(dobLabel, 0, 4);
        formPane.add(dobPicker, 1, 4);

        // Save button
        Button saveButton = new Button("Submit");
        formPane.add(saveButton, 1, 5);

        // Buttons Section (Below Form)
        HBox buttonBox = new HBox(10);
        Button newButton = new Button("New");
        Button deleteButton = new Button("Delete");
        Button restoreButton = new Button("Restore");
        Button findPrevButton = new Button("Find Prev");
        Button findNextButton = new Button("Find Next");
        Button closeButton = new Button("Exit");
        buttonBox.getChildren().addAll(newButton, deleteButton, restoreButton, findPrevButton, findNextButton, closeButton);
        buttonBox.setPadding(new Insets(10));

        // Adding form and button sections to main layout
        mainPane.getChildren().addAll(formPane, buttonBox);

        // Save Button Action
        saveButton.setOnAction(e -> saveData(nameField, idField, genderGroup, provinceField, dobPicker));

        // Close Button Action
        closeButton.setOnAction(e -> primaryStage.close());

        // Scene and Stage setup
        Scene scene = new Scene(mainPane, 600, 400);
        primaryStage.setTitle("User Registration Form");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void saveData(TextField nameField, TextField idField, ToggleGroup genderGroup, TextField provinceField, DatePicker dobPicker) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("user_data_v2.txt", true))) {
            String name = nameField.getText().trim();
            String id = idField.getText().trim();
            RadioButton selectedGender = (RadioButton) genderGroup.getSelectedToggle();
            String gender = selectedGender != null ? selectedGender.getText() : "Not Specified";
            String province = provinceField.getText().trim();
            String dob = dobPicker.getValue() != null ? dobPicker.getValue().toString() : "Not Specified";

            // Write data in a formatted way
            writer.write("Name: " + name + ", ID: " + id + ", Gender: " + gender + ", Province: " + province + ", DOB: " + dob);
            writer.newLine();

            // Confirmation alert
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Data Saved");
            alert.setHeaderText("Success!");
            alert.setContentText("User information saved successfully.");
            alert.showAndWait();
        } catch (IOException ex) {
            // Error alert
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Save Failed");
            alert.setContentText("Could not save user data: " + ex.getMessage());
            alert.showAndWait();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
