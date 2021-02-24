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
    int active = 0;


    public MainViewController() {
        setupListeners();
        setupInputLogic();
    }

    public void setHostServices(HostServices hostServices) {
        this._services = hostServices;
    }

    private void setupListeners() {
        Platform.runLater(() -> {
            cbIntegerMode.setOnAction(evt -> {
                if (cbIntegerMode.selectedProperty().get()) {
                    cbRationalMode.selectedProperty().set(false);
                    cleanUpInput();
                } else {
                    cbRationalMode.selectedProperty().set(true);
                }
            });
            cbRationalMode.setOnAction(evt -> {
                if (cbRationalMode.selectedProperty().get()) {
                    cbIntegerMode.selectedProperty().set(false);
                } else {
                    cbIntegerMode.selectedProperty().set(true);
                    cleanUpInput();
                }
            });

            cbIntegerMode.requestFocus();
        });
    }

    private void setupInputLogic() {
        Platform.runLater(() -> {
            tfDec.textProperty().addListener((observableValue, s1, s2) -> {

                if (cbRationalMode.selectedProperty().get()) {
                    if (s2.chars().filter(ch -> ch == '.').count() > 1) {
                        tfDec.setText(s1);
                    }
                } else {
                    if (s2.contains(".")) {
                        tfDec.setText(s1);
                    }
                }
            });

        });
    }

    private void cleanUpInput() {
        String fieldText = tfAccess(true, null);
        if (fieldText.contains(".")) {
            fieldText = fieldText.chars().filter(i -> ((char)i != '.')).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
            tfAccess(false, fieldText);
        }
    }

    private String tfAccess (boolean get, String text) {
        switch (active) {
            case 0:
                if (get) {
                    return tfDec.getText();
                } else {
                    tfDec.setText(text);
                }
                break;
            case 1:
                if (get) {
                    return tfBin.getText();
                } else {
                    tfBin.setText(text);
                }
                break;
            case 2:
                if (get) {
                    return tfHex.getText();
                } else {
                    tfHex.setText(text);
                }
                break;
            case 3:
                if (get) {
                    return tfOct.getText();
                } else {
                    tfOct.setText(text);
                }
                break;
        }
        return null;
    }


    /* End */
}