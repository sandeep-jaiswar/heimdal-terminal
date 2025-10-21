package com.marketterminal.ui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for the Market Dashboard panel.
 */
public class DashboardController {
  private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

  @FXML private VBox dashboardContainer;
  @FXML private Label nifty50Label;
  @FXML private Label bankNiftyLabel;
  @FXML private Label sensexLabel;
  @FXML private VBox gainersContainer;
  @FXML private VBox losersContainer;

  @FXML
  public void initialize() {
    logger.info("Initializing Dashboard Controller");
    loadMarketData();
  }

  private void loadMarketData() {
    // Placeholder for market data loading
    nifty50Label.setText("NIFTY 50: Loading...");
    bankNiftyLabel.setText("BANK NIFTY: Loading...");
    sensexLabel.setText("SENSEX: Loading...");
  }

  /** Refreshes the dashboard data. */
  public void refresh() {
    logger.info("Refreshing dashboard");
    loadMarketData();
  }
}
