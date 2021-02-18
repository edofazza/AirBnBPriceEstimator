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
            ClassifierBuilder.main(new String[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
