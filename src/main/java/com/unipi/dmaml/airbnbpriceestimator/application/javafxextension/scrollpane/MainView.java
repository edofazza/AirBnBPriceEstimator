package com.unipi.dmaml.airbnbpriceestimator.application.javafxextension.scrollpane;

import com.unipi.dmaml.airbnbpriceestimator.application.javafxextension.pane.HorizontalPaneWithRadioButtons;
import com.unipi.dmaml.airbnbpriceestimator.application.javafxextension.pane.HorizontalPaneWithTextFields;;
import com.unipi.dmaml.airbnbpriceestimator.application.javafxextension.radiobutton.RadioButtonForAmenity;
import com.unipi.dmaml.airbnbpriceestimator.application.javafxextension.textfields.OnlyCharactersTextField;
import com.unipi.dmaml.airbnbpriceestimator.application.javafxextension.textfields.OnlyDecimalsTextField;
import com.unipi.dmaml.airbnbpriceestimator.application.prediction.InstanceClassifier;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.util.List;

public class MainView extends ScrollPane {
    private VBox vBox = new VBox();

    public MainView(int width, int height) {
        setPrefSize(width, height);
        setStyle("-fx-background: white; -fx-border-color: white;");

        vBox.setSpacing(20);
        setContent(vBox);

        for (int i = 0; i < 13; i++) {
            TextField genericTextField1 = new OnlyCharactersTextField(50, 50, "Pippo");
            TextField genericTextField2 = new OnlyDecimalsTextField(300, 50, "Meow");
            HorizontalPaneWithTextFields horizontalPane = new HorizontalPaneWithTextFields(width - 10, genericTextField1, genericTextField2);
            vBox.getChildren().add(horizontalPane);
        }

        /*InstanceClassifier instanceClassifier = new InstanceClassifier();
        List<Pair<String, String>> pairList = instanceClassifier.getAttributes();

        for (Pair<String, String> pair: pairList) {
            System.out.println(pair.getKey());
        }*/
    }
}
