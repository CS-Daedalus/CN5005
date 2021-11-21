package com.gentree.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Objects;

public class MainController
{
    @FXML
    private Label welcomeText;

    @FXML
    public void initialize()
    {
        welcomeText.setText("Hello World!");
    }

    @FXML
    protected void importCsv(ActionEvent event)
    {
        System.out.println("Importing csv...");

        FileChooser fc = new FileChooser();
        File selectedFile;
        fc.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        selectedFile = fc.showOpenDialog(null);


        if (selectedFile != null)
        {
            System.out.println(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    protected void sortPersons(ActionEvent event)
            throws Exception
    {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(
                        Thread.currentThread().getContextClassLoader()
                                .getResource("view/people.fxml")));
        stage.setScene(new Scene(root));
        stage.setTitle("testing");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node)event.getSource()).getScene().getWindow());
        stage.show();
    }

    @FXML
    protected void findRelations(ActionEvent event)
            throws Exception
    {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(
                Objects.requireNonNull(
                        Thread.currentThread().getContextClassLoader()
                                .getResource("view/relations.fxml")));
        stage.setScene(new Scene(root));
        stage.setTitle("testing");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node)event.getSource()).getScene().getWindow());
        stage.show();
    }

    @FXML
    protected void exportToDot(ActionEvent event)
    {
        System.out.println("Exporting DOT...");

        FileChooser fileChooser = new FileChooser();
        String smaple = "contents of the dot file";

        System.out.println("The button openExportDot was activated");
        fileChooser.setInitialDirectory(new File("C:\\"));
        fileChooser.setInitialFileName("Dot_file");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Dot Files", "*.dot"));
        File file = fileChooser.showSaveDialog(new Stage());
        if(file != null)
        {
            saveSystem(file, smaple);
            System.out.println("Dot file created!");
            System.out.println("Dot filename: " + file.getName());
            System.out.println("Dot absolutePath: " + file.getAbsolutePath());
        }
        else
        {
            System.out.println("Aborded");
        }
    }

    @FXML
    protected void exportToImage(ActionEvent event)
    {
        System.out.println("Exporting image...");

        FileChooser fileChooser = new FileChooser();


        System.out.println("The button openExportDot was activated");
        fileChooser.setInitialDirectory(new File("C:\\"));
        fileChooser.setInitialFileName("Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.jpg"),
                new FileChooser.ExtensionFilter("Image Files", "*.png"),
                new FileChooser.ExtensionFilter("Image Files", "*.svg"));
        File file = fileChooser.showSaveDialog(new Stage());
        if(file != null)
        {
            System.out.println("Image file created!");
            System.out.println("Image filename: " + FilenameUtils.getBaseName(file.getName()));
            System.out.println("Image fileExtention: " + FilenameUtils.getExtension(file.getName()));
            System.out.println("Image absolutePath: " + file.getAbsolutePath());
        }
        else
        {
            System.out.println("Aborded");
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
