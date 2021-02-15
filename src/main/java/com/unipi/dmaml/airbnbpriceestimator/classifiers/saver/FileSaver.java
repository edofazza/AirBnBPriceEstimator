package com.unipi.dmaml.airbnbpriceestimator.classifiers.saver;

import weka.classifiers.Evaluation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileSaver {
    private File file;
    private Evaluation results;

    public FileSaver(Evaluation results, String algorithmName, String attributeSelectionName){
        String pathName = "results/" + algorithmName;
        if(attributeSelectionName!=null)
            pathName=pathName + "_With_" + attributeSelectionName;
        file = new File(pathName);
        this.results=results;
    }

    public void save(){
        try(BufferedWriter bf = Files.newBufferedWriter(Paths.get(file.toURI()))){
            bf.write(results.toSummaryString("Results of "+file.getName() +"\n\n", true));
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

}
