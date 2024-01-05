package ru.vsu.cs;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;

import java.io.IOException;

public class Main extends Application{
    static FXMLLoader loader;
    static Scene scene;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/mainScene.fxml"));
        scene = new Scene(loader.load(), 800, 600);
        stage.setTitle("Simple App");
        stage.setScene(scene);
        stage.show();
    }
}