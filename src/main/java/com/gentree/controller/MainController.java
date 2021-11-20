package com.gentree.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Objects;

public class MainController
{
    @FXML
    private Label welcomeText;

    @FXML
    public void initialize()
    {
        welcomeText.setText("Hello World!");
    }

    @FXML
    protected void importCsv(ActionEvent event)
    {
        System.out.println("Importing csv...");
    }

    @FXML
    protected void sortPersons(ActionEvent event)
            throws Exception
    {
        System.out.println("Sorting persons...");
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(
                        Thread.currentThread().getContextClassLoader()
                                .getResource("view/people.fxml")));
        stage.setScene(new Scene(root));
        stage.setTitle("testing");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node)event.getSource()).getScene().getWindow());
        stage.show();
    }

    @FXML
    protected void findRelations(ActionEvent event)
    {
        System.out.println("Find relations...");
    }

    @FXML
    protected void exportToDot(ActionEvent event)
    {
        System.out.println("Exporting DOT...");
    }

    @FXML
    protected void exportToImage(ActionEvent event)
    {
        System.out.println("Exporting image...");
    }

}
