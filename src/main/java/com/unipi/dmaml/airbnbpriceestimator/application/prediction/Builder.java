package com.unipi.dmaml.airbnbpriceestimator.application.prediction;


import weka.classifiers.Classifier;
import weka.classifiers.functions.LinearRegression;
import weka.core.*;

import weka.core.converters.ConverterUtils.DataSource;

public class Builder {
    private static final String selectedClassifier="RandomForest";
    private static final String selectedAttributeAlgorithm = "CfsSubsetEval+BestFirst";

    public static Instances buildInstances(){
        try {
            DataSource source = new DataSource("models/" + selectedClassifier + "_" + selectedAttributeAlgorithm + "_data.arff");
            Instances model = source.getDataSet();
            return model;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Classifier loadClassifier(){
        try {
            return (Classifier) SerializationHelper.read("models/" + selectedClassifier + "_" + selectedAttributeAlgorithm + ".model");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
