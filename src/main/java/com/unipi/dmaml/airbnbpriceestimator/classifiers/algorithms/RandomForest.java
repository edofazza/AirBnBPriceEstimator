package com.unipi.dmaml.airbnbpriceestimator.classifiers.algorithms;

import com.unipi.dmaml.airbnbpriceestimator.classifiers.saver.FileSaver;
import weka.attributeSelection.BestFirst;
import weka.attributeSelection.CfsSubsetEval;
import weka.attributeSelection.GreedyStepwise;
import weka.classifiers.Evaluation;
import weka.core.Attribute;
import weka.core.Instances;
import weka.core.SerializationHelper;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.supervised.attribute.AttributeSelection;

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;

public class RandomForest {
    private Instances dataset;
    private final int numFolds=10;

    public RandomForest(Instances dataset){
        this.dataset=dataset;
    }

    public void buildClassifiersAndSaveResults(){
        //buildRandomForest();
        buildRandomForestWithAttributeSelection();
    }

    private void buildRandomForest(){
        try {
            Instances randData = new Instances(dataset);
            randData.randomize(new Random(1));

            for(int i=0; i<numFolds; i++){
                executeCV(randData, i, null, null);
            }
            System.out.println("linear regression terminated");
        }catch (Exception e){e.printStackTrace();}
    }

    private void buildRandomForestWithAttributeSelection(){
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

            System.out.println("RandomForest with attribute selection terminated");

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
            train = Filter.useFilter(train, filter);
            test = Filter.useFilter(test, filter);
            for(int i=0; i<train.numAttributes(); i++)
                chosen.add(train.attribute(i));
        }
        weka.classifiers.trees.RandomForest classifier = new weka.classifiers.trees.RandomForest();
        classifier.buildClassifier(train);
        Evaluation evaluation = new Evaluation(train);
        evaluation.evaluateModel(classifier, test);
        new FileSaver(evaluation, "RandomForest", filterName, currentFold, chosen).save();
        if(currentFold==8)
            saveModel(filterName, classifier, test);
    }

    private void saveModel(String filterName, weka.classifiers.trees.RandomForest classifier, Instances dataFormat) throws Exception{
        SerializationHelper.write("models/RandomForest_" + filterName + ".model", classifier);
        ArffSaver saver = new ArffSaver();
        saver.setInstances(dataFormat);
        saver.setFile(new File("models/RandomForest_" + filterName + "_data.arff"));
        saver.writeBatch();
    }
}
