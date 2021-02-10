package com.unipi.dmaml.airbnbpriceestimator;

import com.unipi.dmaml.airbnbpriceestimator.utils.ColumnHandler;
import com.unipi.dmaml.airbnbpriceestimator.utils.HotVectorGenerator;

import java.util.List;

public class Preprocesser {

    private static String csvFile="csv/airbnb_dataset_preprocessed";
    public static void main(String[] args) {

        //TODO load csvFile
        //TODO write attribute price into price.csv and remove column
        //TODO write attribute amenities into amenities.csv and remove column
        //TODO write attribute bathrooms into bathrooms.csv and remove column

        //TODO new thread :{
        // for bedroom missing values, Weka filter ReplaceMissingValuesWithUserConstant(1)
        // for ALL the other missing values, Weka filter ReplaceMissingValues (also for score rating)
        // for time response, sortLabels+OrdinalToNumeric
        //}

        //TODO new HotVectorThread{
        /*
        HotVectorGenerator hotVectorGenerator = new HotVectorGenerator();
        List<String> features = hotVectorGenerator.getAllFeatures("csv/amenities.csv");
        hotVectorGenerator.sortHeaders(features);
        hotVectorGenerator.createHotVectorCSV("csv/result.csv", "csv/amenities.csv", features);
        */
        //}

        //TODO new ColumnHandlerThread (can be more than 1 if you want){
        ColumnHandler columnHandler = new ColumnHandler();
        //columnHandler.bathroomColumns("csv/bathrooms.csv", "csv/bathroomsFormatted.csv");
        //columnHandler.removeDollarFromPrice("csv/price.csv", "csv/priceFormatted.csv");
        //}

        //TODO merge file into new completely preprocessed file
        //TODO delete all the files but the final one

    }
}
