package com.unipi.dmaml.airbnbpriceestimator.classifiers.linearRegression;

import com.unipi.dmaml.airbnbpriceestimator.classifiers.saver.FileSaver;
import weka.classifiers.Evaluation;
import weka.core.Instances;

import java.util.Random;

public class LinearRegression {
    private Instances dataset;

    public LinearRegression(Instances dataset){
        this.dataset=dataset;
    }

    public void buildClassifierAndSaveResults(){
        try {
            String[] options = new String[6];
            options[0] = "-S";
            options[1] = "1";        //no attribute selection
            options[2] = "-R";
            options[3] = "1.0E-8";   //ridge
            options[3] = "-num-decimal-places";
            options[4] = "4";

            weka.classifiers.functions.LinearRegression classifier = new weka.classifiers.functions.LinearRegression();
            classifier.setOptions(options);
            Evaluation evaluation = new Evaluation(dataset);
            evaluation.crossValidateModel(classifier, dataset, 10, new Random(1));
            new FileSaver(evaluation, "LinearRegression", null).save();
        }catch (Exception e){e.printStackTrace();}

    }
}
