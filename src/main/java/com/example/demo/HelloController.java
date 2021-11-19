package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {

    @FXML
    private Label welcomeText;

    @FXML
    protected void openImport() throws IOException {

        // TODO: Get file, check file extension. Return file if .csv is true, else throw error
        FXMLLoader newLoader = new FXMLLoader(Main.class.getResource("dummy.fxml"));
        Scene newScene = new Scene(newLoader.load(), 240, 240);
        Stage newWindow = new Stage();
        newWindow.setTitle("Second Stage");
        newWindow.setScene(newScene);
        newWindow.show();
    }

    @FXML
    protected void openSort() throws IOException {

        FXMLLoader newLoader = new FXMLLoader(Main.class.getResource("dummy.fxml"));
        Scene newScene = new Scene(newLoader.load(), 240, 240);
        Stage newWindow = new Stage();
        newWindow.setTitle("Second Stage");
        newWindow.setScene(newScene);
        newWindow.show();
    }

    @FXML
    protected void openRelate() throws IOException {

        FXMLLoader newLoader = new FXMLLoader(Main.class.getResource("dummy.fxml"));
        Scene newScene = new Scene(newLoader.load(), 240, 240);
        Stage newWindow = new Stage();
        newWindow.setTitle("Second Stage");
        newWindow.setScene(newScene);
        newWindow.show();
    }

    @FXML
    protected void openExportDot() throws IOException {

        FXMLLoader newLoader = new FXMLLoader(Main.class.getResource("dummy.fxml"));
        Scene newScene = new Scene(newLoader.load(), 240, 240);
        Stage newWindow = new Stage();
        newWindow.setTitle("Second Stage");
        newWindow.setScene(newScene);
        newWindow.show();
    }

    @FXML
    protected void openExportJpg() throws IOException {

        FXMLLoader newLoader = new FXMLLoader(Main.class.getResource("dummy.fxml"));
        Scene newScene = new Scene(newLoader.load(), 240, 240);
        Stage newWindow = new Stage();
        newWindow.setTitle("Second Stage");
        newWindow.setScene(newScene);
        newWindow.show();
    }

}