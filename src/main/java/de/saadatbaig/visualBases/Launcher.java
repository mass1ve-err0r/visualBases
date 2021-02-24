/*
 * Project: visualBases
 * Author:  Saadat M. Baig
 * Date:    24.02.21
 * License: BSD-3-Clause-Attribution
 */
package de.saadatbaig.visualBases;

import de.saadatbaig.visualBases.Controllers.MainViewController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.Optional;


public class Launcher extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("FXMLs/MainView.fxml"));
        Pane root = loader.load();
        ((MainViewController)loader.getController()).setHostServices(getHostServices());
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("visualBases");
        stage.setResizable(false);
        stage.setOnCloseRequest((evt) -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("visualBases - Confirmation");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you'd like to exit?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Platform.exit();
            } else {
                evt.consume();
            }
        });

        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


    /* End */
}