package com.gentree.controller;

import com.gentree.common.Utils;
import com.gentree.service.RepositoriesService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class PeopleController
{
    private final ObservableList<String> finalNameList = FXCollections.observableArrayList();
    private int repositoryVersion;

    @FXML private ListView<String> NamesList;

    public void init()
    {
        RepositoriesService repositoriesService = RepositoriesService.getInstance();

        if (finalNameList.isEmpty())
        {
            finalNameList.addAll(repositoriesService.getPersonRepository().getPersonsName());
            NamesList.setItems(finalNameList);
        }
        else if (repositoriesService.getVersion() != repositoryVersion)
        {
            finalNameList.clear();
            init();
        }

        repositoryVersion = repositoriesService.getVersion();
    }

    @FXML
    protected void saveSortedPeople()
    {

        System.out.println("Saving the txt file with the sorted people...");

        FileChooser fileChooser = new FileChooser();

        System.out.println("The button saveSortedPeople was activated");
        fileChooser.setInitialDirectory(new File(Utils.USER_DOWNLOAD_DIR));
        fileChooser.setInitialFileName("people-sorted");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Text Document", "*.txt"));
        File file = fileChooser.showSaveDialog(NamesList.getScene().getWindow());

        if (file != null)
        {
            saveSystem(file, finalNameList);
            System.out.println("Text document successfully created!");
            System.out.println("File name: " + file.getName());
            System.out.println("File Path: " + file.getAbsolutePath());
        }
        else
        {
            System.out.println("Aborted");
        }
    }

    private void saveSystem(File file, @NotNull List<String> nList)
    {
        try
        {
            PrintWriter printWriter = new PrintWriter(file);

            for (String s : nList)
            {
                printWriter.println(s);
            }

            printWriter.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
}
