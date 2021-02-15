package com.unipi.dmaml.airbnbpriceestimator.classifiers.algorithms;

import com.unipi.dmaml.airbnbpriceestimator.classifiers.saver.FileSaver;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.core.Instances;

import java.util.Random;

public class RandomForest {
    private Instances dataset;

    public RandomForest(Instances dataset){
        this.dataset=dataset;
    }

    public void buildClassifiersAndSaveResults(){
        //buildRandomForest();
        buildRandomForestWithAttributeSelection();
    }

    private void buildRandomForest(){
        try {
            weka.classifiers.trees.RandomForest classifier = new weka.classifiers.trees.RandomForest();
            Evaluation evaluation = new Evaluation(dataset);
            evaluation.crossValidateModel(classifier, dataset, 10, new Random(1));
            new FileSaver(evaluation, "RandomForest", null).save();
            System.out.println("random forest terminated");
        }catch (Exception e){e.printStackTrace();}
    }

    private void buildRandomForestWithAttributeSelection(){
        try{
            AttributeSelectedClassifier classifier1 = new AttributeSelectedClassifier();
            String[] options1 = new String[6];
            options1[0]="-E"; options1[1]="weka.attributeSelection.CfsSubsetEval -P 1 -E 1"; //attribute evaluator
            options1[2]="-S"; options1[3]="weka.attributeSelection.BestFirst -D 2 -N 5"; //search algorithm = best first bidirectional
            options1[4]="-W"; options1[5]="weka.classifiers.trees.RandomForest"; //classification algorithm
            classifier1.setOptions(options1);
            Evaluation evaluation1 = new Evaluation(dataset);
            evaluation1.crossValidateModel(classifier1, dataset, 10, new Random(1));
            new FileSaver(evaluation1, "RandomForest", "CfsSubsetEval+BestFirst").save();
            System.out.println("random forest terminated");

            AttributeSelectedClassifier classifier2 = new AttributeSelectedClassifier();
            String[] options2 = new String[6];
            options2[0]="-E"; options2[1]="weka.attributeSelection.CfsSubsetEval -P 1 -E 1"; //attribute evaluator
            options2[2]="-S"; options2[3]="weka.attributeSelection.GreedyStepwise -T -1.7976931348623157E308 -N -1 -num-slots 1"; //search algorithm
            options2[4]="-W"; options2[5]="weka.classifiers.trees.RandomForest"; //classification algorithm
            classifier2.setOptions(options2);
            Evaluation evaluation2 = new Evaluation(dataset);
            evaluation2.crossValidateModel(classifier2, dataset, 10, new Random(1));
            new FileSaver(evaluation2, "RandomForest", "CfsSubsetEval+GreedyStepwise").save();
            System.out.println("random forest terminated");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
