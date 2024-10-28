package com.fintech.parser.hw5.service.memento;

import com.fintech.parser.hw5.model.Locations;
import com.fintech.parser.hw5.repository.LocationsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class LocationHistoryImpl implements LocationHistory{
    private final ConcurrentHashMap<String, Stack<LocationsMemento>> historyMap = new ConcurrentHashMap<>();
    private final LocationsRepository locationsRepository;



    @Override
    public void save(Locations locations) {
        if (!historyMap.containsKey(locations.getSlug())) {
            historyMap.put(locations.getSlug(), new Stack<>());
        }
        historyMap.get(locations.getSlug()).push(locations.save());
    }

    @Override
    public void undo(Locations locations) {
        if (!historyMap.containsKey(locations.getSlug()) || historyMap.get(locations.getSlug()).isEmpty()) {
           log.info("История пуста, нечего отменять.");
        }
        else  {
            Locations result = locations.restore(historyMap.get(locations.getSlug()).pop());
            locationsRepository.update(result.getSlug(), result);
        }
    }

    @Override
    public void delete(String id) {
        historyMap.remove(id);
    }
}
