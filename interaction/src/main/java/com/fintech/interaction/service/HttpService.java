package com.fintech.interaction.service;

import java.util.List;

public interface HttpService<T> {
    List<T> getListByApi();
}