package com.marketterminal.app;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main JavaFX application entry point for Heimdal Terminal.
 */
public class MainApp extends Application {
  private static final Logger logger = LoggerFactory.getLogger(MainApp.class);
  private static final String APP_TITLE = "Heimdal Terminal - Indian Stock Market Research";

  public static void main(String[] args) {
    logger.info("Starting Heimdal Terminal Application");
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) {
    try {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
      BorderPane root = loader.load();

      Scene scene = new Scene(root, 1400, 900);
      scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());

      primaryStage.setTitle(APP_TITLE);
      primaryStage.setScene(scene);
      primaryStage.setMaximized(true);
      primaryStage.show();

      logger.info("Application started successfully");
    } catch (IOException e) {
      logger.error("Failed to load main application window", e);
      throw new RuntimeException("Failed to start application", e);
    }
  }

  @Override
  public void stop() {
    logger.info("Shutting down Heimdal Terminal Application");
  }
}
