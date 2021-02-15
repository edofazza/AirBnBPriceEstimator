package com.unipi.dmaml.airbnbpriceestimator.classifiers.algorithms;

import com.unipi.dmaml.airbnbpriceestimator.classifiers.saver.FileSaver;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.core.Instances;

import java.util.Random;

public class LinearRegression {
    private Instances dataset;

    public LinearRegression(Instances dataset){
        this.dataset=dataset;
    }

    public void buildClassifiersAndSaveResults(){
        //buildLinearRegression();
        buildLinearRegressionWithAttributeSelection();
    }

    private void buildLinearRegression(){
        try {
            weka.classifiers.functions.LinearRegression classifier = new weka.classifiers.functions.LinearRegression();
            String[] options = new String[4];
            options[0] = "-S"; options[1]="0";
            options[2] = "-R"; options[3]="1.0E-8";
            classifier.setOptions(options);
            classifier.buildClassifier(dataset);
            Evaluation evaluation = new Evaluation(dataset);
            evaluation.crossValidateModel(classifier, dataset, 10, new Random(1));
            new FileSaver(evaluation, "LinearRegression", null).save();
        }catch (Exception e){e.printStackTrace();}
    }

    private void buildLinearRegressionWithAttributeSelection(){
        try{
            AttributeSelectedClassifier classifier = new AttributeSelectedClassifier();
            String[] options = new String[6];
            options[0]="-E"; options[1]="weka.attributeSelection.CfsSubsetEval -P 1 -E 1"; //attribute evaluator
            options[2]="-S"; options[3]="weka.attributeSelection.BestFirst -D 2 -N 5"; //search algorithm
            options[4]="-W"; options[5]="weka.classifiers.functions.LinearRegression"; //classification algorithm
            classifier.setOptions(options);
            Evaluation evaluation = new Evaluation(dataset);
            evaluation.crossValidateModel(classifier, dataset, 10, new Random(1));
            new FileSaver(evaluation, "LinearRegression", "CfsSubsetEval+BestFirst").save();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
