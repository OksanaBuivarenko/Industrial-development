package com.fintech.parser;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ParserApplication {
    public static void main(String[] args) {
        Parser parser = new Parser();
        City city = parser.toObject("city.json");
        parser.toObject("city-error.json");
        parser.toXML(city);
    }
}