package com.example.effortlogger;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;




public class LoggedInController implements Initializable {
    //Efort Console
    @FXML
    private Button btn_logout;
    @FXML
    private Label lbl_welcome1;
    @FXML
    private ChoiceBox<String> lifeCycleBoxConsole;
    @FXML
    private ChoiceBox<String> effortCategoryBoxConsole;
    @FXML
    private ChoiceBox<String> planBoxConsole;
    @FXML
    private ChoiceBox<String> projectBoxConsole;
    @FXML
    private Button btn_StartActivity;
    @FXML
    private Button btn_StopActivity;
    @FXML
    private TextField tf_OtherConsole;
    @FXML
    private Label txt_clockState;
    @FXML
    private Label timetest;
    @FXML
    private  Label txt_DateStart;

    private volatile boolean stop;

    //Effort Log Editor
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

    //below is for logs
    @FXML
    private TableView<Userinfo> table;
    @FXML
    private TableColumn<Userinfo, Integer> index;
    @FXML
    private TableColumn<Userinfo, String> data;
    @FXML
    private TableColumn<Userinfo, String> start;
    @FXML
    private TableColumn<Userinfo, String> stop1;
    @FXML
    private TableColumn<Userinfo, String> lifecycle;
    @FXML
    private TableColumn<Userinfo, String> cat;
    @FXML
    private TableColumn<Userinfo, String> deliverable;
    // Declare userinfoList at the class level
    private final ObservableList<Userinfo> userinfoList = FXCollections.observableArrayList();

    //below is for def table 1
    @FXML
    private TableView<SpecifyName> spn;
    @FXML
    private TableColumn<SpecifyName, String> name;
    @FXML
    private TableColumn<SpecifyName, Integer> one;
    @FXML
    private TableColumn<SpecifyName, Integer> two;
    @FXML
    private TableColumn<SpecifyName, Integer> three;
    @FXML
    private TableColumn<SpecifyName, Integer> four;
    @FXML
    private TableColumn<SpecifyName, Integer> five;
    @FXML
    private TableColumn<SpecifyName, Integer> six;
    // Declare userinfoList at the class level
    private final ObservableList<SpecifyName> SpecifyNameList = FXCollections.observableArrayList();

    //def table 2
    @FXML
    private TableView<LifecycleSteps> lifec;
    @FXML
    private TableColumn<LifecycleSteps, String> specifyLC;
    @FXML
    private TableColumn<LifecycleSteps, Integer> E;
    @FXML
    private TableColumn<LifecycleSteps, Integer> D;
    private final ObservableList<LifecycleSteps> LifecycleList = FXCollections.observableArrayList();

    //def table 3
    @FXML
    private TableView<specifyCat> specifye;
    @FXML
    private TableColumn<specifyCat, String> specifyEC;
    private final ObservableList<specifyCat> specifyCatsList = FXCollections.observableArrayList();

    //def table 4
    @FXML
    private TableView<plansHere> specifyplan;
    @FXML
    private TableColumn<plansHere, String> plans;
    private final ObservableList<plansHere> plansHereList = FXCollections.observableArrayList();

    //def table 5
    @FXML
    private TableView<deliverableHere> specifyd;
    @FXML
    private TableColumn<deliverableHere, String> deliver;
    private final ObservableList<deliverableHere> deliverableHereList = FXCollections.observableArrayList();



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
        //Effort Console
        projectBoxConsole.setItems(projectList);
        projectBoxConsole.setValue("Business Project");
        //Effort Log Editor
        projectBox.setItems(projectList);
        projectBox.setValue("Business Project");

        // Set default items for lifeCycleBox
        //Effort Console
        lifeCycleBoxConsole.setItems(lifeCycleList);
        lifeCycleBoxConsole.setValue("Planning");
        //Effort Log Editor
        lifeCycleBox.setItems(lifeCycleList);
        lifeCycleBox.setValue("Planning");

        // Set default items for effortCategoryBox
        //Effort Console
        effortCategoryBoxConsole.setItems(effortCategoryList);
        effortCategoryBoxConsole.setValue("Plans");
        //Effort Log Editor
        effortCategoryBox.setItems(effortCategoryList);
        effortCategoryBox.setValue("Plans");

        // Set default items for planBox
        //Effort Console
        planBoxConsole.setItems(plansList);
        planBoxConsole.setValue("Project Plan");
        //Effort Log Editor
        planBox.setItems(plansList);
        planBox.setValue("Project Plan");

        // Initially hide tf_Other
        tf_OtherConsole.setVisible(false);
        tf_Other.setVisible(false);


        // Listener for the effortCategoryBox selection change in Console and Editor
        effortCategoryBoxConsole.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updatePlanBoxItemsConsole();
            }
        });
        effortCategoryBox.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                updatePlanBoxItems();
            }
        });


        btn_StartActivity.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                //State of clock
                txt_clockState.setText("Clock Running!!");
                txt_clockState.setTextFill(Color.GREEN);
                Timenow();
                Date();



            }
        });
        btn_logout.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                DataBaseUtils.changeScene(actionEvent, "hello-view.fxml", null, "Log In!");
            }
        });

        btn_StopActivity.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                txt_clockState.setText("Clock STOPPED!!");
                txt_clockState.setTextFill(Color.RED);
                stop = true;

                // Get the selected values from the choice boxes WHILE THEY CAN BE USED IN NEXT STEPS
                String selectedLifeCycle = lifeCycleBoxConsole.getValue();
                String selectedEffortCategory = effortCategoryBoxConsole.getValue();
                String selectedPlan = planBoxConsole.getValue();
                System.out.println("life CycleBox " + selectedLifeCycle);
                System.out.println("life EfforCategory " + selectedEffortCategory);
                System.out.println("life Plan " + selectedPlan);
                System.out.println(timestoped);// SAM USE THIS VARIABLE
                System.out.println(datestoped);


                userinfoList.add(new Userinfo(1, datestoped, "00:00:00", timestoped, selectedLifeCycle, selectedEffortCategory, selectedPlan));

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

        // Set up TableView columns
        index.setCellValueFactory(new PropertyValueFactory<>("index"));
        data.setCellValueFactory(new PropertyValueFactory<>("data"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        stop1.setCellValueFactory(new PropertyValueFactory<>("stop1"));
        lifecycle.setCellValueFactory(new PropertyValueFactory<>("lifecycle"));
        cat.setCellValueFactory(new PropertyValueFactory<>("cat"));
        deliverable.setCellValueFactory(new PropertyValueFactory<>("deliverable"));



        // Set the ObservableList as the data source for the TableView
        table.setItems(userinfoList);

        // Set up TableView columns def table 1
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        one.setCellValueFactory(new PropertyValueFactory<>("one"));
        two.setCellValueFactory(new PropertyValueFactory<>("two"));
        three.setCellValueFactory(new PropertyValueFactory<>("three"));
        four.setCellValueFactory(new PropertyValueFactory<>("four"));
        five.setCellValueFactory(new PropertyValueFactory<>("five"));
        six.setCellValueFactory(new PropertyValueFactory<>("six"));

        SpecifyNameList.add(new SpecifyName("Business Project", 1, 5, 8, 10, 2, 6));
        SpecifyNameList.add(new SpecifyName("Development Project", 9, 4, 12, 0, 5, 9));
        spn.setItems(SpecifyNameList);

        specifyLC.setCellValueFactory(new PropertyValueFactory<>("specifyLC"));
        E.setCellValueFactory(new PropertyValueFactory<>("E"));
        D.setCellValueFactory(new PropertyValueFactory<>("D"));

        LifecycleList.add(new LifecycleSteps("Problem Understanding", 2, 1));
        LifecycleList.add(new LifecycleSteps("Conceptual Design Plan",1,3 ));
        LifecycleList.add(new LifecycleSteps("Requirements",2,1 ));
        LifecycleList.add(new LifecycleSteps("Conceptual Design",1,2 ));

        // Set the ObservableList as the data source for the TableView
        lifec.setItems(LifecycleList);

        specifyEC.setCellValueFactory(new PropertyValueFactory<>("specifyEC"));

        specifyCatsList.add(new specifyCat("1. Plans"));
        specifyCatsList.add(new specifyCat("2. Deliverables"));
        specifyCatsList.add(new specifyCat("3. Interuption"));
        specifyCatsList.add(new specifyCat("4. Defects"));
        specifyCatsList.add(new specifyCat("5. Other"));
        specifye.setItems(specifyCatsList);

        plans.setCellValueFactory(new PropertyValueFactory<>("plans"));

        plansHereList.add(new plansHere("1. Project Plan"));
        plansHereList.add(new plansHere("2. Riskmanagement Plan"));
        plansHereList.add(new plansHere("3. Conceptual Design Plan"));
        plansHereList.add(new plansHere("4. Detail Design Plan"));
        plansHereList.add(new plansHere("5. Implemenation Plan"));
        specifyplan.setItems(plansHereList);

        deliver.setCellValueFactory(new PropertyValueFactory<>("deliver"));

        deliverableHereList.add(new deliverableHere("1. Conceptual Design"));
        deliverableHereList.add(new deliverableHere("2. Detail Design"));
        deliverableHereList.add(new deliverableHere("3. Test Cases"));
        deliverableHereList.add(new deliverableHere("4. Solution"));
        deliverableHereList.add(new deliverableHere("5. Reflection"));
        deliverableHereList.add(new deliverableHere("6. Outline"));
        deliverableHereList.add(new deliverableHere("7. Draft"));
        deliverableHereList.add(new deliverableHere("8. Report"));
        deliverableHereList.add(new deliverableHere("9. User Defined"));
        deliverableHereList.add(new deliverableHere("10. other"));
        specifyd.setItems(deliverableHereList);

    }

    String timestoped = "";
    public void Timenow(){
        Thread thread = new Thread(() -> {
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
            while (!stop){
                try{
                    Thread.sleep(1000);
                }catch(Exception e){
                    System.out.println("e");
                }
                timestoped= sdf.format(new Date());
                Platform.runLater(() -> {
                    timetest.setText(timestoped);
                });
            }
        });
        thread.start();
    }
    String datestoped = "";
    public void Date(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        datestoped = sdf.format(new Date());
        txt_DateStart.setText(datestoped);
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
    private void updatePlanBoxItemsConsole() {
        // Update the items in the planBox based on the selected option in the effortCategoryBox
        String selectedEffortCategoryConsole = effortCategoryBoxConsole.getValue();
        switch (selectedEffortCategoryConsole) {
            case "Plans":
                //Console
                planBoxConsole.setItems(plansList);
                planBoxConsole.setValue("Project Plan");
                tf_OtherConsole.setVisible(false); // Hide tf_Other
                planBoxConsole.setDisable(false);
                break;
            case "Deliverables":
                //Console
                planBoxConsole.setItems(deliverablesList);
                planBoxConsole.setValue("Conceptual Design");
                tf_OtherConsole.setVisible(false); // Hide tf_Other
                planBoxConsole.setDisable(false);
                break;
            case "Interruptions":
                //Console
                planBoxConsole.setItems(interruptionsList);
                planBoxConsole.setValue("Break");
                tf_OtherConsole.setVisible(false); // Hide tf_Other
                planBoxConsole.setDisable(false);
                break;
            case "Defects":
                //Console
                planBoxConsole.setItems(defectsList);
                planBoxConsole.setValue("Break");
                tf_OtherConsole.setVisible(false); // Hide tf_Other
                planBoxConsole.setDisable(false);
                break;
            case "Other":
                //Console
                tf_OtherConsole.setVisible(true); // Show tf_Other
                planBoxConsole.setDisable(true);
                break;
            default:
                // Handle default case
                break;
        }
    }
    private void updatePlanBoxItems() {
        // Update the items in the planBox based on the selected option in the effortCategoryBox
        String selectedEffortCategory = effortCategoryBox.getValue();
        switch (selectedEffortCategory) {
            case "Plans":
                //Editor
                planBox.setItems(plansList);
                planBox.setValue("Project Plan");
                tf_Other.setVisible(false); // Hide tf_Other
                planBox.setDisable(false);
                break;
            case "Deliverables":
                //Editor
                planBox.setItems(deliverablesList);
                planBox.setValue("Conceptual Design");
                tf_Other.setVisible(false); // Hide tf_Other
                planBox.setDisable(false);
                break;
            case "Interruptions":
                //Editor
                planBox.setItems(interruptionsList);
                planBox.setValue("Break");
                tf_Other.setVisible(false); // Hide tf_Other
                planBox.setDisable(false);
                break;
            case "Defects":
                //Editor
                planBox.setItems(defectsList);
                planBox.setValue("Break");
                tf_Other.setVisible(false); // Hide tf_Other
                planBox.setDisable(false);
                break;
            case "Other":
                //Editor
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
        //Sam was here
    public static class Userinfo //this if for all Log!!
    {
        public int index;
        public String data;
        public String start;
        public String stop1;
        public String lifecycle;
        public String cat;
        public String deliverable;

        public Userinfo(int index, String data, String start, String stop1, String lifecycle, String cat, String devliverable)
        {
            //variables
            this.index = index;
            this.data = data;
            this.start = start;
            this.stop1 = stop1;
            this.lifecycle = lifecycle;
            this.cat = cat;
            this.deliverable = devliverable;
        }//end constructor
        // Getter methods
        public int getIndex() {
            return index;
        }

        public String getData() {
            return data;
        }

        public String getStart() {
            return start;
        }

        public String getStop1() {
            return stop1;
        }

        public String getLifecycle() {
            return lifecycle;
        }

        public String getCat() {
            return cat;
        }

        public String getDeliverable() {
            return deliverable;
        }
    }//end of public class userinfo

    public static class SpecifyName //this is for def Table #1
    {
        //variable
        public String name;
        public int one;
        public int two;
        public int three;
        public int four;
        public int five;
        public int six;

        public SpecifyName(String name, int one, int two, int three, int four, int five, int six)
        {
            this.name = name;
            this.one = one;
            this.two = two;
            this.three = three;
            this.four = four;
            this.five = five;
            this.six = six;
        }//end constructor
        public String getName() {
            return name;
        }
        public int getOne(){
            return one;
        }
        public int getTwo(){
            return two;
        }
        public int getThree(){
            return three;
        }
        public int getFour(){
            return four;
        }
        public int getFive(){
            return five;
        }
        public int getSix(){
            return six;
        }
    }

    public static class LifecycleSteps
    {
        public String specifyLC;
        public int E;
        public int D;
        //const.
        public LifecycleSteps(String specifyLC, int E, int D)
        {
            this.specifyLC = specifyLC;
            this.E = E;
            this.D = D;
        }
        public String getSpecifyLC() {
            return specifyLC;
        }
        public int getE(){
            return E;
        }
        public int getD(){
            return D;
        }
    }//end of lifecyclesteps

    public static class specifyCat{
        public String specifyEC;

        public specifyCat(String specifyEC){
            this.specifyEC = specifyEC;
        }
        public String getSpecifyEC() {
            return specifyEC;
        }
    }

    public static class plansHere{
        public String plans;

        public plansHere(String plans){
            this.plans = plans;
        }
        public String getPlans(){
            return plans;
        }
    }

    public static class deliverableHere{
        public String deliver;

        public deliverableHere(String deliver){
            this.deliver = deliver;
        }
        public String getDeliver(){
            return deliver;
        }
    }
}