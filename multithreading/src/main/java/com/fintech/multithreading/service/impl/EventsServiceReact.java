package com.fintech.multithreading.service.impl;


import com.fintech.multithreading.dto.CurrencyDto;
import com.fintech.multithreading.dto.request.EventsRq;
import com.fintech.multithreading.dto.response.EventsRs;
import com.fintech.multithreading.mapper.EventsMapper;
import com.fintech.multithreading.model.Events;
import com.fintech.multithreading.service.EventsService;
import com.fintech.multithreading.service.TimeService;
import jakarta.annotation.Priority;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.springframework.core.Ordered.LOWEST_PRECEDENCE;

@Slf4j
@Priority(LOWEST_PRECEDENCE)
@Service
@RequiredArgsConstructor
public class EventsServiceReact implements EventsService {

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
        Mono<Double> amount = getAmount(eventsRq);

        Mono<List<Events>> eventList = getEventsList(eventsRq);

        return eventList.zipWith(amount, this::filtered)
                .onErrorContinue(RuntimeException.class,
                        (ex, ignored) -> log.error("Ошибка при получении событий", ex))
                .toFuture().get();
    }

    public List<Events> filtered(List<Events> list, Double budget) {
        return list.stream().filter(event -> getPrice(event.getPrice()) <= budget).collect(Collectors.toList());
    }

    public Double getPrice(String string) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(string);
        if (matcher.find()) {
            return Double.valueOf(matcher.group(0));
        }
        return 0.0;
    }

    public Mono<Double> getAmount(EventsRq events) {
        CurrencyDto currencyDto = CurrencyDto.builder()
                .toCurrency("RUB")
                .fromCurrency(events.getCurrency().toString().toUpperCase())
                .amount(events.getBudget())
                .build();
        return Mono.just(executeConvertCurrency.getAmount(currencyDto));
    }

    public Mono<List<Events>> getEventsList(EventsRq events) {
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
        return executeEventsService.getListByApiMono(dateFrom, dateTo);
    }
}