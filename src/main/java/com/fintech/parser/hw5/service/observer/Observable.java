package com.fintech.parser.hw5.service.observer;

import java.util.List;

public interface Observable<T> {

    void addObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObserver(List<T> locationsList);
}
