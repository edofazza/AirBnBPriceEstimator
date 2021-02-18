package com.unipi.dmaml.airbnbpriceestimator.application.prediction;


import weka.classifiers.Classifier;
import weka.core.*;

import weka.core.converters.ConverterUtils.DataSource;

public class Builder {
    private static final String selectedClassifier="M5Rules";
    private static final String selectedClassifierRF = "RandomForest";
    private static final String selectedAttributeAlgorithm = "CfsSubsetEval+BestFirst";
    private static Classifier loadedClassifier = null;

    public static Instances buildInstances(boolean isRandomForest){
        try {
            DataSource source;
            if (isRandomForest)
                source = new DataSource("models/" + selectedClassifierRF + "_" + selectedAttributeAlgorithm + "_data.arff");
            else
                source = new DataSource("models/" + selectedClassifier + "_" + selectedAttributeAlgorithm + "_data.arff");
            Instances model = source.getDataSet();
            return model;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static Classifier loadClassifier(boolean isRandomForest){
        try {
            if(loadedClassifier==null)
                if (isRandomForest)
                    loadedClassifier = (Classifier) SerializationHelper.read("models/" + selectedClassifierRF + "_" + selectedAttributeAlgorithm + ".model");
                else
                    loadedClassifier = (Classifier) SerializationHelper.read("models/" + selectedClassifier + "_" + selectedAttributeAlgorithm + ".model");
            return loadedClassifier;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
