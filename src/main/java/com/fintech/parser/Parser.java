package com.fintech.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Slf4j
@NoArgsConstructor
public class Parser {

    public City toObject(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();
        City city = null;
        try {
            city = objectMapper.readValue(new File(filePath), City.class);
            log.info("Json file with name {} deserialized successfully.", filePath);
            log.debug("Object City with slug {} created successfully.", city.getSlug());
        } catch (FileNotFoundException e) {
            log.warn("Json file with name {} cannot be found.", filePath);
        } catch (IOException e) {
            log.error("Json file with name {} cannot be deserialized. \n {}", filePath, e.getMessage());
        }
        return city;
    }

    public void toXML(String filePath) {
        City city = toObject(filePath);
        if(city!=null) {
            ObjectMapper objectMapperToXML = new XmlMapper();
            try {
                objectMapperToXML.writeValue(new File("city.xml"), city);
                log.info("Json file with name {} serialized successfully to xml.", filePath);
            } catch (Exception e) {
                log.error("Json file with name {} cannot be serialized to xml. \n {}", filePath, e.getMessage());
            }
        }
    }
}