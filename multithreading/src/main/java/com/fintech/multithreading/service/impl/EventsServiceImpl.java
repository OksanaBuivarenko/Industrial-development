package com.fintech.multithreading.service.impl;


import com.fintech.multithreading.dto.CurrencyDto;
import com.fintech.multithreading.dto.request.EventsRq;
import com.fintech.multithreading.dto.response.EventsRs;
import com.fintech.multithreading.mapper.EventsMapper;
import com.fintech.multithreading.model.Events;
import com.fintech.multithreading.service.EventsService;
import com.fintech.multithreading.service.TimeService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventsServiceImpl implements EventsService {

    private final ExecuteEventsService executeEventsService;

    private final ExecuteConvertCurrency executeConvertCurrency;

    private final EventsMapper eventsMapper;

    private final TimeService timeService;

    @Override
    public List<EventsRs> getEventsRs(EventsRq eventsRq) {
        return getFiltredEventsList(eventsRq).stream().map(eventsMapper::toDto).collect(Collectors.toList());
    }

    @SneakyThrows
    public List<Events> getFiltredEventsList(EventsRq eventsRq) {
        CompletableFuture<Double> completableFuture1 = CompletableFuture
                .supplyAsync(() -> getAmount(eventsRq));

        CompletableFuture<List<Events>> completableFuture2 = CompletableFuture
                .supplyAsync(() -> getEventsList(eventsRq));

        List<Events> result = new ArrayList<>();
        BiConsumer<Double, List<Events>> stringBiConsumer = (budget, eventList) ->
                eventList.stream()
                        .filter(event -> getPrice(event.getPrice()) <= budget)
                        .map(result::add)
                        .collect(Collectors.toList());

        completableFuture1.thenAcceptBoth(completableFuture2, stringBiConsumer).get();

        return result;
    }

    public Double getPrice(String string) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            return Double.valueOf(matcher.group(0));
        }
        return 0.0;
    }

    public Double getAmount(EventsRq events) {
        CurrencyDto currencyDto = CurrencyDto.builder()
                .toCurrency("RUB")
                .fromCurrency(events.getCurrency().toString().toUpperCase())
                .amount(events.getBudget())
                .build();
        return executeConvertCurrency.getAmount(currencyDto);
    }

    public List<Events> getEventsList(EventsRq events) {
        String dateFrom = "";
        String dateTo = "";
        if (events.getDateFrom() == null && events.getDateTo() == null) {
            dateFrom = timeService.getNow();
            dateTo = timeService.getPlusWeek();
        }
        if (events.getDateFrom() != null) {
            dateFrom = String.valueOf(events.getDateFrom().getTime() / 1000);
        }
        if (events.getDateTo() != null) {
            dateTo = String.valueOf(events.getDateTo().getTime() / 1000);
        }
        return executeEventsService.getListByApi(dateFrom, dateTo);
    }
}