package com.fintech.interaction.service;

import java.util.Map;

public interface HttpService<K, V> {
    Map<K, V> getMapByApi();
}