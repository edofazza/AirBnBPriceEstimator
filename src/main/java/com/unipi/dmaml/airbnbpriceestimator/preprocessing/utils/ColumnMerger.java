package com.unipi.dmaml.airbnbpriceestimator.preprocessing.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class ColumnMerger {
    public void createMergedCsv(String newFile, String... csvPaths) {
        clearFile(newFile);

        Map<String, List<String>> columnHashMap = new HashMap<>();

        for (String csv: csvPaths) {
            try (
                 BufferedReader br = new BufferedReader(new FileReader(csv))
            ) {
                // TRANSFORM EACH COLUMN IN A VECTOR
                String line = br.readLine(); // IT CONTAINS THE LINE WITH ALL THE HEADER
                String[] headers = line.split(",|;");

                // INSERT ALL THE HEADERS IN THE MAP
                // THE DATA IS IMPUTED WITH A CERTAIN ORDER:
                // 1. the manually formatted
                // 2. all the column modification we made
                for (String header: headers) {
                    String[] tmpHeaders = columnHashMap.keySet().toArray(new String[0]);
                    for (String s: tmpHeaders)
                        if (s.toLowerCase(Locale.ROOT).equals(header.toLowerCase(Locale.ROOT)))
                            columnHashMap.remove(s);
                    columnHashMap.put(header, new ArrayList<>());
                }

                // SINGLE VECTORS CREATION
                while((line = br.readLine())!=null) {
                    // TAKE THE VALUES
                    String[] values = line.split(";|,");

                    // INSERT THE VALUES
                    for (int i = 0; i < headers.length; i++)
                        columnHashMap.get(headers[i]).add(values[i]);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(newFile), StandardOpenOption.APPEND)) {
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

    public void clearFile(String path) {
        try (Writer fileWriter = new FileWriter(path, false)) {
            fileWriter.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ColumnMerger columnMerger = new ColumnMerger();
        columnMerger.createMergedCsv("csv/preprocessing/csvPreprocessed.csv",
                "csv/airbnb_dataset_preprocessed.csv",
                "csv/bathroomsFormatted.csv",
                "csv/priceFormatted.csv",
                "csv/preprocessing/amenitiesMerged.csv"
                );
    }
}
