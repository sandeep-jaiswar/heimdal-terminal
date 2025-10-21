package com.marketterminal.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for the Stock Chart panel.
 */
public class StockChartController {
  private static final Logger logger = LoggerFactory.getLogger(StockChartController.class);

  @FXML private TextField symbolField;
  @FXML private ComboBox<String> timeframeCombo;
  @FXML private LineChart<String, Number> priceChart;
  @FXML private VBox indicatorsPanel;
  @FXML private Label stockInfoLabel;

  @FXML
  public void initialize() {
    logger.info("Initializing Stock Chart Controller");
    setupTimeframes();
  }

  private void setupTimeframes() {
    timeframeCombo.getItems().addAll("1D", "1W", "1M", "3M", "6M", "1Y", "5Y", "Max");
    timeframeCombo.setValue("1Y");
  }

  @FXML
  private void onSearchStock() {
    String symbol = symbolField.getText();
    if (symbol != null && !symbol.isEmpty()) {
      logger.info("Loading chart for symbol: {}", symbol);
      loadStockChart(symbol);
    }
  }

  private void loadStockChart(String symbol) {
    stockInfoLabel.setText("Loading data for " + symbol + "...");
    // Chart loading will be implemented with actual data
  }

  @FXML
  private void onTimeframeChanged() {
    String timeframe = timeframeCombo.getValue();
    logger.info("Timeframe changed to: {}", timeframe);
    // Refresh chart with new timeframe
  }

  /** Adds an indicator overlay to the chart. */
  public void addIndicator(String indicatorType) {
    logger.info("Adding indicator: {}", indicatorType);
    // Indicator logic will be implemented
  }
}
