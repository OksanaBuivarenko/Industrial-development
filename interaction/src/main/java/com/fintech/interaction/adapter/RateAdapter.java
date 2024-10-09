package com.fintech.interaction.adapter;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class RateAdapter extends XmlAdapter<String, Double> {

    @Override
    public Double unmarshal(String value) {
        return Double.valueOf(value.replace(",", "."));
    }

    @Override
    public String marshal(Double value) {
        if (value == null) {
            return null;
        }
        return (javax.xml.bind.DatatypeConverter.printDouble(value));
    }
}