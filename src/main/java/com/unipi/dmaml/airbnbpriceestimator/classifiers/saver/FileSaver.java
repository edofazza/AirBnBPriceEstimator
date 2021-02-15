package com.unipi.dmaml.airbnbpriceestimator.classifiers.saver;

import weka.classifiers.Evaluation;
import weka.core.Attribute;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Enumeration;
import java.util.List;

public class FileSaver {
    private File file;
    private Evaluation results;
    private int foldNum;
    private List<Attribute> chosenAttributes;

    public FileSaver(Evaluation results, String algorithmName, String attributeSelectionName, int fold, List<Attribute> chosen){
        String pathName = "results/" + algorithmName;
        if(attributeSelectionName!=null)
            pathName=pathName + "_With_" + attributeSelectionName;
        file = new File(pathName);
        this.foldNum=fold;
        this.chosenAttributes = chosen;
        this.results=results;
    }

    public void save(){
        try(BufferedWriter bf = Files.newBufferedWriter(Paths.get(file.toURI()), StandardOpenOption.APPEND)){
            bf.write(results.toSummaryString("Results of "+file.getName() + "\tfold n."+ foldNum + "\n\n", false));
            bf.write("\n Selected Features:\n");
            for(Attribute a: chosenAttributes)
                bf.write(a.name() + " : " + a.index() + "\n");
        }catch (IOException ioe){
            ioe.printStackTrace();
        }
    }

}
