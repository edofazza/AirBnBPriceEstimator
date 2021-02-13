package com.unipi.dmaml.airbnbpriceestimator.preprocessing.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ColumnHandler {
    public void bathroomColumns(String path, String newPath) {
        clearFile(newPath);

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(newPath), StandardOpenOption.APPEND);
             BufferedReader br = new BufferedReader(new FileReader(path))
        ) {
            // FOR LINE FOR HEADERS
            writer.write("bathrooms,bathroomsShared\n");

            // GET EACH LINE AND CREATE THE NEW LINE FROM IT
            String line;
            br.readLine(); //WE REMOVE THE HEADER IN THIS WAY
            while((line = br.readLine())!=null) {
                // IF EMPTY = ? THEN BOTH OF THE COLUMNS WILL BE EMPTY = ?
                if (line.equals("?")) {
                    writer.write("1,0\n");
                    continue;
                }

                // WE CHECK THE NUMBER OF BATHROOMS
                String numberOfBath;
                if (line.contains(".5"))
                    numberOfBath = line.substring(0, 3);
                else if (line.startsWith("S"))
                    numberOfBath = "1";
                else if (line.startsWith("H"))
                    numberOfBath = "0.5";
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

    public void removeDollarFromPrice(String path, String newPath) {
        clearFile(newPath);

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(newPath), StandardOpenOption.APPEND);
             BufferedReader br = new BufferedReader(new FileReader(path))
        ) {
            // GET EACH LINE AND CREATE THE NEW LINE FROM IT
            String line;

            writer.write(br.readLine() + "\n");
            while((line = br.readLine())!=null) {
                // IF EMPTY = ? THEN BOTH OF THE COLUMNS WILL BE EMPTY = ?
                if (line.equals("?")) {
                    writer.write("?\n");
                    continue;
                }

                writer.write(line.replace("$", "") + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reviewScoreRating(String path, String newPath) {
        clearFile(newPath);

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(newPath), StandardOpenOption.APPEND);
        ) {
            BufferedReader br = new BufferedReader(new FileReader(path));

            String line;
            int minimumMark = 9999;

            // GET THE MINIMUM
            br.readLine(); // REMOVE THE HEADER
            while((line = br.readLine())!=null) {
                // IF EMPTY = ? THEN BOTH OF THE COLUMNS WILL BE EMPTY = ?
                if (line.equals("?"))
                    continue;

                int parsedInt = Integer.parseInt(line);
                minimumMark = minimumMark > parsedInt ? parsedInt : minimumMark;
            }
            minimumMark--;
            br.close();

            br = new BufferedReader(new FileReader(path));
            // GET EACH LINE AND CREATE THE NEW LINE FROM IT
            writer.write(br.readLine() + "\n");
            while((line = br.readLine())!=null) {
                // IF EMPTY = ? THEN BOTH OF THE COLUMNS WILL BE EMPTY = ?
                if (line.equals("?")) {
                    writer.write(minimumMark + "\n");
                    continue;
                }

                writer.write(line + "\n");
            }
            br.close();
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
