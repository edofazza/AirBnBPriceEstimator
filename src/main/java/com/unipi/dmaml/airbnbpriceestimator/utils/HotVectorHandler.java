package com.unipi.dmaml.airbnbpriceestimator.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class HotVectorHandler {
    public void mergeColumns(String path, String newPath) {
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
                    "Safe",
                    "standard cable");





            // PRINT CSV
            String[] clearedHeaders = columnHashMap.keySet().toArray(new String[0]);
            Arrays.sort(clearedHeaders);
            String headerLine = String.join(",", clearedHeaders);
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

    public void clearFile(String path) {
        try (Writer fileWriter = new FileWriter(path, false)) {
            fileWriter.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HotVectorHandler tmp = new HotVectorHandler();
        tmp.mergeColumns("csv/result.csv", "csv/preprocessing/amenitiesMerged.csv");
    }
}
