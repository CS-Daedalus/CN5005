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
    protected void saveSortedPeople(javafx.event.ActionEvent actionEvent) {

        System.out.println("Saving the txt file with the sorted people...");

        FileChooser fileChooser = new FileChooser();
        String sample = "contents of the txt file";

        System.out.println("The button saveSortedPeople was activated");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.setInitialFileName("Sorted_people_txt_file");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Txt Files", "*.txt"));
        File file = fileChooser.showSaveDialog(new Stage());
        if(file != null)
        {
            saveSystem(file, sample);
            System.out.println("Txt file created!");
            System.out.println("Txt filename: " + file.getName());
            System.out.println("Txt absolutePath: " + file.getAbsolutePath());
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
