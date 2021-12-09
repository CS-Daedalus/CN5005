package com.gentree.controller;

import com.gentree.common.Util;
import com.gentree.model.Person;
import com.gentree.model.Relation;
import com.gentree.service.CsvService;
import com.gentree.service.FamilyService;
import com.gentree.service.RepositoriesService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Deque;
import java.util.Objects;

public class MainController
{

    @FXML
    private Label welcomeText;

    @FXML
    private Button sortButton;

    @FXML
    private Button findButton;

    @FXML
    private Button exportDotButton;

    @FXML
    private Button exportImageButton;

    private boolean state = false;

    @FXML
    public void initialize()
    {
        welcomeText.setText("Hello World!");
    }

    @FXML
    protected void importCsv(@NotNull ActionEvent event)
    {
        unlockButtons();
        state = false;
        System.out.println("Importing csv...");
        FileChooser fc = new FileChooser();
        File        selectedFile;
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        selectedFile = fc.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        if (selectedFile != null)
        {
            try
            {
                ImmutablePair<Deque<Person.Tuple>, Deque<Relation.Tuple>> fileData =  CsvService
                    .getInstance().readFile(selectedFile.getAbsolutePath());

                RepositoriesService.getInstance().feed(fileData.getLeft(), fileData.getRight());

                FamilyService.getInstance().populateFamilyTree();
                state = true;
                unlockButtons();

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }


            System.out.println(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    protected void sortPersons(@NotNull ActionEvent event)
        throws Exception
    {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(
            Objects.requireNonNull(Thread.currentThread()
                                         .getContextClassLoader()
                                         .getResource("view/people.fxml")));
        stage.setScene(new Scene(root));
        stage.setTitle("testing");
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) event.getSource()).getScene().getWindow());
        stage.show();
    }

    @FXML
    protected void findRelations(@NotNull ActionEvent event)
        throws Exception
    {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(
            Objects.requireNonNull(Thread.currentThread()
                                         .getContextClassLoader()
                                         .getResource("view/relations.fxml")));
        stage.setScene(new Scene(root));
        stage.setTitle("testing");
        stage.setResizable(false);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(((Node) event.getSource()).getScene().getWindow());
        stage.show();
    }

    @FXML
    protected void exportToDot(@NotNull ActionEvent event)
    {
        System.out.println("Exporting DOT...");

        FileChooser fileChooser = new FileChooser();
        String      sample      = "contents of the dot file";

        System.out.println("The button openExportDot was activated");
        fileChooser.setInitialDirectory(new File(Util.USER_HOME_DIR));
        fileChooser.setInitialFileName("Dot_file");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Dot Files", "*.dot"));
        File file = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());

        if (file != null)
        {
            saveSystem(file, sample);
            System.out.println("Dot file created!");
            System.out.println("Dot filename: " + file.getName());
            System.out.println("Dot absolutePath: " + file.getAbsolutePath());
        }
        else
        {
            System.out.println("Aborted");
        }
    }

    @FXML
    protected void exportToImage(@NotNull ActionEvent event)
    {
        System.out.println("Exporting image...");

        FileChooser fileChooser = new FileChooser();

        System.out.println("The button openExportDot was activated");
        fileChooser.setInitialDirectory(new File(Util.USER_HOME_DIR));
        fileChooser.setInitialFileName("Image");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Image Files", "*.jpg"),
            new FileChooser.ExtensionFilter("Image Files", "*.png"),
            new FileChooser.ExtensionFilter("Image Files", "*.svg"));
        File file = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());
        if (file != null)
        {
            System.out.println("Image file created!");
            System.out.println("Image filename: " + FilenameUtils.getBaseName(file.getName()));
            System.out.println("Image file extension: " + FilenameUtils.getExtension(file.getName()));
            System.out.println("Image absolute path: " + file.getAbsolutePath());
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

    private void unlockButtons()
    {
        sortButton.setDisable(!state);
        findButton.setDisable(!state);
        exportDotButton.setDisable(!state);
        exportImageButton.setDisable(!state);
        state = false;

    }

}
