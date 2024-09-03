package com.fintech.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
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
        } catch (IOException e) {
            log.error("Json file with name {} cannot be deserialized. \n {}", filePath, e.getMessage());
        }
        return city;
    }

    public void toXML(City city) {
        ObjectMapper objectMapperToXML = new XmlMapper();
        try {
            objectMapperToXML.writeValue(new File("city.xml"), city);
            log.info("Object city with slug {} serialized successfully to xml.", city.getSlug());
        } catch (IOException e) {
            log.info("Object city with slug {} cannot be serialized  to xml. \n {}", city.getSlug(), e.getMessage());
        }
    }
}