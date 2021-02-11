package com.unipi.dmaml.airbnbpriceestimator.preprocessing.tasks;

import com.unipi.dmaml.airbnbpriceestimator.preprocessing.utils.ColumnHandler;

public class BathroomCleaner implements Runnable{
    public void run(){
        ColumnHandler columnHandler = new ColumnHandler();
        columnHandler.bathroomColumns("csv/bathrooms.csv", "csv/bathroomsFormatted.csv");
    }
}
