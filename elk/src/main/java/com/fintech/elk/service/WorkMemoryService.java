package com.fintech.elk.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
@Slf4j
@Service
public class WorkMemoryService {

    static final List<Object[]> arrays = new LinkedList<>();

    public void recursivePrint(int num) {
        if(num == 0)
            return;
        else
            recursivePrint(++num);
    }

    public void grabMemory() {
        while (true){
            arrays.add(new Object[100]);
        }
    }
}
