package com.gentree.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.util.ArrayList;
import java.util.List;

public class RelationsController
{

    @FXML
    private ListView listViewA;

    @FXML
    private ListView listViewB;

    @FXML
    private Label outputLabel;

    @FXML
    private Button confirmButton;

    private final List<String> peopleList = new ArrayList<>();

    private final ObservableList<String> observableListA = FXCollections.observableArrayList();
    private final ObservableList<String> observableListB = FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        peopleList.add("PersonA");
        peopleList.add("PersonD");
        peopleList.add("PersonC");
        peopleList.add("PersonB");

        observableListA.setAll(peopleList);
        observableListB.setAll(peopleList);

        listViewA.setItems(observableListA);
        listViewB.setItems(observableListB);
    }

    public void findRelation() { outputLabel.setText("You have selected the following names: "+getSelection(listViewA)+" and "+getSelection(listViewB)); }

    private String getSelection (ListView list) { return String.valueOf(list.getSelectionModel().getSelectedItems()); }
}
