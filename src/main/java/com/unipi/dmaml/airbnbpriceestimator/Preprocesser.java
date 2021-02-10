package com.unipi.dmaml.airbnbpriceestimator;

import com.unipi.dmaml.airbnbpriceestimator.utils.ColumnHandler;
import com.unipi.dmaml.airbnbpriceestimator.utils.HotVectorGenerator;
import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Preprocesser {

    private static String csvFile="csv/airbnb_dataset10.csv";
    public static void main(String[] args) {

        CSVLoader loader= new CSVLoader();
        try {
            loader.setBufferSize(100);
            loader.setDateAttributes("1");
            loader.setDateFormat("dd/MM/yyyy");
            loader.setEnclosureCharacters("\"");
            loader.setFieldSeparator(";");
            loader.setMissingValue("?");
            loader.setNominalAttributes("2,5,7,8,9,10,12,19,20");
            loader.setNumericAttributes("3,4,6,11,13-18");
            loader.setSource(new File(csvFile));
            Instances data=loader.getDataSet();
            for(int i=0; i<10; i++){
                for(int j=0; j<data.numAttributes(); j++){
                    System.out.println(data.instance(i).toString(j));
                }
                System.out.println("\n ---------------------------------------------- \n");
            }

        }catch (IOException ioe){
            ioe.printStackTrace();
        }

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
