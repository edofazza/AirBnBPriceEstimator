package com.unipi.dmaml.airbnbpriceestimator.preprocessing;

import com.unipi.dmaml.airbnbpriceestimator.preprocessing.javafxextension.scrollpane.MainView;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AirBnBPriceEstimatorFX extends Application {
    private final int width = 550;
    private final int height = 700;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        MainView mainView = new MainView(width);
        Group group = new Group(mainView);

        Scene scene = new Scene(group, width, height);
        primaryStage.setScene(scene);
        primaryStage.setTitle("AirBnBPriceEstimator");
        primaryStage.show();
    }
}
