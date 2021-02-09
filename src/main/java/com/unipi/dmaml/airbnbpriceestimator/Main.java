package com.unipi.dmaml.airbnbpriceestimator;

import com.unipi.dmaml.airbnbpriceestimator.utils.ColumnHandler;
import com.unipi.dmaml.airbnbpriceestimator.utils.HotVectorGenerator;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        /*
        HotVectorGenerator hotVectorGenerator = new HotVectorGenerator();
        List<String> features = hotVectorGenerator.getAllFeatures("csv/amenities.csv");
        hotVectorGenerator.sortHeaders(features);
        hotVectorGenerator.createHotVectorCSV("csv/result.csv", "csv/amenities.csv", features);
        */
        ColumnHandler columnHandler = new ColumnHandler();
        //columnHandler.bathroomColumns("csv/bathrooms.csv", "csv/bathroomsFormatted.csv");
        //columnHandler.removeDollarFromPrice("csv/price.csv", "csv/priceFormatted.csv");
        columnHandler.reviewScoreRating("csv/score.csv", "csv/scoreFormatted.csv");
    }
}
