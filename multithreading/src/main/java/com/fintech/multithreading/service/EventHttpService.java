package com.fintech.multithreading.service;

import com.fintech.multithreading.model.Events;
import java.util.List;

public interface EventHttpService {

    List<Events> getListByApi(String actualSince, String actualUntil);
}
