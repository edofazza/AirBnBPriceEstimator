package com.unipi.dmaml.airbnbpriceestimator.preprocessing.tasks;

import com.unipi.dmaml.airbnbpriceestimator.preprocessing.utils.OneHotGenerator;

import java.util.List;

public class ListToOneHotHandler implements Runnable{
    @Override
    public void run() {
        OneHotGenerator oneHotGenerator = new OneHotGenerator();
        List<String> features = oneHotGenerator.getAllFeatures("csv/amenities.csv");
        oneHotGenerator.sortHeaders(features);
        oneHotGenerator.createHotVectorCSV("csv/result.csv", "csv/amenities.csv", features);
    }
}
