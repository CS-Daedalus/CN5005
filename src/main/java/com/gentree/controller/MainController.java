package com.gentree.controller;

import com.gentree.common.Util;
import com.gentree.model.Person;
import com.gentree.model.Relation;
import com.gentree.service.CsvService;
import com.gentree.service.FamilyService;
import com.gentree.service.RepositoriesService;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Deque;

public class MainController
{
    @FXML private Button testButtonA;
    @FXML private Button testButtonB;
    @FXML private Button testButtonC;
    @FXML private Button importSelect;
    @FXML private MenuButton exportSelect;
    @FXML private TabPane tabPane;
    /*
    Call point for the tab pane's tabs. For every new tab,
    you must include the fx:id of the Tab and its controller
    with this specific format. For the tab "%ADD_NAME%":

        @FXML private Tab %ADD_NAME%Tab;
        @FXML private %ADD_NAME%Controller %ADD_NAME%PageController;
    */
    @FXML private Tab peopleTab;
    @FXML private PeopleController peoplePageController;
    @FXML private Tab relationsTab;
    @FXML private RelationsController relationsPageController;
    /*
    Ignore the unused Controllers, they are actually used.
    */

    public void initialize()
    {
        /*
        Tab selector, for every new tab, add an "else if" statement.
        Can definitely be improved.
         */
        tabPane.getSelectionModel().selectedItemProperty().addListener(
            (ObservableValue<? extends Tab> observable,
                Tab oldValue, Tab newValue) -> {
                    if (newValue == peopleTab) peoplePageController.init();
                    else if (newValue == relationsTab) relationsPageController.init();
                    });
    }

    @FXML
    /*
    Method for creating FileChooser objects destined for importing files into the program in one line.
    Requires a NEW FileChooser to build upon and the file extensions in the format below
    To use, declare a new variable of same type and add:
        = importFile(new FileChooser(), new String[]{".%TYPE1%", ".%TYPE2%", ...})
     */
    public FileChooser importFile(FileChooser var, String[] extension)
    {
        var.setInitialDirectory(new File(Util.USER_HOME_DIR));
        for (String str : extension)
        {
            var.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(
                String.format("%s File", str),
                String.format("*%s", str)));
        }
        return var;
    }

    @FXML
    /*
    Method for creating FileChooser objects destined for exporting files out of the program in one line.
    Requires a NEW FileChooser to build upon and the file extensions in the format below
    To use, declare a new variable of same type and add:
        = exportFile(new FileChooser(), new String[]{".%TYPE1%", ".%TYPE2%", ...})
     */
    public FileChooser exportFile(FileChooser var, String[] extension)
    {
        var.setInitialFileName("LoremIpsum");
        var.setInitialDirectory(new File(Util.USER_HOME_DIR));
        for (String str : extension)
        {
            var.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(
                String.format("%s File", str),
                String.format("*%s", str)));
        }
        return var;
    }

    @FXML
    // Import CSV button function.
    protected void importCsv(@NotNull ActionEvent event)
    {
        FileChooser input = importFile(new FileChooser(), new String[]{".csv"});
        File inputFile = input.showOpenDialog(((Node) event.getSource()).getScene().getWindow());

        if (inputFile != null)
        {
            try
            {
                ImmutablePair<Deque<Person.Tuple>, Deque<Relation.Tuple>> fileData =  CsvService
                    .getInstance().readFile(inputFile.getAbsolutePath());

                RepositoriesService.getInstance().feed(fileData.getLeft(), fileData.getRight());

                FamilyService.getInstance().populateFamilyTree();

                setAccess(true);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @FXML
    // Export Image button function
    protected void exportImage(@NotNull ActionEvent event)
    {
        FileChooser output = exportFile(new FileChooser(), new String[]{".jpg", ".png", ".svg"});
        File outputFile = output.showSaveDialog(exportSelect.getScene().getWindow());

        if (outputFile != null)
        {
            System.out.println("Image file created!");
            System.out.println("Image filename: " + FilenameUtils.getBaseName(outputFile.getName()));
            System.out.println("Image file extension: " + FilenameUtils.getExtension(outputFile.getName()));
            System.out.println("Image absolute path: " + outputFile.getAbsolutePath());
        }
        else
        {
            System.out.println("Aborted");
        }
    }

    @FXML
    // Export DOT button function
    protected void exportDot(@NotNull ActionEvent event)
    {
        FileChooser output = exportFile(new FileChooser(), new String[]{".dot"});
        File outputFile = output.showSaveDialog(exportSelect.getScene().getWindow());
        String sample = "contents of the dot file";

        if (outputFile != null)
        {
            saveSystem(outputFile, sample);
            System.out.println("Dot file created!");
            System.out.println("Dot filename: " + outputFile.getName());
            System.out.println("Dot absolutePath: " + outputFile.getAbsolutePath());
        }
        else
        {
            System.out.println("Aborted");
        }
    }

    @FXML
    // Export CSV button function
    protected void exportCsv(@NotNull ActionEvent event)
    {
        FileChooser output = exportFile(new FileChooser(), new String[]{".csv"});
        File outputFile = output.showSaveDialog(exportSelect.getScene().getWindow());
        String sample = "Lorem ipsum; dolor; sit";

        if (outputFile != null)
        {
            saveSystem(outputFile, sample);
            System.out.println("CSV file created!");
            System.out.println("CSV filename: " + outputFile.getName());
            System.out.println("CSV absolutePath: " + outputFile.getAbsolutePath());
            /*
            Uncomment the line below when the CSV unloader is functional.
            setAccess(false);
             */
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

    /*
    Method controlling access to GUI elements with given bool value:
        set TRUE for unlocking the GUI
            or
        set FALSE for locking the GUI
     */
    private void setAccess(boolean b)
    {
        testButtonA.setDisable(!b);
        testButtonB.setDisable(!b);
        testButtonC.setDisable(!b);
        exportSelect.setDisable(!b);
        tabPane.setDisable(!b);
        /*
        Flips given status to disable new CSV entry until a CSV export has been done.
        Uncomment the line below when the CSV unloader is functional.
        importSelect.setDisable(b);
         */
    }
}
