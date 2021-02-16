package com.unipi.dmaml.airbnbpriceestimator.application.javafxextension.pane;

import javafx.scene.control.RadioButton;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class HorizontalPaneWithRadioButtons extends Pane implements IHorizontalPane {
    RadioButton radioButton1;
    RadioButton radioButton2;

    public HorizontalPaneWithRadioButtons(int width, RadioButton radioButton1, RadioButton radioButton2) {
        setPrefWidth(width);

        this.radioButton1 = radioButton1;
        this.radioButton2 = radioButton2;

        if (radioButton2 != null)
            getChildren().add(radioButton2);
        getChildren().add(radioButton1);
    }

    @Override
    public List<Pair<String, String>> getResult() {
        List<Pair<String, String>> pairList = new ArrayList<>();

        Pair<String, String> pair;
        if (radioButton1.isSelected())
             pair = new Pair<>(radioButton1.getText(), "1");
        else
            pair = new Pair<>(radioButton1.getText(), "0");
        pairList.add(pair);

        if (radioButton2 != null) {
            if (radioButton2.isSelected())
                pair = new Pair<>(radioButton2.getText(), "1");
            else
                pair = new Pair<>(radioButton2.getText(), "0");
            pairList.add(pair);
        }
        return pairList;
    }
}
