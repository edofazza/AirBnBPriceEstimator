package com.unipi.dmaml.airbnbpriceestimator.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HotVectorGenerator {

    public List<String> getAllFeatures(String path) {
        List<String> featureSet = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;

            while((line = br.readLine())!=null) {
                // remove not useful chars
                line = line.replace("\"", "");
                line = line.replace("[", "");
                line = line.replace("]", "");

                // split the data
                String[] featureArray = line.split(",");
                // add the feature if it’s not present yet
                for (String s : featureSet) {
                    if (!featureSet.contains(s))
                        featureSet.add(s);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(featureSet.size());
        return featureSet;
    }
}
