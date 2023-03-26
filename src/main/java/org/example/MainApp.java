package org.example;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
        @Override
        public void start(Stage stage) throws Exception {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/sample.fxml"));
            System.out.println(getClass().getResource("/sample.fxml"));
            Parent root = fxmlLoader.load();
          //  Parent root = FXMLLoader.load(getClass().getResource("/resources.sample.fxml"));

            Scene scene = new Scene(root);
            stage.setTitle("Painter"); // displayed in window's title bar
            stage.setScene(scene);
            stage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }

}