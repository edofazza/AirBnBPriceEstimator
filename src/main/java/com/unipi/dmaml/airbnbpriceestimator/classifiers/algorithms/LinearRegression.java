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
            AttributeSelectedClassifier classifier1 = new AttributeSelectedClassifier();
            String[] options1 = new String[6];
            options1[0]="-E"; options1[1]="weka.attributeSelection.CfsSubsetEval -P 1 -E 1"; //attribute evaluator
            options1[2]="-S"; options1[3]="weka.attributeSelection.BestFirst -D 2 -N 5"; //search algorithm
            options1[4]="-W"; options1[5]="weka.classifiers.functions.LinearRegression"; //classification algorithm
            classifier1.setOptions(options1);
            Evaluation evaluation1 = new Evaluation(dataset);
            evaluation1.crossValidateModel(classifier1, dataset, 10, new Random(1));
            new FileSaver(evaluation1, "LinearRegression", "CfsSubsetEval+BestFirst").save();


            AttributeSelectedClassifier classifier2 = new AttributeSelectedClassifier();
            String[] options2 = new String[6];
            options2[0]="-E"; options2[1]="weka.attributeSelection.CfsSubsetEval -P 1 -E 1"; //attribute evaluator
            options2[2]="-S"; options2[3]="weka.attributeSelection.GreedyStepwise -T -1.7976931348623157E308 -N -1 -num-slots 1"; //search algorithm
            options2[4]="-W"; options2[5]="weka.classifiers.functions.LinearRegression"; //classification algorithm
            classifier2.setOptions(options2);
            Evaluation evaluation2 = new Evaluation(dataset);
            evaluation2.crossValidateModel(classifier2, dataset, 10, new Random(1));
            new FileSaver(evaluation2, "LinearRegression", "CfsSubsetEval+GreedyStepwise").save();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
