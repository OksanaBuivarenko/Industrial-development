package com.fintech.multithreading.model;

import lombok.Data;

import java.util.List;

@Data
public class EventList {

    private Long count;

    private String next;

    private String previous;

    private List<Events> results;
}
