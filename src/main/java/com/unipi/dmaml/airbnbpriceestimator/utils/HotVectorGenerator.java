package com.unipi.dmaml.airbnbpriceestimator.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
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
                String[] featureArray = line.split(", ");
                // add the feature if it’s not present yet
                for (String s : featureArray) {
                    if (!featureSet.contains(s))
                        featureSet.add(s);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return featureSet;
    }

    public void createHotVectorCSV(String newFilePath, String csv, List<String> featureSet) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(newFilePath), StandardOpenOption.APPEND);
             BufferedReader br = new BufferedReader(new FileReader(csv))
        ) {
            // INSERT THE NEW COLUMN HEADERS
            String[] featureArray = featureSet.toArray(new String[0]);
            writer.write(String.join(",", featureArray) + "\n");

            // GET EACH LINE AND CREATE THE NEW LINE FROM IT
            String line;
            while((line = br.readLine())!=null) {
                // remove not useful chars
                line = line.replace("\"", "");
                line = line.replace("[", "");
                line = line.replace("]", "");

                // split the data
                String[] features = line.split(", ");

                // NEW STRING THAT WILL CONTAINS THE BINARY INFORMATION
                String newString = new String();

                // FOR EACH FEATURE CHECK IF THE BnB HAS IT
                for (String s : featureSet) {
                    for (String feature: features) {
                        if (s.equals(feature)) {
                            newString += "1,";
                            break;
                        }
                    }
                    newString += "0,";
                }
                newString = newString.substring(0, newString.length() - 1);
                writer.write(newString + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
