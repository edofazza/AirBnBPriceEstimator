package com.unipi.dmaml.airbnbpriceestimator.preprocessing.tasks;

import com.unipi.dmaml.airbnbpriceestimator.preprocessing.utils.ColumnHandler;

public class PriceCleaner implements Runnable{

    public void run(){
        ColumnHandler columnHandler = new ColumnHandler();
        columnHandler.removeDollarFromPrice("csv/price.csv", "csv/priceFormatted.csv");
    }
}
