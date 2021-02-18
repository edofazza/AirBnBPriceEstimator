package com.unipi.dmaml.airbnbpriceestimator.application;

import com.unipi.dmaml.airbnbpriceestimator.application.javafxextension.button.RegularButton;
import com.unipi.dmaml.airbnbpriceestimator.application.javafxextension.label.TitleLabel;
import com.unipi.dmaml.airbnbpriceestimator.application.javafxextension.scrollpane.MainView;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AirBnBPriceEstimatorFX extends Application {
    private final int width = 550;
    private final int height = 700;
    private Group group;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        //////////////////////////////
        TitleLabel label = new TitleLabel("\tCHOOSE \nTHE CLASSIFIER");

        RegularButton randomForest = new RegularButton("RANDOM FOREST", 200, 250);
        RegularButton m5Rules = new RegularButton("M5Rules", 240, 350);
        randomForest.setOnAction(e -> display('R'));
        m5Rules.setOnAction(e -> display('M'));

        group = new Group();
        group.getChildren().addAll(label, randomForest, m5Rules);

        //////////////////////////////

        Scene scene = new Scene(group, width, height);
        primaryStage.setScene(scene);
        primaryStage.setTitle("AirBnBPriceEstimator");
        primaryStage.show();
    }

    public void display(char c) {
        group.getChildren().clear();

        MainView mainView;
        switch (c) {
            case 'R':
                mainView = new MainView(width, height, 'R');
                group.getChildren().add(mainView);
                break;
            case 'M':
                mainView = new MainView(width, height, 'M');
                group.getChildren().add(mainView);
                break;
        }
    }
}
