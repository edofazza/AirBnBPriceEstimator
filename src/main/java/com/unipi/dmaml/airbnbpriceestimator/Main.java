package com.unipi.dmaml.airbnbpriceestimator;

import com.unipi.dmaml.airbnbpriceestimator.utils.HotVectorGenerator;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        HotVectorGenerator hotVectorGenerator = new HotVectorGenerator();
        List<String> features = hotVectorGenerator.getAllFeatures("csv/amenities.csv");
        hotVectorGenerator.createHotVectorCSV("csv/result.csv", "csv/amenities.csv", features);
    }
}
