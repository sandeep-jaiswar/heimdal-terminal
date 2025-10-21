package com.marketterminal.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for the Stock Screener panel.
 */
public class ScreenerController {
  private static final Logger logger = LoggerFactory.getLogger(ScreenerController.class);

  @FXML private VBox filtersPanel;
  @FXML private TableView<Object> resultsTable;
  @FXML private Button addFilterButton;
  @FXML private Button runScreenButton;
  @FXML private Label resultCountLabel;

  @FXML
  public void initialize() {
    logger.info("Initializing Screener Controller");
    setupResultsTable();
  }

  private void setupResultsTable() {
    // Table columns will be added dynamically
    resultCountLabel.setText("0 stocks found");
  }

  @FXML
  private void onAddFilter() {
    logger.info("Adding new filter");
    HBox filterRow = createFilterRow();
    filtersPanel.getChildren().add(filterRow);
  }

  private HBox createFilterRow() {
    HBox row = new HBox(10);
    ComboBox<String> fieldCombo = new ComboBox<>();
    fieldCombo.getItems().addAll("Price", "Volume", "RSI", "SMA20", "SMA50", "Change %");
    fieldCombo.setPromptText("Select Field");

    ComboBox<String> operatorCombo = new ComboBox<>();
    operatorCombo.getItems().addAll(">", "<", ">=", "<=", "=");
    operatorCombo.setPromptText("Operator");

    TextField valueField = new TextField();
    valueField.setPromptText("Value");

    Button removeButton = new Button("Remove");
    removeButton.setOnAction(e -> filtersPanel.getChildren().remove(row));

    row.getChildren().addAll(fieldCombo, operatorCombo, valueField, removeButton);
    return row;
  }

  @FXML
  private void onRunScreen() {
    logger.info("Running screener");
    // Screener logic will be implemented
    resultCountLabel.setText("Screening...");
  }

  /** Clears all filters. */
  public void clearFilters() {
    filtersPanel.getChildren().clear();
    resultsTable.getItems().clear();
    resultCountLabel.setText("0 stocks found");
  }
}
