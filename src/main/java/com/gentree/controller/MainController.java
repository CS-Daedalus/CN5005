package com.gentree.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MainController
{
    @FXML
    private Label welcomeText;

    public MainController(Label welcomeText)
    {
        this.welcomeText = welcomeText;
    }

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
    {
        System.out.println("Sorting persons...");
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
