package com.unipi.dmaml.airbnbpriceestimator.application.javafxextension.scrollpane;

import com.unipi.dmaml.airbnbpriceestimator.application.javafxextension.pane.HorizontalPaneWithRadioButtons;
import com.unipi.dmaml.airbnbpriceestimator.application.javafxextension.pane.HorizontalPaneWithTextFields;;
import com.unipi.dmaml.airbnbpriceestimator.application.javafxextension.pane.IHorizontalPane;
import com.unipi.dmaml.airbnbpriceestimator.application.javafxextension.radiobutton.RadioButtonForAmenity;
import com.unipi.dmaml.airbnbpriceestimator.application.javafxextension.textfields.GeneralTextField;
import com.unipi.dmaml.airbnbpriceestimator.application.javafxextension.textfields.OnlyCharactersTextField;
import com.unipi.dmaml.airbnbpriceestimator.application.javafxextension.textfields.OnlyDecimalsTextField;
import com.unipi.dmaml.airbnbpriceestimator.application.prediction.InstanceClassifier;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainView extends ScrollPane {
    private VBox vBox = new VBox();
    private List<IHorizontalPane> paneList = new ArrayList<>();

    public MainView(int width, int height) {
        setPrefSize(width, height);
        setStyle("-fx-background: white; -fx-border-color: white;");

        vBox.setSpacing(20);
        setContent(vBox);

        InstanceClassifier instanceClassifier = new InstanceClassifier();
        List<Pair<String, String>> pairList = instanceClassifier.getAttributes();

        // I LIST ALL THE FIELDS DIVIDING AMENITIES FROM THE REST
        List<String> amenities = new ArrayList<>();
        List<String> normalFeature = new ArrayList<>();
        for (Pair<String, String> pair: pairList) {
            if (pair.getKey().toLowerCase(Locale.ROOT).equals(pair.getKey()))
                normalFeature.add(pair.getKey());
            else
                amenities.add(pair.getKey());
        }
        System.out.println(normalFeature.size());
        System.out.println(amenities.size());

        int loop = (normalFeature.size() % 2 == 0 ? normalFeature.size() : normalFeature.size() + 1)/2;
        System.out.println(loop);
        for (int i = 0; i < loop; i++) {
            GeneralTextField textField1 = new GeneralTextField(50, 50, normalFeature.get(i*2));

            GeneralTextField textField2 = null;
            try {
               textField2 = new GeneralTextField(300, 50, normalFeature.get(i*2 + 1));
            } catch (Exception e) {}

            HorizontalPaneWithTextFields horizontalPaneWithTextFields = new HorizontalPaneWithTextFields(width - 20 , textField1, textField2);
            paneList.add(horizontalPaneWithTextFields);
            vBox.getChildren().add(horizontalPaneWithTextFields);
        }

        loop = (amenities.size() % 2 == 0 ? amenities.size() : amenities.size() + 1)/2;
        for (int i = 0; i < loop; i++) {
            RadioButtonForAmenity radioButton1 = new RadioButtonForAmenity(50, 50, amenities.get(i*2));
            RadioButtonForAmenity radioButton2 = null;

            try {
                radioButton2 = new RadioButtonForAmenity(300, 50, amenities.get(i*2 + 1));
            } catch (Exception e) {}

            HorizontalPaneWithRadioButtons horizontalPaneWithRadioButtons = new HorizontalPaneWithRadioButtons(width - 20, radioButton1, radioButton2);
            paneList.add(horizontalPaneWithRadioButtons);
            vBox.getChildren().add(horizontalPaneWithRadioButtons);
        }

        Button button = new Button("PREDICT PRICE");
        button.setOnAction(e -> buttonAction(instanceClassifier));
        vBox.getChildren().addAll(button);
    }

    public void buttonAction(InstanceClassifier instanceClassifier) {


        Label label = new Label("PREDICTED PRICE: " + instanceClassifier.predictPrice(computeFields()));
        vBox.getChildren().add(label);
    }

    public List<Pair<String, String>> computeFields() {
        List<Pair<String, String>> pairList = new ArrayList<>();

        for (IHorizontalPane pane: paneList) {
            pairList.addAll(pane.getResult());
        }

        return pairList;
    }
}
