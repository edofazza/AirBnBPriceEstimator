package com.unipi.dmaml.airbnbpriceestimator.application.javafxextension.textfields;

import javafx.scene.control.TextField;

public class GeneralTextField extends TextField {
    public GeneralTextField(int x, int y, String placeholder) {
        relocate(x, y);
        setPromptText(placeholder);
    }
}
