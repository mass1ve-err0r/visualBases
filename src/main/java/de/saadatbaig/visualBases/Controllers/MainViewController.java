/*
 * Project: visualBases
 * Author:  Saadat M. Baig
 * Date:    24.02.21
 * License: BSD-3-Clause-Attribution
 */
package de.saadatbaig.visualBases.Controllers;

import de.saadatbaig.visualBases.Utils.ConverterIR;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
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
    @FXML public Label labelBrand;

    ConverterIR _converter;
    HostServices _services;
    int _active = 0;


    public MainViewController() {
        _converter = new ConverterIR();
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
                    for (int i = 0; i < 4; i++) {
                        _active = i;
                        cleanUpInput();
                    }
                } else {
                    cbRationalMode.selectedProperty().set(true);
                }
            });
            cbRationalMode.setOnAction(evt -> {
                if (cbRationalMode.selectedProperty().get()) {
                    cbIntegerMode.selectedProperty().set(false);
                } else {
                    cbIntegerMode.selectedProperty().set(true);
                    for (int i = 0; i < 4; i++) {
                        _active = i;
                        cleanUpInput();
                    }
                }
            });

            labelBrand.setOnMouseClicked(evt -> {
                _services.showDocument("https://github.com/mass1ve-err0r/visualBases");
            });
            labelBrand.setTooltip(new Tooltip("Click me to view the source code! :-)"));

            cbIntegerMode.requestFocus();
        });
    }

    private void setupInputLogic() {
        Platform.runLater(() -> {
            tfDec.textProperty().addListener((observableValue, s1, s2) -> {
                _active = 0;
                if (!s2.matches("[\\d.]+")) {
                    tfDec.setText(s2.replaceAll("[^\\d.]", ""));
                }
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
            tfDec.setOnAction(evt -> {
                String s_bin = "null", s_hex = "null", s_oct = "null";
                if (tfDec.getText().isEmpty()) { return; }
                if (cbIntegerMode.selectedProperty().get()) {
                    s_bin = _converter.convertFromDecimalTo(tfDec.getText(), 2);
                    s_hex = _converter.convertFromDecimalTo(tfDec.getText(), 16);
                    s_oct = _converter.convertFromDecimalTo(tfDec.getText(), 8);
                } else {
                    s_bin = _converter.convertFromDecimalToBinR(tfDec.getText());
                    s_hex = _converter.convertFromDecimalToHexR(tfDec.getText());
                    s_oct = _converter.convertFromDecimalToOctR(tfDec.getText());
                }
                textfieldRW(1, false, s_bin);
                textfieldRW(2, false, s_hex);
                textfieldRW(3, false, s_oct);
            });

            tfBin.textProperty().addListener(((observableValue, s1, s2) -> {
                _active = 1;
                if (!s2.matches("[01.]+")) {
                    tfBin.setText(s2.replaceAll("[^01.]", ""));
                }
                if (cbRationalMode.selectedProperty().get()) {
                    if (s2.chars().filter(ch -> ch == '.').count() > 1) {
                        tfBin.setText(s1);
                    }
                } else {
                    if (s2.contains(".")) {
                        tfBin.setText(s1);
                    }
                }
            }));
            tfBin.setOnAction(evt -> {
                String s_dec = "null", s_hex = "null", s_oct = "null";
                if (tfBin.getText().isEmpty()) { return; }
                if (cbIntegerMode.selectedProperty().get()) {
                    s_dec = _converter.convertFromBinTo(tfBin.getText(), 10);
                    s_hex = _converter.convertFromBinTo(tfBin.getText(), 16);
                    s_oct = _converter.convertFromBinTo(tfBin.getText(), 8);
                } else {
                    s_dec = _converter.convertBinToDecimalR(tfBin.getText());
                    s_hex = _converter.convertFromDecimalToHexR(s_dec);
                    s_oct = _converter.convertFromDecimalToOctR(s_dec);
                }
                textfieldRW(0, false, s_dec);
                textfieldRW(2, false, s_hex);
                textfieldRW(3, false, s_oct);
            });

            tfHex.textProperty().addListener(((observableValue, s1, s2) -> {
                _active = 2;
                if (!s2.matches("[0-9a-f.]+")) {
                    tfHex.setText(s2.replaceAll("[^0-9a-f.]", ""));
                }
                if (cbRationalMode.selectedProperty().get()) {
                    if (s2.chars().filter(ch -> ch == '.').count() > 1) {
                        tfHex.setText(s1);
                    }
                } else {
                    if (s2.contains(".")) {
                        tfHex.setText(s1);
                    }
                }
            }));
            tfHex.setOnAction(evt -> {
                String s_dec = "null", s_bin = "null", s_oct = "null";
                if (tfHex.getText().isEmpty()) { return; }
                if (cbIntegerMode.selectedProperty().get()) {
                    s_dec = _converter.convertFromHexTo(tfHex.getText(), 10);
                    s_bin = _converter.convertFromHexTo(tfHex.getText(), 2);
                    s_oct = _converter.convertFromHexTo(tfHex.getText(), 8);
                } else {
                    s_dec = _converter.convertHexToDecimalR(tfHex.getText());
                    s_bin = _converter.convertFromDecimalToBinR(s_dec);
                    s_oct = _converter.convertFromDecimalToOctR(s_dec);
                }
                textfieldRW(0, false, s_dec);
                textfieldRW(1, false, s_bin);
                textfieldRW(3, false, s_oct);
            });

            tfOct.textProperty().addListener(((observableValue, s1, s2) -> {
                _active = 3;
                if (!s2.matches("[0-7.]+")) {
                    tfOct.setText(s2.replaceAll("[^0-7.]", ""));
                }
                if (cbRationalMode.selectedProperty().get()) {
                    if (s2.chars().filter(ch -> ch == '.').count() > 1) {
                        tfOct.setText(s1);
                    }
                } else {
                    if (s2.contains(".")) {
                        tfOct.setText(s1);
                    }
                }
            }));
            tfOct.setOnAction(evt -> {
                String s_dec = "null", s_bin = "null", s_hex = "null";
                if (tfOct.getText().isEmpty()) { return; }
                if (cbIntegerMode.selectedProperty().get()) {
                    s_dec = _converter.convertFromOctTo(tfOct.getText(), 10);
                    s_bin = _converter.convertFromOctTo(tfOct.getText(), 2);
                    s_hex = _converter.convertFromOctTo(tfOct.getText(), 16);
                } else {
                    s_dec = _converter.convertOctToDecR(tfOct.getText());
                    s_bin = _converter.convertFromDecimalToBinR(s_dec);
                    s_hex = _converter.convertFromDecimalToHexR(s_dec);
                }
                textfieldRW(0, false, s_dec);
                textfieldRW(1, false, s_bin);
                textfieldRW(2, false, s_hex);
            });
        });
    }

    private void cleanUpInput() {
        String fieldText = textfieldRW(true, null);
        if (fieldText.contains(".")) {
            fieldText = fieldText.chars().filter(i -> ((char)i != '.')).collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
            textfieldRW(false, fieldText);
        }
    }

    private String textfieldRW(boolean read, String text) {
         return textfieldRW(_active, read, text);
    }

    private String textfieldRW(int tfi, boolean read, String text) {
        switch (tfi) {
            case 0:
                if (read) {
                    return tfDec.getText();
                } else {
                    tfDec.setText(text);
                }
                break;
            case 1:
                if (read) {
                    return tfBin.getText();
                } else {
                    tfBin.setText(text);
                }
                break;
            case 2:
                if (read) {
                    return tfHex.getText();
                } else {
                    tfHex.setText(text);
                }
                break;
            case 3:
                if (read) {
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