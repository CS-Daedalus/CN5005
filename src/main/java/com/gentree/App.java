package com.gentree;

import com.gentree.common.Utils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

public class App
    extends Application
{
    @Override
    public void start(Stage stage)
        throws Exception
    {
        if (!(Utils.isWindows() || Utils.isMac()))
        {
            //Load Arial font for all *nix platforms
            Font.loadFont(
                Objects.requireNonNull(
                    Thread.currentThread().getContextClassLoader()
                          .getResource("fonts/Arial.ttf")).toExternalForm(), 12);
        }

        Parent root = FXMLLoader.load(
            Objects.requireNonNull(
                Thread.currentThread().getContextClassLoader()
                      .getResource("view/main.fxml")));

        stage.setTitle(Utils.TITLE);
        stage.setScene(new Scene(root, Utils.APP_WIDTH, Utils.APP_HEIGHT));
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
