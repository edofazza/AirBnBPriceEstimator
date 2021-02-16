package com.unipi.dmaml.airbnbpriceestimator.application.javafxextension.pane;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class HorizontalPaneWithTextFields extends Pane implements IHorizontalPane {
    private TextField gtf1;
    private TextField gtf2;

    public HorizontalPaneWithTextFields(int width, TextField gtf1, TextField gtf2) {
        setWidth(width);

        this.gtf1 = gtf1;
        this.gtf2 = gtf2;

        Label label1 = new Label(gtf1.getPromptText());
        label1.relocate(50, 30);
        getChildren().addAll(gtf1, label1);

        if (gtf2 != null) {
            Label label2 = new Label(gtf2.getPromptText());
            label2.relocate(300, 30);
            getChildren().addAll(gtf2, label2);
        }
    }

    @Override
    public List<Pair<String, String>> getResult() {
        List<Pair<String, String>> pairList = new ArrayList<>();

        gtf1.setText(gtf1.getText().substring(0, 1).toUpperCase() + gtf1.getText().substring(1));
        Pair<String, String> pair = new Pair<>(gtf1.getPromptText(), gtf1.getText().replaceAll("\\s+$", "").replace(" ", "_"));
        pairList.add(pair);

        if (gtf2 != null) {
            gtf2.setText(gtf2.getText().substring(0, 1).toUpperCase() + gtf2.getText().substring(1));
            pair = new Pair<>(gtf2.getPromptText(), gtf2.getText().replaceAll("\\s+$", "").replace(" ", "_"));
            pairList.add(pair);
        }
        return pairList;
    }
}
