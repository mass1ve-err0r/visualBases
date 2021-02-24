/*
 * Project: visualBases
 * Author:  Saadat M. Baig
 * Date:    24.02.21
 * License: BSD-3-Clause-Attribution
 */
package de.saadatbaig.visualBases.Controllers;

import de.saadatbaig.visualBases.Utils.IntegerConverter;
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

    IntegerConverter _iconv;
    HostServices _services;
    int _active = 0;


    public MainViewController() {
        _iconv = new IntegerConverter();
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
                if (tfDec.getText().isEmpty()) { return; }
                String s_bin = _iconv.convertFromDecimalTo(tfDec.getText(), 2);
                String s_hex = _iconv.convertFromDecimalTo(tfDec.getText(), 16);
                String s_oct = _iconv.convertFromDecimalTo(tfDec.getText(), 8);
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
                if (tfBin.getText().isEmpty()) { return; }
                String s_dec = _iconv.convertFromBinTo(tfBin.getText(), 10);
                String s_hex = _iconv.convertFromBinTo(tfBin.getText(), 16);
                String s_oct = _iconv.convertFromBinTo(tfBin.getText(), 8);
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
                if (tfHex.getText().isEmpty()) { return; }
                String s_dec = _iconv.convertFromHexTo(tfHex.getText(), 10);
                String s_bin = _iconv.convertFromHexTo(tfHex.getText(), 2);
                String s_oct = _iconv.convertFromHexTo(tfHex.getText(), 8);
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
                if (tfOct.getText().isEmpty()) { return; }
                String s_dec = _iconv.convertFromOctTo(tfOct.getText(), 10);
                String s_hex = _iconv.convertFromOctTo(tfOct.getText(), 16);
                String s_bin = _iconv.convertFromOctTo(tfOct.getText(), 2);
                textfieldRW(0, false, s_dec);
                textfieldRW(2, false, s_hex);
                textfieldRW(1, false, s_bin);
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