package com.unipi.dmaml.airbnbpriceestimator.application.javafxextension.pane;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class HorizontalPaneWithTextFields extends Pane {
    public HorizontalPaneWithTextFields(int width, TextField gtf1, TextField gtf2) {
        setWidth(width);

        Label label1 = new Label(gtf1.getPromptText());
        label1.relocate(50, 30);
        Label label2 = new Label(gtf2.getPromptText());
        label2.relocate(350, 30);
        getChildren().addAll(gtf1, gtf2, label1, label2);
    }
}
