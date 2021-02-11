package com.unipi.dmaml.airbnbpriceestimator.preprocessing.loaders;

import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.File;
import java.io.IOException;

public class RawDataLoader {
    private static String csvFile="csv/airbnb_dataset10.csv"; //TODO change name of the file

    public Instances loadRawFile(){
        CSVLoader loader= new CSVLoader();
        Instances data;
        try {
            loader.setBufferSize(100);
            loader.setDateAttributes("1");
            loader.setDateFormat("dd/MM/yyyy");
            loader.setEnclosureCharacters("\"");
            loader.setFieldSeparator(";");
            loader.setMissingValue("?");
            loader.setNominalAttributes("2,5,7,8,9,10,12,19,20");
            loader.setNumericAttributes("3,4,6,11,13-18");
            loader.setSource(new File(csvFile));
            data=loader.getDataSet();
            for(int i=0; i<10; i++){
                for(int j=0; j<data.numAttributes(); j++){
                    System.out.println(data.instance(i).toString(j));
                }
                System.out.println("\n ---------------------------------------------- \n");
            }

        }catch (IOException ioe){
            ioe.printStackTrace();
            data=null;
        }
        return data;
    }

}
