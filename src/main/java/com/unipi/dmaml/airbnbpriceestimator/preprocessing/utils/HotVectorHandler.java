package com.unipi.dmaml.airbnbpriceestimator.preprocessing.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class HotVectorHandler {
    public void operate(String path, String newPath) {
        clearFile(newPath);

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(newPath), StandardOpenOption.APPEND);
             BufferedReader br = new BufferedReader(new FileReader(path))
        ) {
          // TRANSFORM EACH COLUMN IN A VECTOR
            Map<String, List<String>> columnHashMap = new HashMap<>();

            String line = br.readLine(); // IT CONTAINS THE LINE WITH ALL THE HEADER
            String[] headers = line.split(",");

            // INSERT ALL THE HEADERS IN THE MAP
            for (String header: headers)
                columnHashMap.put(header, new ArrayList<>());

            // SINGLE VECTORS CREATION
            while((line = br.readLine())!=null) {
                // TAKE THE VALUES
                String[] values = line.split(",");

                // INSERT THE VALUES
                for (int i = 0; i < headers.length; i++)
                    columnHashMap.get(headers[i]).add(values[i]);
            }

            removeColumns(columnHashMap,
                    " THERE IS A CELLING FAN AN A PORTABLE AC IN THE BEDROOM conditioner",
                    "Limited housekeeping \\u2014 on request",
                    "Safe");


            // MERGE COLUMNS
            mergeColumnsThatContainSpecificWords(columnHashMap, "Toiletries", "toiletries");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Stove", "stove");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Refrigerator", "refrigerator");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Sound system", "sound system");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Linens", "linens");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Breakfast", "breakfast");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Air conditioning", "air conditioner", "air conditioning");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Dryer", "dryer");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Extra pillows", "pillow");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Parking", "parking");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Wifi", "wifi");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Oven", "oven");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Garden", "garden");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Heating", "heating");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Onsite restaurant", "restaurant");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Kitchen", "kitchen", "kitchenette");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Onsite bar", "bar");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Pool", "pool");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Washer", "washer");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Hot water", "hot water", "hot tub");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Gym", "fitness", "gym");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Coffee machine", "coffe", "Nespresso");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Body soap", "soap", "shower gel");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Body Shampoo", "shampoo");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Body conditioner", "conditioner");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Garage", "garage");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Freezer", "freezer");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Amazon Prime Video", "Amazon");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Chromecast", "chromecast");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Netflix", "netflix");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Premium cable", "premium cable");
            mergeColumnsThatContainSpecificWords(columnHashMap, "TV", "TV", "standard cable");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Dinnerware", "dinnerware", "dishes", "cooking basics");
            mergeColumnsThatContainSpecificWords(columnHashMap, "Crib", "crib");

            // PRINT CSV
            String[] clearedHeaders = columnHashMap.keySet().toArray(new String[0]);

            String[] underscoreHeaders = new String[clearedHeaders.length];
            // PUT UNDERSCORE INSTEAD OF SPACE
            for (int i = 0; i < clearedHeaders.length; i++)
                underscoreHeaders[i] = clearedHeaders[i].replace(" ", "_");

            // SORT THE TWO ARRAYS, THE ORDER BETWEEN THE TWO WILL BE THE SAME
            // THE COLUMNS THAT START WITH A SPACE ARE ALREADY ELIMINATED OR MERGED
            Arrays.sort(clearedHeaders);
            Arrays.sort(underscoreHeaders);

            String headerLine = String.join(",", underscoreHeaders);
            writer.write(headerLine + "\n");

            // THE NUMBER OF LINES THAT I NEED TO PRINT IS EQUAL TO THE NUMBER OF VALUES INSIDE
            // A SINGLE LIST
            for (int i = 0; i < columnHashMap.get(clearedHeaders[0]).size(); i++) {
                // I LOOP PER LINE
                String lineToPrint = new String();
                for (String clearedHeader: clearedHeaders) {
                    lineToPrint += columnHashMap.get(clearedHeader).get(i) + ",";
                }
                lineToPrint = lineToPrint.substring(0, lineToPrint.length() - 1);
                writer.write(lineToPrint + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeColumns(Map<String, List<String>> map, String... headers) {
        for (String h: headers)
            map.remove(h);
    }

    public void mergeColumnsThatContainSpecificWords(Map<String, List<String>> map, String newHeader, String... headers) {
        // TAKE ALL THE HEADERS
        String[] clearedHeaders = map.keySet().toArray(new String[0]);

        // SELECT ONLY THE PROPER HEADERS THAT NEED TO BE MERGED
        List<String> headersToMerge = new ArrayList<>();

        for (String clearedHeader: clearedHeaders) {
            // CHECK IF THE HEADER SELECTED IS PRESENT IN THE ONES THAT I WANT TO MERGE
            for (String h: headers) {
                if (clearedHeader.toLowerCase(Locale.ROOT).contains(h.toLowerCase(Locale.ROOT))) {
                    headersToMerge.add(clearedHeader);
                    break;
                }
            }
        }
        System.out.println(newHeader + "-> HEADERS MERGED: " + String.join(", ", headersToMerge.toArray(new String[0])));

        // TAKE THE VECTORS AND SUM THEM
        List<List<String>> vectors = new ArrayList<>();
        int length = map.get(headersToMerge.get(0)).size();

        for (String h: headersToMerge) {
            vectors.add(map.get(h));
        }

        int[] sumVector = new int[length];
        Arrays.fill(sumVector, 0);

        for (List<String> vector: vectors) {
            // TRANSFORM THE STRING LIST IN INTEGER LIST
            List<Integer> tmpVector = new ArrayList<>();

            for (String s: vector)
                tmpVector.add(Integer.parseInt(s));

            // SUM
            for (int i = 0; i < length; i++)
                sumVector[i] += tmpVector.get(i);
        }

        // IF 2 OR MORE, CHANGE IN 1
        for (int i = 0; i < length; i++)
            sumVector[i] = sumVector[i] > 0 ? 1 : 0;


        // TRANSFORM THE SUM VECTOR INTO A STRING LIST
        List<String> newColumn = new ArrayList<>();
        for (int i: sumVector) {
            newColumn.add(Integer.toString(i));
        }

        // REMOVE THE headersToMerge FROM THE MAP
        for (String s: headersToMerge)
            map.remove(s);

        // ADD THE NEW VECTOR IN THE MAP
        map.put(newHeader, newColumn);
    }

    public void clearFile(String path) {
        try (Writer fileWriter = new FileWriter(path, false)) {
            fileWriter.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HotVectorHandler tmp = new HotVectorHandler();
        tmp.operate("csv/result.csv", "csv/preprocessing/amenitiesMerged.csv");
    }
}
