package com.unipi.dmaml.airbnbpriceestimator.preprocessing.tasks;

import weka.core.Instances;
import weka.core.converters.CSVSaver;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.OrdinalToNumeric;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;
import weka.filters.unsupervised.attribute.ReplaceMissingWithUserConstant;
import weka.filters.unsupervised.attribute.SortLabels;

import java.io.File;
import java.util.concurrent.Executors;


public class MissingValueFiller implements Runnable {
    private Instances data;

    public MissingValueFiller(Instances oldData){
        data = oldData;
    }

    @Override
    public void run(){
        ReplaceMissingWithUserConstant defaultValue = new ReplaceMissingWithUserConstant();
        ReplaceMissingValues missing = new ReplaceMissingValues();
        SortLabels responseTimeSorter = new SortLabels();
        OrdinalToNumeric numericResponseTime = new OrdinalToNumeric();
        defaultValue.setAttributes("12");
        defaultValue.setNumericReplacementValue("1");
        responseTimeSorter.setAttributeIndices("2");
        numericResponseTime.setAttributesToOperateOn("2");
        try{
            defaultValue.setInputFormat(data);
            Instances tempData = Filter.useFilter(data, defaultValue);
            missing.setInputFormat(tempData);
            tempData = Filter.useFilter(tempData, missing);
            responseTimeSorter.setInputFormat(tempData);
            tempData = Filter.useFilter(tempData, responseTimeSorter);
            numericResponseTime.setInputFormat(tempData);
            Instances newData = Filter.useFilter(tempData, numericResponseTime);

            saveIntoFile(newData);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void saveIntoFile(Instances data) throws Exception{
        CSVSaver c= new CSVSaver();
        c.setInstances(data);
        c.setFieldSeparator(";");
        c.setFile(new File("csv/airbnb_dataset_preprocessed.csv"));
        c.writeBatch();
    }
}
