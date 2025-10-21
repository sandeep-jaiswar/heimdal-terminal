package com.marketterminal.ui.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main controller for the application window with tabbed navigation.
 */
public class MainController {
  private static final Logger logger = LoggerFactory.getLogger(MainController.class);

  @FXML private BorderPane mainContainer;
  @FXML private TabPane mainTabPane;

  @FXML
  public void initialize() {
    logger.info("Initializing Main Controller");
    loadTabs();
  }

  private void loadTabs() {
    try {
      addTab("Dashboard", "/fxml/dashboard.fxml");
      addTab("Stock Chart", "/fxml/stockchart.fxml");
      addTab("Screener", "/fxml/screener.fxml");
      addTab("Portfolio", "/fxml/portfolio.fxml");
      addTab("Corporate Actions", "/fxml/corporateactions.fxml");

      logger.info("All tabs loaded successfully");
    } catch (Exception e) {
      logger.error("Failed to load tabs", e);
    }
  }

  private void addTab(String title, String fxmlPath) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
    Node content = loader.load();

    Tab tab = new Tab(title);
    tab.setContent(content);
    tab.setClosable(false);

    mainTabPane.getTabs().add(tab);
  }

  @FXML
  private void onExit() {
    logger.info("Exit requested");
    System.exit(0);
  }

  @FXML
  private void onAbout() {
    logger.info("About dialog requested");
    // About dialog will be implemented
  }
}
