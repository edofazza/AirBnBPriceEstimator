package com.unipi.dmaml.airbnbpriceestimator.classifiers.loaders;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ConfigurationData {
    String dateAttributes;
    String dateFormat;
    String nominalAttributes;
    String numericAttributes;

    public String getDateAttributes() {
        return dateAttributes;
    }

    public String getDateFormat() {
        return dateFormat;
    }

    public String getNominalAttributes() {
        return nominalAttributes;
    }

    public String getNumericAttributes() {
        return numericAttributes;
    }

    @XmlElement
    public void setDateAttributes(String dateAttributes) {
        this.dateAttributes = dateAttributes;
    }

    @XmlElement
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @XmlElement
    public void setNominalAttributes(String nominalAttributes) {
        this.nominalAttributes = nominalAttributes;
    }

    @XmlElement
    public void setNumericAttributes(String numericAttributes) {
        this.numericAttributes = numericAttributes;
    }
}
