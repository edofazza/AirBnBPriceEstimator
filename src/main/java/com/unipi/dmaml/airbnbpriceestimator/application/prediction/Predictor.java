package com.unipi.dmaml.airbnbpriceestimator.application.prediction;

import javafx.util.Pair;

import java.util.List;
import java.util.Map;

public interface Predictor {
    /**
     * predicts the price of an instance of B'n'B
     * @param attributes a map with name and values of the specified attributes
     * @return the predicted price as double value
     */
    double predictPrice(List<Pair<String, String>> attributes);

    /**
     * gets a list of the required attributes to fill. The list can then be passed to the predictor
     * @return List of (name, value) pairs of the attributes. Value are all to null
     */
    List<Pair<String, String>> getAttributes();

}
