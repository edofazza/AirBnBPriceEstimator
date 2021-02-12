package com.unipi.dmaml.airbnbpriceestimator.preprocessing;

import com.unipi.dmaml.airbnbpriceestimator.preprocessing.loaders.RawDataLoader;
import com.unipi.dmaml.airbnbpriceestimator.preprocessing.tasks.*;
import com.unipi.dmaml.airbnbpriceestimator.preprocessing.utils.*;
import weka.core.*;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

import java.io.*;
import java.nio.file.*;
import java.util.Locale;

public class Preprocesser {

    public static void main(String[] args) throws Exception{

        Instances rawData = new RawDataLoader().loadRawFile();

        writeIntoFile("csv/price.csv", rawData.attribute("price"), rawData);
        writeIntoFile("csv/bathrooms.csv", rawData.attribute("bathrooms_text"), rawData);
        Remove removePrice = new Remove();
        removePrice.setAttributeIndices("last");
        Remove removeBathrooms = new Remove();
        removeBathrooms.setAttributeIndices("12");
        Instances newData=rawData;
        try{
            removePrice.setInputFormat(rawData);
            Instances tempdata = Filter.useFilter(rawData, removePrice);
            removeBathrooms.setInputFormat(tempdata);
            newData = Filter.useFilter(tempdata, removeBathrooms);
        }catch (Exception e){
            e.printStackTrace();
        }

        Thread t1 = new Thread(new MissingValueFiller(newData));
        Thread t2 = new Thread(new PriceCleaner());
        Thread t3 = new Thread(new BathroomCleaner());
        Thread t4 = new Thread(new ListToHotVectorHandler());
        t1.start(); t2.start(); t3.start(); t4.start();
        t1.join(); t2.join(); t3.join(); t4.join();
        System.out.println("Main thread waited everyone");

        // REFINE ONE HOT
        HotVectorHandler tmp = new HotVectorHandler();
        tmp.operate("csv/result.csv", "csv/preprocessing/amenitiesMerged.csv");

        // MERGE
        ColumnMerger columnMerger = new ColumnMerger();
        columnMerger.createMergedCsv("csv/preprocessing/csvPreprocessed.csv",
                "csv/airbnb_dataset_preprocessed.csv",
                "csv/bathroomsFormatted.csv",
                "csv/priceFormatted.csv",
                "csv/preprocessing/amenitiesMerged.csv"
        );

        // DELETE TMP FILES
        deleteFile("csv/bathrooms.csv");
        deleteFile("csv/bathroomsFormatted.csv");
        deleteFile("csv/price.csv");
        deleteFile("csv/priceFormatted.csv");
        deleteFile("csv/result.csv");
        deleteFile("csv/preprocessing/amenitiesMerged.csv");
        deleteFile("airbnb_dataset_preprocessed.csv");
    }

    private static void deleteFile(String path) {
        File myObj = new File(path);
        myObj.delete();
    }

    private static void writeIntoFile(String path, Attribute column, Instances data){
        try(BufferedWriter bf = Files.newBufferedWriter(Paths.get(path))){
            if (column.name().toLowerCase(Locale.ROOT).contains("bathroom"))
                bf.write("bathroom"+"\n");
            else
                bf.write(column.name()+"\n");
            for(Instance i: data){
                bf.write(i.stringValue(column)+"\n");
            }
        }catch(IOException ioe){
            ioe.printStackTrace();
        }

    }

}
