package com.unipi.dmaml.airbnbpriceestimator.preprocessing.javafxextension.textfields;

import javafx.scene.control.TextField;

public class OnlyCharactersTextField extends TextField {

    public OnlyCharactersTextField(int x, int y, String placeholder) {
        relocate(x, y);
        setPromptText(placeholder);

        textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[a-zA-Z]+$")) {
                setText(newValue.replaceAll("\\d", ""));
            }
        });
    }
}
