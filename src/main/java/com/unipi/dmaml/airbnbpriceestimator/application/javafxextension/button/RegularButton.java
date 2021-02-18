package com.unipi.dmaml.airbnbpriceestimator.application.javafxextension.button;

import javafx.scene.control.Button;

public class RegularButton extends Button {

    public RegularButton(String text, int x, int y) {
        super();

        setText(text);
        relocate(x, y);

        setStyle("-fx-font-size: 15px;\n" +
                "    -fx-font-family: 'Arial';\n" +
                "    -fx-font-weight: bold;\n" +
                "    -fx-background-color: transparent;\n" +
                "    -fx-border-color: #000000;");
    }
}
