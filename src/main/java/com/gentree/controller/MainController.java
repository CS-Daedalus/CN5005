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

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

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

    // The path, where the csv file is located
    private String csvInputPath = "";

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
        // Store the path, where the csv file is located
        csvInputPath = inputFile.getAbsolutePath();

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
    protected void exportDot(@NotNull ActionEvent event) throws IOException {
        FileChooser output = exportFile(new FileChooser(), new String[]{".dot"});
        File outputFile = output.showSaveDialog(exportSelect.getScene().getWindow());
        // Take the csv file from the stored path and read it
        File csvFile = new File(csvInputPath);
        FileReader csvFileReader = new FileReader(csvFile);
        BufferedReader csvBufferedReader = new BufferedReader(csvFileReader);
        List<String> csvFileLines = new ArrayList<>();
        String temp_line = null;
        // This is the final content of the dot file
        String final_dot_output = "";
        // Read the lines of the csv file one by one
        temp_line = csvBufferedReader.readLine();
        while (temp_line != null){
            csvFileLines.add(temp_line);
            temp_line = csvBufferedReader.readLine();
        }
        csvBufferedReader.close();
        // Get the final content of the dot file
        final_dot_output = generateTreeDot(csvFileLines);
        if (outputFile != null)
        {
            saveSystem(outputFile, final_dot_output);
            System.out.println("Dot file created!");
            System.out.println("Dot filename: " + outputFile.getName());
            System.out.println("Dot absolutePath: " + outputFile.getAbsolutePath());
        }
        else
        {
            System.out.println("Aborted");
        }
    }

    // Generate the output of the dot file based on the lines of the csv file
    protected String generateTreeDot(List<String> csvLines) {
        // Generated output
        String dot_output = "";
        String temp_dot_output = "";
        // Array, which will contain the comma-separated values of each line of the csv
        String[] tempArray;
        // Map, in which the man is the key and the woman of the relationship is the value
        Map<String, String> husband = new HashMap<String, String>();
        // Map, in which the child is the key and its parent or parents the value
        Map<String, String> child = new HashMap<String, String>();
        // Array list, which will contain the in-relationship children
        ArrayList inRelChild = new ArrayList();
        dot_output += "digraph G{\nedge [dir=none];\nnode [shape=box];\ngraph [splines=ortho];\n";
        for (int i=0; i<csvLines.size(); i++) {
            tempArray = csvLines.get(i).split(",");
            // Check the value of the second comma-separated element of each line
            switch (tempArray[1].replaceAll("\\s", "").toLowerCase()) {
                case "man":
                    // Define the node of a man
                    dot_output += "\"" + tempArray[0] + "\"" + " [shape=box, regular=0, color=\"blue\", style=\"filled\", fillcolor=\"lightblue\"];\n";
                    break;
                case "woman":
                    // Define the node of a woman
                    dot_output += "\"" + tempArray[0] + "\"" + " [shape=oval, regular=0, color=\"red\", style=\"filled\", fillcolor=\"pink\"];\n";
                    break;
                case "husband":
                    // Fill the "husband" hash map
                    husband.put(tempArray[0].trim(), tempArray[2].trim());
                    break;
                case "father":
                    // Fill the "child" map and the "inRelChild" array list
                    if (child.containsKey(tempArray[2].trim())) {
                        child.put(tempArray[2].trim(), tempArray[0].replaceAll("\\s", "") + child.get(tempArray[2].trim()).replaceAll("\\s", ""));
                        inRelChild.add(tempArray[2].trim());
                    } else {
                        child.put(tempArray[2].trim(), tempArray[0].trim());
                    }
                    break;
                case "mother":
                    // Fill the "child" map and the "inRelChild" array list
                    if (child.containsKey(tempArray[2].trim())) {
                        child.put(tempArray[2].trim(), child.get(tempArray[2].trim()).replaceAll("\\s", "") + tempArray[0].replaceAll("\\s", ""));
                        inRelChild.add(tempArray[2].trim());
                    } else {
                        child.put(tempArray[2].trim(), tempArray[0].trim());
                    }
                    break;
            }
        }

        // The "husband" relationship is represented with a diamond-shaped node. The node is named as "ManNameWomanName". The couple is on the same rank.
        for (Map.Entry<String, String> set: husband.entrySet()) {
            dot_output += set.getKey().replaceAll("\\s", "") + set.getValue().replaceAll("\\s", "") + " [shape=diamond, label=\"\", height=0.25, width=0.25];\n";
            dot_output += "{rank=same; \"" + set.getKey() + "\" -> " + set.getKey().replaceAll("\\s", "") + set.getValue().replaceAll("\\s", "") + " -> \"" + set.getValue() + "\"};\n";
        }

        // Drawing the "child relationship". In-relationship children are drawn with blue arrows, whereas out-relationship ones with red.
        for (Map.Entry<String, String> set: child.entrySet()) {
            if (inRelChild.contains(set.getKey())) {
                dot_output += set.getValue() + " -> \"" + set.getKey() + "\" [color=\"blue\"];\n";
            } else {
                // The out-relationship children are defined at the end of the dot file.
                temp_dot_output += "\"" + set.getValue() + "\" -> \"" + set.getKey() + "\" [color=\"red\"];\n";
            }
        }

        dot_output += temp_dot_output + "}\n";
        return dot_output;
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
