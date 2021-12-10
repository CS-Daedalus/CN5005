package com.gentree.controller;

import com.gentree.service.FamilyService;
import com.gentree.service.RepositoriesService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RelationsController
{

    private final List<String> peopleList = new ArrayList<>();
    private final ObservableList<String> observableListA = FXCollections.observableArrayList();
    private final ObservableList<String> observableListB = FXCollections.observableArrayList();

    @FXML private ListView<String> listViewA;
    @FXML private ListView<String> listViewB;
    @FXML private Label outputLabel;
    @FXML private Button confirmButton;

    private boolean SelectA = false;
    private boolean SelectB = false;

    @FXML
    public void init()
    {
        if (peopleList.isEmpty())
        {
            peopleList.addAll(RepositoriesService.getInstance().getPersonRepository().getPersonsName());

            observableListA.setAll(peopleList);
            observableListB.setAll(peopleList);

            listViewA.setItems(observableListA);
            listViewB.setItems(observableListB);
        }
    }

    public void checkSelectionA()
    {
        SelectA = !getSelection(listViewA).isEmpty();
        unlockButton();
    }

    public void checkSelectionB()
    {

        SelectB = !getSelection(listViewB).isEmpty();
        unlockButton();
    }

    private void unlockButton()
    {
        confirmButton.setDisable(!(SelectA && SelectB));
    }

    public void findRelation()
    {
        outputLabel.setText(
            String.format(
                "You have selected the following names: %s and %s",
                getSelection(listViewA),
                getSelection(listViewB)));

        FamilyService.getInstance().findBond(getSelection(listViewA), getSelection(listViewB));
    }

    private String getSelection(@NotNull ListView<String> list)
    {
        return list.getSelectionModel().getSelectedItems().get(0);
    }
}
