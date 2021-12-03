package com.gentree.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    private List<String> stringListA = new ArrayList<>(5);
    private List<String> stringListB = new ArrayList<>(5);

    private ObservableList observableListA = FXCollections.observableArrayList();
    private ObservableList observableListB = FXCollections.observableArrayList(stringListB);

    public void setListView() {

        stringListA.add("PersonA");
        observableListA.setAll(stringListA);
        listViewA.setItems(observableListA);

        stringListB.add("PersonB");
        observableListB.setAll(stringListB);
        listViewB.setItems(observableListB);
    }

    @FXML
    void initialize() { setListView(); }
}
