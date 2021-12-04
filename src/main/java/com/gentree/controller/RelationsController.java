package com.gentree.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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

    private boolean SelectA = false;
    private boolean SelectB = false;

    @FXML
    public void initialize() {

        //demo samples names--------------------------------------------------------------------
        peopleList.add("Robert Baratheon");
        peopleList.add("Cersei Lannister");
        peopleList.add("Jofrey Baratheon");
        peopleList.add("Myrcella Baratheon");
        peopleList.add("Tommen Baratheon");
        peopleList.add("Gendry");
        peopleList.add("Cassana Estermont");
        peopleList.add("Margaret Tyrell");
        peopleList.add("Stannis Baratheon");
        peopleList.add("Selyse Baratheon");
        peopleList.add("Shireen Baratheon");
        //--------------------------------------------------------------------------------------

        observableListA.setAll(peopleList.stream().sorted().collect(Collectors.toList()));
        observableListB.setAll(peopleList.stream().sorted().collect(Collectors.toList()));

        listViewA.setItems(observableListA);
        listViewB.setItems(observableListB);
    }

    public void checkSelectionA () {

        SelectA = (!Objects.equals(getSelection(listViewA), "[]"));
        unlockButton();
    }

    public void checkSelectionB () {

        SelectB = (!Objects.equals(getSelection(listViewB), "[]"));
        unlockButton();
    }

    private void unlockButton () { confirmButton.setDisable(!(SelectA && SelectB)); }

    public void findRelation() { outputLabel.setText("You have selected the following names: "+getSelection(listViewA)+" and "+getSelection(listViewB)); }

    private String getSelection (ListView list) { return String.valueOf(list.getSelectionModel().getSelectedItems()); }
}
