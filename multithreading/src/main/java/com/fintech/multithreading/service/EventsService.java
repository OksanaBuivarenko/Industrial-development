package com.fintech.multithreading.service;


import com.fintech.multithreading.dto.request.EventsRq;
import com.fintech.multithreading.dto.response.EventsRs;

import java.util.List;

public interface EventsService {

    List<EventsRs> getEventsRs(EventsRq events);
}
