package com.gentree.controller;

import com.gentree.common.Util;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class PeopleController
{
    private final List<String> NameList = new ArrayList<>();

    private final ObservableList<String> finalNameList = FXCollections.observableArrayList();

    @FXML
    private ListView<String> NamesList;

    public void initialize()
    {
        NameList.add("PersonA");
        NameList.add("PersonB");
        NameList.add("PersonC");
        NameList.add("PersonD");

        finalNameList.setAll(NameList);
        NamesList.setItems(finalNameList);
    }

    @FXML
    protected void saveSortedPeople()
    {

        System.out.println("Saving the txt file with the sorted people...");

        FileChooser fileChooser = new FileChooser();

        System.out.println("The button saveSortedPeople was activated");
        fileChooser.setInitialDirectory(new File(Util.USER_HOME_DIR));
        fileChooser.setInitialFileName("Sorted Names");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Text Document", "*.txt"));
        File file = fileChooser.showSaveDialog(new Stage());

        if (file != null)
        {
            saveSystem(file, NameList);
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
