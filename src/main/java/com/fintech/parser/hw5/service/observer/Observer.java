package com.fintech.parser.hw5.service.observer;

import java.util.List;

public interface Observer<T> {

    void handleEvent(List<T> list);
}
