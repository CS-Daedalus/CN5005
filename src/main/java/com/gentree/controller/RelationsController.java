package com.gentree.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RelationsController
{

    @FXML
    private ListView listViewA;

    @FXML
    private ListView listViewB;

    @FXML
    private Label myLabel;

    @FXML
    private Button myButton;

    @FXML
    private AnchorPane anchorPane;

    private List<String> stringListA = new ArrayList<>(5);

    private List<String> stringListB = new ArrayList<>(5);

    private ObservableList observableListA = FXCollections.observableArrayList();

    private ObservableList observableListB = FXCollections.observableArrayList();

    public void setListView() {

        stringListA.add("PersonA");

        observableListA.setAll(stringListA);

        listViewA.setItems(observableListA);

        AnchorPane.setLeftAnchor(listViewA, 65.0);

        stringListB.add("PersonB");

        observableListB.setAll(stringListB);

        listViewB.setItems(observableListB);

        AnchorPane.setRightAnchor(listViewB, 30.0);

        AnchorPane.setTopAnchor(myLabel, 5.0);

        AnchorPane.setBottomAnchor(myButton, 5.0);

    }

    @FXML
    void initialize(){

        setListView();
    }

}
