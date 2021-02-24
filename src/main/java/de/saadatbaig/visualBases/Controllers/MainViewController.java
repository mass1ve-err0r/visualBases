/*
 * Project: visualBases
 * Author:  Saadat M. Baig
 * Date:    24.02.21
 * License: BSD-3-Clause-Attribution
 */
package de.saadatbaig.visualBases.Controllers;

import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;


public class MainViewController {

    @FXML public AnchorPane apFoundation;
    @FXML public Label labelDec;
    @FXML public TextField tfDec;
    @FXML public Label labelBin;
    @FXML public TextField tfBin;
    @FXML public Label labelHex;
    @FXML public TextField tfHex;
    @FXML public Label labelOct;
    @FXML public TextField tfOct;
    @FXML public HBox hbCheckboxes;
    @FXML public CheckBox cbIntegerMode;
    @FXML public CheckBox cbRationalMode;
    HostServices _services;

    public MainViewController() {
        setupListeners();
    }

    public void setHostServices(HostServices hostServices) {
        this._services = hostServices;
    }

    private void setupListeners() {
        Platform.runLater(() -> {
            cbIntegerMode.setOnAction(evt -> {
                if (cbIntegerMode.selectedProperty().get()) {
                    cbRationalMode.selectedProperty().set(false);
                } else {
                    cbRationalMode.selectedProperty().set(true);
                }
            });
            cbRationalMode.setOnAction(evt -> {
                if (cbRationalMode.selectedProperty().get()) {
                    cbIntegerMode.selectedProperty().set(false);
                } else {
                    cbIntegerMode.selectedProperty().set(true);
                }
            });

            cbIntegerMode.requestFocus();
        });
    }


    /* End */
}