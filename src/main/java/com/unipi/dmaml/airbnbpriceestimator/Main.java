package com.unipi.dmaml.airbnbpriceestimator;

import com.unipi.dmaml.airbnbpriceestimator.utils.HotVectorGenerator;

public class Main {
    public static void main(String[] args) {
        HotVectorGenerator hotVectorGenerator = new HotVectorGenerator();
        hotVectorGenerator.getAllFeatures("csv/amenities.csv");
    }
}
