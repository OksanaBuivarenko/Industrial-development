package com.fintech.parser.hw5.repository;

import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public abstract class AppRepository <K, V> {

    private ConcurrentHashMap<K, V> appStorage = new ConcurrentHashMap<>();

    public List<V> findAll() {
        return new ArrayList<>(appStorage.values());
    }

    public Optional<V> findById(K key) {
        return Optional.ofNullable(appStorage.get(key));
    }

    public void save(K key, V value) {
        appStorage.put(key, value);
    }

    public V update(K key, V value) {
       return appStorage.computeIfPresent(key, (k,v) -> v = value);
    }

    public void delete(K key) {
        appStorage.remove(key);
    }

    public Boolean containsId(K key) {
        return appStorage.containsKey(key);
    }
}