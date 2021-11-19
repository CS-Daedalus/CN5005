package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class createWindow extends Application {

    @Override
    public void start(Stage mainStage) throws IOException {

        FXMLLoader mainLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Scene mainScene = new Scene(mainLoader.load(), 320, 240);

        mainStage.setTitle("Example GUI");
        mainStage.setScene(mainScene);

        mainStage.show();

    }

}
