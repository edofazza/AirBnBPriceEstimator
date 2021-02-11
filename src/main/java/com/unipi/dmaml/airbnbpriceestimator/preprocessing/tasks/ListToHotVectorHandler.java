package com.unipi.dmaml.airbnbpriceestimator.preprocessing.tasks;

import com.unipi.dmaml.airbnbpriceestimator.preprocessing.utils.HotVectorGenerator;

import java.util.List;

public class ListToHotVectorHandler implements Runnable{
    @Override
    public void run() {
        HotVectorGenerator hotVectorGenerator = new HotVectorGenerator();
        List<String> features = hotVectorGenerator.getAllFeatures("csv/amenities.csv");
        hotVectorGenerator.sortHeaders(features);
        hotVectorGenerator.createHotVectorCSV("csv/result.csv", "csv/amenities.csv", features);
    }
}
