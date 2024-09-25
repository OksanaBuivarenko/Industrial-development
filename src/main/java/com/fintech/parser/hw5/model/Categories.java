package com.fintech.parser.hw5.model;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class Categories {

    private Long id;

    private String slug;

    private String name;
}