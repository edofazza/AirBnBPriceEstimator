package com.unipi.dmaml.airbnbpriceestimator.application.javafxextension.textfields;

import javafx.scene.control.TextField;

public class OnlyDecimalsTextField extends TextField {

    /**
     * Constructs a particular TextField that takes only decimal values
     * @param x the x axis position
     * @param y the y axis position
     */
    public OnlyDecimalsTextField(int x, int y, String placeholder) {
        relocate(x, y);
        setPromptText(placeholder);

        textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }
}
