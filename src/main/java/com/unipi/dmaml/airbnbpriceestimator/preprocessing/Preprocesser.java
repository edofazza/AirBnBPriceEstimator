package com.unipi.dmaml.airbnbpriceestimator.preprocessing;

import com.unipi.dmaml.airbnbpriceestimator.preprocessing.loaders.RawDataLoader;
import com.unipi.dmaml.airbnbpriceestimator.preprocessing.tasks.BathroomCleaner;
import com.unipi.dmaml.airbnbpriceestimator.preprocessing.tasks.ListToHotVectorHandler;
import com.unipi.dmaml.airbnbpriceestimator.preprocessing.tasks.MissingValueFiller;
import com.unipi.dmaml.airbnbpriceestimator.preprocessing.tasks.PriceCleaner;
import com.unipi.dmaml.airbnbpriceestimator.preprocessing.utils.ColumnHandler;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.CSVSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.Remove;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
