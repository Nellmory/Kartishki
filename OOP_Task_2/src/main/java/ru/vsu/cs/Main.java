package ru.vsu.cs;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
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
        loader.setLocation(Main.class.getResource("/start.fxml"));
        //loader.setLocation(Main.class.getClassLoader().getResource("/start.fxml"));
        scene = new Scene(loader.load(), 800, 600);
        stage.setTitle("Card Games");
        stage.setScene(scene);
        stage.show();
    }
}