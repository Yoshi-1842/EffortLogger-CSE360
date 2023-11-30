package com.example.effortlogger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class LoggedInController implements Initializable {

    @FXML
    private Button btn_logout;
    @FXML
    private Label lbl_welcome1;
    @FXML
    private Button btn_SubmitReport;
    @FXML
    private Button btn_DisplayReports;
    @FXML
    private Button btn_UpdateEntry;
    @FXML
    private Button btn_ClearEffortLog;
    @FXML
    private Button btn_DeleteEntry;
    @FXML
    private TextField tf_Reports;
    @FXML
    private TextField tf_Other;
    @FXML
    private TextField tf_Date;
    @FXML
    private TextField tf_StartTime;
    @FXML
    private TextField tf_StopTime;
    @FXML
    private ChoiceBox<String> lifeCycleBox;
    @FXML
    private ChoiceBox<String> effortCategoryBox;
    @FXML
    private ChoiceBox<String> planBox; 
    @FXML
    private ChoiceBox<String> currentLogEntryBox;
    @FXML
    private ChoiceBox<String> projectBox;

    private List<String> reportsList = new ArrayList<>();

    // ListView for displaying reports
    private final ListView<String> listViewReports = new ListView<>();

    // Options for Defects
    ObservableList<String> projectList = FXCollections.observableArrayList("Business Project", "Development Project");
    // Options for Life Cycle
    ObservableList<String> lifeCycleList = FXCollections.observableArrayList("Planning", "Information Gathering", "Information Understanding",
            "Verifying", "Outlining", "Drafting", "Finalizing", "Team Meeting", "Coach Meeting", "Stakeholder Meeting");
    // Options for Effort Category
    ObservableList<String> effortCategoryList = FXCollections.observableArrayList("Plans", "Deliverables", "Interruptions", "Defects", "Other");

    // Options for Plans
    ObservableList<String> plansList = FXCollections.observableArrayList("Project Plan", "Risk Management Plan", "Conceptual Design Plan",
            "Detailed Design Plan", "Implementation Plan");
    // Options for Deliverables
    ObservableList<String> deliverablesList = FXCollections.observableArrayList("Conceptual Design", "Details Design", "Test Cases", "Solution",
            "Reflection", "Outline", "Draft", "Report", "User Defined", "Other");
    // Options for Interruptions
    ObservableList<String> interruptionsList = FXCollections.observableArrayList("Break", "Phone", "Teammate", "Visitor", "Other");
    // Options for Defects
    ObservableList<String> defectsList = FXCollections.observableArrayList("Break", "Phone", "Teammate", "Visitor", "Other");
 

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	
    	btn_ClearEffortLog.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Display a warning before clearing the currentLogEntryBox
                boolean confirmed = showConfirmationDialog("Clear Effort Log", "Are you sure you want to clear the effort log?");
                if (confirmed) {
                    currentLogEntryBox.setValue(null);
                }
            }
        });
    	
    	btn_DeleteEntry.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Get the selected entry from currentLogEntryBox
                String selectedEntry = currentLogEntryBox.getValue();
                if (selectedEntry != null) {
                    // Display a confirmation dialog before deleting the entry
                    boolean confirmed = showConfirmationDialog("Delete Entry", "Are you sure you want to delete this entry?");
                    if (confirmed) {
                        // Remove the selected entry from currentLogEntryBox
                        currentLogEntryBox.getItems().remove(selectedEntry);
                        // Optionally, you may want to clear other related fields as well
                        tf_Date.clear();
                        tf_StartTime.clear();
                        tf_StopTime.clear();
                    }
                }
            }
        });
    	
    	// Set default items for projectBox
        projectBox.setItems(projectList);
        projectBox.setValue("Business Project");
        
    	// Set default items for lifeCycleBox
        lifeCycleBox.setItems(lifeCycleList);
        lifeCycleBox.setValue("Planning");
        
        // Set default items for effortCategoryBox
        effortCategoryBox.setItems(effortCategoryList);
        effortCategoryBox.setValue("Plans");

        // Set default items for planBox
        planBox.setItems(plansList);
        planBox.setValue("Project Plan");

        // Initially hide tf_Other
        tf_Other.setVisible(false);

        // Listener for the effortCategoryBox selection change
        effortCategoryBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updatePlanBoxItems();
            }
        });

        btn_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DataBaseUtils.changeScene(actionEvent, "hello-view.fxml", null, "Log In!");
            }
        });

        btn_SubmitReport.setOnAction(new EventHandler<ActionEvent>() {
        	@Override
            public void handle(ActionEvent actionEvent) {
                String reportText = tf_Reports.getText();
                if (!reportText.trim().isEmpty()) {
                    reportsList.add(reportText);
                    tf_Reports.clear();
                    System.out.println("Report added: " + reportText);
                } else {
                    System.out.println("Report text is empty.");
                }
            }
        });
        // Update Button
        btn_UpdateEntry.setOnAction(new EventHandler<ActionEvent>() {
        	 @Override
             public void handle(ActionEvent actionEvent) {
                 // Validate date and time before updating the entry
                 String date = tf_Date.getText();
                 String startTime = tf_StartTime.getText();
                 String stopTime = tf_StopTime.getText();

                 if (isValidDate(date) && isValidTime(startTime) && isValidTime(stopTime)) {
                     // Build the log entry string
                     String logEntry = buildLogEntry();

                     // Set the log entry in the currentLogEntryBox
                     currentLogEntryBox.setValue(logEntry);
                 } else {
                     System.out.println("Invalid date or time. Please check your input fields.");
                 }
             }
        });

        btn_DisplayReports.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                // Display reports in a new scene with a ListView
                displayReports();
            }
        });
    }

    public void setUserInformation(String username) {
        lbl_welcome1.setText("Welcome to the Effort LoggerTool " + username + " !");
    }

    private void displayReports() {
        // Create a new stage (window) for displaying reports
        Stage reportsStage = new Stage();
        reportsStage.setTitle("Submitted Reports");

        // Set the stage as a modal dialog to block interaction with the main window
        reportsStage.initModality(Modality.APPLICATION_MODAL);
        reportsStage.initStyle(StageStyle.UTILITY);

        // Create an ObservableList to hold the reports
        ObservableList<String> observableReportsList = FXCollections.observableArrayList(reportsList);

        // Set the items in the ListView
        listViewReports.setItems(observableReportsList);

        // Create a VBox layout to hold the ListView
        VBox vBox = new VBox();
        vBox.setSpacing(10);

        // Add the ListView to the VBox
        vBox.getChildren().add(listViewReports);

        // Create a Scene and set it in the Stage
        Scene scene = new Scene(vBox, 400, 300);
        reportsStage.setScene(scene);

        // Show the stage
        reportsStage.show();
    }

    private void updatePlanBoxItems() {
        // Update the items in the planBox based on the selected option in the effortCategoryBox
        String selectedEffortCategory = effortCategoryBox.getValue();
        switch (selectedEffortCategory) {
            case "Plans":
                planBox.setItems(plansList);
                planBox.setValue("Project Plan");
                tf_Other.setVisible(false); // Hide tf_Other
                planBox.setDisable(false);
                break;
            case "Deliverables":
                planBox.setItems(deliverablesList);
                planBox.setValue("Conceptual Design");
                tf_Other.setVisible(false); // Hide tf_Other
                planBox.setDisable(false);
                break;
            case "Interruptions":
                planBox.setItems(interruptionsList);
                planBox.setValue("Break");
                tf_Other.setVisible(false); // Hide tf_Other
                planBox.setDisable(false);
                break;
            case "Defects":
                planBox.setItems(defectsList);
                planBox.setValue("Break");
                tf_Other.setVisible(false); // Hide tf_Other
                planBox.setDisable(false);
                break;
            case "Other":
                tf_Other.setVisible(true); // Show tf_Other
                planBox.setDisable(true);
                break;
            default:
                // Handle default case
                break;
        }
    }

    private boolean isValidDate(String date) {
        // Validate date format (yyyy-MM-dd)
        return date.matches("\\d{4}-\\d{2}-\\d{2}");
    }

    private boolean isValidTime(String time) {
        // Validate time format (HH:mm:ss)
        return time.matches("\\d{2}:\\d{2}:\\d{2}");
    }

    private String buildLogEntry() {
    	
        // Get the selected values from the choice boxes
        String selectedLifeCycle = lifeCycleBox.getValue();
        String selectedEffortCategory = effortCategoryBox.getValue();
        String selectedPlan = planBox.getValue();

        // Get the current date and time
        String currentDate = tf_Date.getText();
        String currentTime = tf_StartTime.getText() + "-" + tf_StopTime.getText();

        // Build the log entry string
        return currentDate + " (" + currentTime + ") " +
                selectedLifeCycle + "; " +
                selectedEffortCategory + " " +
                selectedPlan;
    }
    
    private boolean showConfirmationDialog(String title, String message) {
        // Display a confirmation dialog with the specified title and message
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == ButtonType.OK;
    }
}