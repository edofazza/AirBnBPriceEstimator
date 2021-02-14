package com.unipi.dmaml.airbnbpriceestimator.preprocessing.javafxextension.pane;

import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;

public class HorizontalPaneWithRadioButtons extends Pane {
    public HorizontalPaneWithRadioButtons(int width, RadioButton radioButton1, RadioButton radioButton2) {
        setPrefWidth(width);

        getChildren().addAll(radioButton1, radioButton2);
    }
}
