package com.unipi.dmaml.airbnbpriceestimator.application.javafxextension.label;

import javafx.scene.control.Label;

public class TitleLabel extends Label {

    public TitleLabel(String title) {
        super();

        setText(title);
        relocate(140, 70);
        setStyle("-fx-font-family: 'Arial Black';\n" +
                "    -fx-font-size: 30px;\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-text-fill: #a30014;");
    }
}