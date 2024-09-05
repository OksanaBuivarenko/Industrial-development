package com.fintech.parser;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ParserApplication {
    public static void main(String[] args) {
        Parser parser = new Parser();
        parser.toXML("city.json");
        parser.toXML("city-error.json");
        parser.toXML("error.json");
    }
}