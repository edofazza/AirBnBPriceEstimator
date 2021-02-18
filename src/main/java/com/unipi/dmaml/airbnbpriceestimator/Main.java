package com.unipi.dmaml.airbnbpriceestimator;

import com.unipi.dmaml.airbnbpriceestimator.classifiers.ClassifierBuilder;
import com.unipi.dmaml.airbnbpriceestimator.preprocessing.Preprocesser;

/**
 * This class gives the possibility to run the Preprocesser and the ClassifierBuilder
 * together without the need of running the two classes by their own.
 */
public class Main {
    public static void main(String[] args) {
        try {
            Preprocesser.main(new String[0]);
            System.out.println("PREPROCESSING HAS FINISHED");
            ClassifierBuilder.main(new String[0]);
            System.out.println("CLASSIFICATION HAS FINISHED");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
