package com.fintech.parser.hw5.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Locations {

    private String slug;

    private String name;
}