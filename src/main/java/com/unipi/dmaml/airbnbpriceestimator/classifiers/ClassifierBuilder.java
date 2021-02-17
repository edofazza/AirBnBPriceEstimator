package com.unipi.dmaml.airbnbpriceestimator.classifiers;

import com.unipi.dmaml.airbnbpriceestimator.classifiers.algorithms.*;
import com.unipi.dmaml.airbnbpriceestimator.classifiers.loaders.DatasetFromCsvLoader;
import weka.core.Instances;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ClassifierBuilder {

    public static void main(String[] args){
        try {
            Files.createDirectories(Paths.get("models/"));
            Files.createDirectories(Paths.get("results/"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Instances data = new DatasetFromCsvLoader().loadData();
        data.setClass(data.attribute("price"));
        //new LinearRegression(data).buildClassifiersAndSaveResults();
        //new RandomForest(data).buildClassifiersAndSaveResults();
        new KNN(data).buildClassifiersAndSaveResults();
        new M5Rules(data).buildClassifiersAndSaveResults();
    }
}
