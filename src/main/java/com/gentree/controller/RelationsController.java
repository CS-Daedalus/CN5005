package com.gentree.controller;

import com.gentree.model.Relation;
import com.gentree.service.FamilyService;
import com.gentree.service.RepositoriesService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.jetbrains.annotations.NotNull;

public class RelationsController
{

    private final ObservableList<String> observableListA = FXCollections.observableArrayList();

    private int repositoryVersion;

    @FXML private ListView<String> listViewA;
    @FXML private ListView<String> listViewB;
    @FXML private Label outputLabel;
    @FXML private Button confirmButton;

    private boolean SelectA = false;
    private boolean SelectB = false;

    @FXML
    public void init()
    {
        RepositoriesService repositoriesService = RepositoriesService.getInstance();

        if (observableListA.isEmpty())
        {
            observableListA.addAll(repositoriesService.getPersonRepository().getPersonsName());

            listViewA.setItems(observableListA);
            listViewB.setItems(observableListA);
        }
        else if (repositoriesService.getVersion() != repositoryVersion)
        {
            observableListA.clear();
            init();
        }

        repositoryVersion = repositoriesService.getVersion();
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
                "%s is %s to %s",
                getSelection(listViewA),
                getBond(),
                getSelection(listViewB)));
    }

    private String getSelection(@NotNull ListView<String> list)
    {
        return list.getSelectionModel().getSelectedItems().get(0);
    }

    private String getBond()
    {
        Relation.Bond bond = FamilyService.getInstance().findBond(getSelection(listViewA), getSelection(listViewB));
        String degree = bond.toString().substring(bond.toString().length() - 1);

        switch (bond)
        {
            case GRANDSON1:
            case GRANDSON2:
            case GRANDSON3:
                return String.format("%snd degree grand son", degree);

            case GRANDDAUGHTER1:
            case GRANDDAUGHTER2:
            case GRANDDAUGHTER3:
                return String.format("%snd degree grand daughter", degree);

            case GRANDMOTHER1:
            case GRANDMOTHER2:
            case GRANDMOTHER3:
                return String.format("%snd degree grand mother", degree);

            case GRANDFATHER1:
            case GRANDFATHER2:
            case GRANDFATHER3:
                return String.format("%snd degree grand father", degree);

            case COUSIN1:
            case COUSIN2:
            case COUSIN3:
                return String.format("%snd degree cousin", degree);

            case AUNT1:
            case AUNT2:
            case AUNT3:
                return String.format("%snd degree aunt", degree);

            case UNCLE1:
            case UNCLE2:
            case UNCLE3:
                return String.format("%snd degree uncle", degree);

            case NIECE1:
            case NIECE2:
            case NIECE3:
                return String.format("%snd degree niece", degree);

            case NEPHEW1:
            case NEPHEW2:
            case NEPHEW3:
                return String.format("%snd degree nephew", degree);

            case LAW_GRANDSON1:
            case LAW_GRANDSON2:
            case LAW_GRANDSON3:
                return String.format("%snd degree grand son by law", degree);

            case LAW_GRANDDAUGHTER1:
            case LAW_GRANDDAUGHTER2:
            case LAW_GRANDDAUGHTER3:
                return String.format("%snd degree grand daughter by law", degree);

            case LAW_GRANDMOTHER1:
            case LAW_GRANDMOTHER2:
            case LAW_GRANDMOTHER3:
                return String.format("%snd degree grand mother by law", degree);

            case LAW_GRANDFATHER1:
            case LAW_GRANDFATHER2:
            case LAW_GRANDFATHER3:
                return String.format("%snd degree grand father by law", degree);

            case LAW_COUSIN1:
            case LAW_COUSIN2:
            case LAW_COUSIN3:
                return String.format("%snd degree cousin by law", degree);

            case LAW_AUNT1:
            case LAW_AUNT2:
            case LAW_AUNT3:
                return String.format("%snd degree aunt by law", degree);

            case LAW_UNCLE1:
            case LAW_UNCLE2:
            case LAW_UNCLE3:
                return String.format("%snd degree uncle by law", degree);

            case LAW_NIECE1:
            case LAW_NIECE2:
            case LAW_NIECE3:
                return String.format("%snd degree niece by law", degree);

            case LAW_NEPHEW1:
            case LAW_NEPHEW2:
            case LAW_NEPHEW3:
                return String.format("%snd degree nephew by law", degree);

            case LAW_MOTHER:
                return "mother by law";

            case LAW_FATHER:
                return "father by law";

            case LAW_BROTHER:
                return "brother by law";

            case LAW_SISTER:
                return "sister by law";

            case LAW_SON:
                return "son by law";

            case LAW_DAUGHTER:
                return "daughter by law";

            case NONE:
                return "not related";

            default:
                return bond.toString();
        }
    }
}
