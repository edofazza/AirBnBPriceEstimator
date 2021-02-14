package com.unipi.dmaml.airbnbpriceestimator.preprocessing.javafxextension.scrollpane;

import com.unipi.dmaml.airbnbpriceestimator.preprocessing.javafxextension.pane.HorizontalPaneWithRadioButtons;
import com.unipi.dmaml.airbnbpriceestimator.preprocessing.javafxextension.pane.HorizontalPaneWithTextFields;;
import com.unipi.dmaml.airbnbpriceestimator.preprocessing.javafxextension.radiobutton.RadioButtonForAmenity;
import com.unipi.dmaml.airbnbpriceestimator.preprocessing.javafxextension.textfields.OnlyCharactersTextField;
import com.unipi.dmaml.airbnbpriceestimator.preprocessing.javafxextension.textfields.OnlyDecimalsTextField;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainView extends ScrollPane {
    private VBox vBox = new VBox();

    public MainView(int width) {
        setPrefWidth(width);
        setStyle("-fx-background: white; -fx-border-color: white;");

        vBox.setSpacing(20);
        setContent(vBox);

        for (int i = 0; i < 5; i++) {
            TextField genericTextField1 = new OnlyCharactersTextField(50, 50, "Pippo");
            TextField genericTextField2 = new OnlyDecimalsTextField(350, 50, "Meow");
            HorizontalPaneWithTextFields horizontalPane = new HorizontalPaneWithTextFields(width, genericTextField1, genericTextField2);
            vBox.getChildren().add(horizontalPane);
        }
        for (int i = 0; i < 5; i++) {
            RadioButtonForAmenity radioButtonForAmenity1 = new RadioButtonForAmenity(50, 50, "Amenity1");
            RadioButtonForAmenity radioButtonForAmenity2 = new RadioButtonForAmenity(350, 50, "Amenity2");
            HorizontalPaneWithRadioButtons horizontalPane = new HorizontalPaneWithRadioButtons(width, radioButtonForAmenity1, radioButtonForAmenity2);
            vBox.getChildren().add(horizontalPane);
        }

        Pane pane = new Pane();
        pane.setPrefWidth(width);
        pane.getChildren().add(new Button("PROCESS"));
        vBox.getChildren().add(pane);
    }
}
