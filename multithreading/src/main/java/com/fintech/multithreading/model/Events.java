package com.fintech.multithreading.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Events {

    private Long id;

    private String title;

    private List<Dates> dates;

    private String price;
}
