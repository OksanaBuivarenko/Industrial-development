package com.fintech.parser.hw5.service.memento;

import com.fintech.parser.hw5.model.Categories;
import com.fintech.parser.hw5.repository.CategoriesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoriesHistoryImpl implements CategoriesHistory {

    private final ConcurrentHashMap<Long, Stack<CategoriesMemento>> historyMap = new ConcurrentHashMap<>();

    private final CategoriesRepository categoriesRepository;

    @Override
    public void save(Categories categories) {
        if (!historyMap.containsKey(categories.getId())) {
           historyMap.put(categories.getId(), new Stack<>());
        }
        historyMap.get(categories.getId()).push(categories.save());
    }

    @Override
    public void undo(Categories categories) {
        if (!historyMap.containsKey(categories.getId()) || historyMap.get(categories.getId()).isEmpty()) {
            log.info("История пуста, нечего отменять.");
        }
        else  {
            Categories result = categories.restore(historyMap.get(categories.getId()).pop());
            categoriesRepository.update(result.getId(), result);
        }
    }

    @Override
    public void delete(Long id) {
        historyMap.remove(id);
    }
}
