package com.unipi.dmaml.airbnbpriceestimator.classifiers;

import com.unipi.dmaml.airbnbpriceestimator.classifiers.algorithms.*;
import com.unipi.dmaml.airbnbpriceestimator.classifiers.loaders.DatasetFromCsvLoader;
import weka.core.Instances;

public class ClassifierBuilder {

    public static void main(String[] args){
        Instances data = new DatasetFromCsvLoader().loadData();
        data.setClass(data.attribute("price"));
        //new LinearRegression(data).buildClassifiersAndSaveResults();
        //new RandomForest(data).buildClassifiersAndSaveResults();
        new KNN(data).buildClassifiersAndSaveResults();
        new M5Rules(data).buildClassifiersAndSaveResults();
    }
}
