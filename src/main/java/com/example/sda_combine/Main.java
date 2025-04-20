package com.example.sda_combine;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home-page.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 555, 535);
        primaryStage.setTitle("SMART SPHERE");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {

       launch();
    }
}
