package com.example.effortlogger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class PlanningPokerController implements Initializable {

    @FXML
    private ChoiceBox<String> ChoiceBox1;
    @FXML
    private ChoiceBox<String> ChoiceBox2;
    @FXML
    private TextField TextField1;
    @FXML
    private CheckBox CheckBox1;
    @FXML
    private Button submitButton;
    @FXML
    private Button nextPlayerButton;
    @FXML
    private Button playersDoneButton;
    @FXML
    private TextField playerNameTextField;
    @FXML
    private ListView<String> estimatesListView;
    @FXML
    private Label resultLabel;

    private String[] estimation = {"Story Points", "Story point estimate", "Comments", "Original Estimate", "Custom Fields"};
    private String[] deck = {
            "Fibonacci deck, containing 1, 2, 3, 5, 8, 13, 21, 34, 55 cards",
            "T-shirt deck, containing XXS, XS, S, M, L, XL, XXL cards",
            "Labeled T-Shirt deck, containing XXS=0.5,XS=1,S=2,M=3,L=5,XL=8,XXL=13",
            "Hours deck, containing 1h, 2h, 3h, 4h, 5h, 6h, 7h, 8h, 9h, 10h, 11h, 12h cards",
            "Custom Deck"
    };

    private List<String> estimates = new ArrayList<>();
    private List<PlayerEstimates> playersEstimates = new ArrayList<>();
    private PlayerEstimates currentPlayerEstimates;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ChoiceBox1.getItems().addAll(estimation);
        ChoiceBox2.getItems().addAll(deck);

        CheckBox1.setOnAction(event -> {
            String selectedDeck = ChoiceBox2.getValue();
            displayCards(selectedDeck);
        });

        ChoiceBox2.setOnAction(event -> {
            String selectedDeck = ChoiceBox2.getValue();
            displayCards(selectedDeck);
        });

        submitButton.setOnAction(this::submitEstimate);
        nextPlayerButton.setOnAction(this::nextPlayer);
        playersDoneButton.setOnAction(this::playersDone);
    }

    private void displayCards(String selectedDeck) {
        if (selectedDeck != null) {
            // Assuming the cards are separated by commas
            String[] cards = selectedDeck.split(", ");

            // Add "?" and coffee symbol to each card if CheckBox1 is selected
            StringBuilder result = new StringBuilder();
            for (String card : cards) {
                if (CheckBox1.isSelected()) {
                    result.append(card).append("?, ☕  "); // Add "?" and coffee symbol
                } else {
                    result.append(card).append(", ");
                }
            }

            // Display the result in the Label
            resultLabel.setText(result.toString());
        }
    }

    private void submitEstimate(ActionEvent event) {
        String selectedEstimate = ChoiceBox1.getValue();
        estimates.add(selectedEstimate);
        if (currentPlayerEstimates != null) {
            currentPlayerEstimates.addEstimate(selectedEstimate);
        }
        // Display the estimates in the ListView
        estimatesListView.getItems().setAll(estimates);
    }

    private void nextPlayer(ActionEvent event) {
        String playerName = playerNameTextField.getText();
        currentPlayerEstimates = new PlayerEstimates(playerName);
        playersEstimates.add(currentPlayerEstimates);
        resultLabel.setText("Next Player: " + playerName);
    }

    private void playersDone(ActionEvent event) {
        // Determine and display the best estimate
        String bestEstimate = determineBestEstimate();
        resultLabel.setText("Best Estimate: " + bestEstimate);
    }

    private String determineBestEstimate() {
        // Your logic to determine the best estimate goes here
        // For simplicity, let's just return the last submitted estimate
        if (!estimates.isEmpty()) {
            return estimates.get(estimates.size() - 1);
        } else {
            return "No estimates submitted";
        }
    }

    private static class PlayerEstimates {
        private String playerName;
        private List<String> estimates = new ArrayList<>();

        public PlayerEstimates(String playerName) {
            this.playerName = playerName;
        }

        public void addEstimate(String estimate) {
            estimates.add(estimate);
        }

        public String getPlayerName() {
            return playerName;
        }

        public List<String> getEstimates() {
            return estimates;
        }
    }
}