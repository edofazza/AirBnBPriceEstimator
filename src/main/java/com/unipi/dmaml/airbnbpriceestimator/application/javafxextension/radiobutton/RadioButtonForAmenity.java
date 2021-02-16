package com.unipi.dmaml.airbnbpriceestimator.application.javafxextension.radiobutton;

import javafx.scene.control.RadioButton;

public class RadioButtonForAmenity extends RadioButton {
    public RadioButtonForAmenity(int x, int y, String text) {
        super(text);
        relocate(x, y);
    }
}
