package com.unipi.dmaml.airbnbpriceestimator.preprocessing;

import com.unipi.dmaml.airbnbpriceestimator.preprocessing.loaders.RawDataLoader;
import com.unipi.dmaml.airbnbpriceestimator.preprocessing.utils.ColumnHandler;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Preprocesser {

    public static void main(String[] args) {

        Instances rawData = new RawDataLoader().loadRawFile();

        writeIntoFile("csv/price.csv", rawData.attribute("price"), rawData);
        writeIntoFile("csv/bathrooms.csv", rawData.attribute("bathrooms_text"), rawData);
        Remove removePrice = new Remove();
        removePrice.setAttributeIndices("last");
        Remove removeBathrooms = new Remove();
        removeBathrooms.setAttributeIndices("12");
        try{
            removePrice.setInputFormat(rawData);
            Instances tempdata = Filter.useFilter(rawData, removePrice);
            Instances newData = Filter.useFilter(tempdata, removeBathrooms);
        }catch (Exception e){
            e.printStackTrace();
        }



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

    private static void writeIntoFile(String path, Attribute column, Instances data){
        try(BufferedWriter bf = Files.newBufferedWriter(Paths.get(path))){
            bf.write(column.name()+"\n");
            for(Instance i: data){
                bf.write(i.stringValue(column)+"\n");
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

    }

}
