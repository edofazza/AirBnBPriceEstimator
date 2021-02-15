package com.unipi.dmaml.airbnbpriceestimator.classifiers.algorithms;

import com.unipi.dmaml.airbnbpriceestimator.classifiers.saver.FileSaver;
import org.w3c.dom.Attr;
import weka.attributeSelection.GreedyStepwise;
import weka.core.Attribute;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.core.Instances;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;

public class LinearRegression {
    private Instances dataset;
    private final int numFolds=10;

    public LinearRegression(Instances dataset){
        this.dataset=dataset;
    }

    public void buildClassifiersAndSaveResults(){
        //buildLinearRegression();
        buildLinearRegressionWithAttributeSelection();
    }

    private void buildLinearRegression(){
        try {
            Instances randData = new Instances(dataset);
            randData.randomize(new Random(1));

            for(int i=0; i<numFolds; i++){
                executeCV(randData, i, null, null);
            }
            System.out.println("linear regression terminated");
        }catch (Exception e){e.printStackTrace();}
    }

    private void buildLinearRegressionWithAttributeSelection(){
        try{
            Instances randData = new Instances(dataset);
            randData.randomize(new Random(1));

            for(int i=0; i<numFolds; i++){
                AttributeSelection filter = new AttributeSelection();
                filter.setEvaluator(new CfsSubsetEval());
                filter.setSearch(new BestFirst());
                executeCV(randData, i, filter, "CfsSubsetEval+BestFirst");
            }

            randData = new Instances(dataset);
            randData.randomize(new Random(1));

            for(int i=0; i<numFolds; i++){
                AttributeSelection filter = new AttributeSelection();
                filter.setEvaluator(new CfsSubsetEval());
                filter.setSearch(new GreedyStepwise());
                executeCV(randData, i, filter, "CfsSubsetEval+GreedyStepwise");
            }

            System.out.println("linear regression with attribute selection terminated");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void executeCV(Instances dataset, int currentFold, AttributeSelection filter, String filterName) throws Exception {
        Instances train = dataset.trainCV(numFolds, currentFold);
        Instances test = dataset.testCV(numFolds, currentFold);
        List<Attribute> chosen=new ArrayList<>();
        if(filter!=null) {
            filter.setInputFormat(train);
            Instances trainReduced = Filter.useFilter(train, filter);
            Instances testReduced = Filter.useFilter(test, filter);
            train = new Instances(trainReduced);
            test = new Instances(testReduced);
            for(int i=0; i<train.numAttributes(); i++)
                chosen.add(train.attribute(i));
        }
        weka.classifiers.functions.LinearRegression classifier = new weka.classifiers.functions.LinearRegression();
        classifier.buildClassifier(train);
        Evaluation evaluation = new Evaluation(train);
        evaluation.evaluateModel(classifier, test);
        new FileSaver(evaluation, "LinearRegression", filterName, currentFold, chosen).save();
    }

}
