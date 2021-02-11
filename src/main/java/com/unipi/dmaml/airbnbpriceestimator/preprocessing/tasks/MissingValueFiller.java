package com.unipi.dmaml.airbnbpriceestimator.preprocessing.tasks;

import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;
import weka.filters.unsupervised.attribute.ReplaceMissingWithUserConstant;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

public class MissingValueFiller implements Runnable {
    private Instances data;

    public MissingValueFiller(Instances oldData){
        data = oldData;
    }

    @Override
    public void run(){
        ReplaceMissingWithUserConstant defaultValue = new ReplaceMissingWithUserConstant();
        ReplaceMissingValues missing = new ReplaceMissingValues();
        defaultValue.setAttributes("13");
        defaultValue.setNumericReplacementValue("1");
        try(BufferedWriter bf = Files.newBufferedWriter(Paths.get("csv/airbnb_dataset_preprocessed.csv"))) {
            defaultValue.setInputFormat(data);
            Instances tempData = Filter.useFilter(data, defaultValue);
            missing.setInputFormat(tempData);
            Instances newData = Filter.useFilter(tempData, missing);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
