package com.gentree.controller;

import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class PeopleController
{

    @FXML
    protected void saveSortedPeople() {

        System.out.println("Saving the txt file with the sorted people...");

        FileChooser fileChooser = new FileChooser();
        String sample = "contents of the txt file";

        System.out.println("The button saveSortedPeople was activated");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setInitialFileName("Sorted Names");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Text Document", "*.txt"));
        File file = fileChooser.showSaveDialog(new Stage());
        if(file != null)
        {
            saveSystem(file, sample);
            System.out.println("Text document successfully created!");
            System.out.println("File name: " + file.getName());
            System.out.println("File Path: " + file.getAbsolutePath());
        }
        else
        {
            System.out.println("Aborted");
        }

    }

    private void saveSystem(File file, String content)
    {
        try
        {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.write(content);
            printWriter.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }
}
