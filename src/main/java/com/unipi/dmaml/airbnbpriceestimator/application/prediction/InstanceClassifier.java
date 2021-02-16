package com.unipi.dmaml.airbnbpriceestimator.application.prediction;

import javafx.util.Pair;
import weka.classifiers.Classifier;
import weka.core.*;

import java.util.*;

public class InstanceClassifier implements Predictor{
    private static Instances model;

    @Override
    public double predictPrice(List<Pair<String, String>> attributes) {
        try {
            if (model == null)
                model = Builder.buildInstances();
            Classifier classifier = Builder.loadClassifier();
            double[] values = new double[attributes.size()];
            for (int i = 0; i < attributes.size(); i++) {
                Pair<String, String> value = attributes.get(i);
                if (model.attribute(i).isDate())
                    values[i] = model.attribute(i).parseDate(value.getValue());
                else if(model.attribute(i).isNominal())
                    values[i] = model.attribute(i).indexOfValue(value.getValue());
                else //numerical
                    values[i] = Double.parseDouble(value.getValue());
            }
            Instance i = new DenseInstance(1.0, values);
            Instances toClassify = new Instances(model);
            toClassify.delete();
            toClassify.add(i);
            toClassify.setClass(toClassify.attribute("price"));
            classifier.classifyInstance(toClassify.firstInstance());
            return toClassify.firstInstance().classValue();
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public List<Pair<String, String>> getAttributes() {
        if(model==null)
            model = Builder.buildInstances();
        List<Pair<String, String>> attributes = new ArrayList<>();
        for(int i=0; i<model.numAttributes(); i++){
            Pair<String, String> entry= new Pair<>(model.attribute(i).name(), "");
            attributes.add(entry);
        }
        return attributes;
    }
}