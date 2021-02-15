package com.unipi.dmaml.airbnbpriceestimator.classifiers.loaders;

import weka.core.Instances;
import weka.core.converters.CSVLoader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import java.io.File;
import java.io.IOException;

public class DatasetFromCsvLoader {
    private String datasetPath = "csv/preprocessing/csvPreprocessed.csv";
    private String confDataPath = "conf/loaderConf.xml";

    public Instances loadData(){
        Instances data=null;
        try {
            ConfigurationData configurationData = getConfigData();
            CSVLoader loader= new CSVLoader();
                loader.setDateAttributes(configurationData.dateAttributes);
                loader.setDateFormat(configurationData.dateFormat);
                loader.setEnclosureCharacters("\"");
                loader.setFieldSeparator(",");
                loader.setMissingValue("?");
                loader.setNominalAttributes(configurationData.nominalAttributes);
                loader.setNumericAttributes(configurationData.numericAttributes);
                loader.setSource(new File(datasetPath));
                data=loader.getDataSet();
        }catch (Exception e){
            e.printStackTrace();
        }
        return data;
    }

    private ConfigurationData getConfigData() throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(ConfigurationData.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
        ConfigurationData conf = (ConfigurationData) jaxbUnmarshaller.unmarshal(new File(confDataPath));
        System.out.println(conf.dateAttributes + "\n" + conf.dateFormat + "\n" + conf.numericAttributes);
        return conf;
    }

}
