package com.unipi.dmaml.airbnbpriceestimator.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class ColumnHandler {
    public void bathroomColumns(String path, String newPath) {
        clearFile(newPath);

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(newPath), StandardOpenOption.APPEND);
             BufferedReader br = new BufferedReader(new FileReader(path))
        ) {
            // FOR LINE FOR HEADERS
            writer.write("numberOfBathrooms,shared\n");

            // GET EACH LINE AND CREATE THE NEW LINE FROM IT
            String line;
            br.readLine(); //WE REMOVE THE HEADER IN THIS WAY
            while((line = br.readLine())!=null) {
                // IF EMPTY = ? THEN BOTH OF THE COLUMNS WILL BE EMPTY = ?
                if (line.equals("?")) {
                    writer.write("?,?\n");
                    continue;
                }

                // WE CHECK THE NUMBER OF BATHROOMS
                String numberOfBath;
                if (line.contains(".5"))
                    numberOfBath = line.substring(0, 3);
                else
                    numberOfBath = line.substring(0, 1);

                // WE CHECK IF THE BATH IS SHARED
                boolean shared = false;
                if (line.contains("shared"))
                    shared = true;

                if (shared)
                    writer.write(numberOfBath + "," + 1 + "\n");
                else
                    writer.write(numberOfBath + "," + 0 + "\n");
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
}
