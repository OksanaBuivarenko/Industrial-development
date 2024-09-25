package com.fintech.parser.hw5.service;

import java.util.List;

public interface HttpService<T> {
    List<T> getListByApi();
}
