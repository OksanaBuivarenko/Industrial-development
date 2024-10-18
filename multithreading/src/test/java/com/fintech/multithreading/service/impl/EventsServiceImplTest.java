package com.fintech.multithreading.service.impl;

import com.fintech.multithreading.dto.CurrencyDto;
import com.fintech.multithreading.dto.request.EventsRq;
import com.fintech.multithreading.dto.response.EventsRs;
import com.fintech.multithreading.mapper.EventsMapper;
import com.fintech.multithreading.model.Dates;
import com.fintech.multithreading.model.Events;
import com.fintech.multithreading.service.TimeService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static com.fintech.multithreading.enumeration.CurrencyType.eur;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class EventsServiceImplTest {
    private final ExecuteEventsService executeEventsService = Mockito.mock(ExecuteEventsService.class);

    private final ExecuteConvertCurrency executeConvertCurrency = Mockito.mock(ExecuteConvertCurrency.class);

    private final EventsMapper eventsMapper = Mockito.mock(EventsMapper.class);

    private final TimeService timeService = Mockito.mock(TimeService.class);

    private final EventsServiceImpl eventsService = new EventsServiceImpl(executeEventsService,
            executeConvertCurrency, eventsMapper, timeService);

    private EventsRq eventsRq;
    private List<Events> eventsList;
    private Events events;
    private EventsRs eventsRs;
    private CurrencyDto currencyDto;

    @BeforeEach
    void setUp() {
        eventsRs = new EventsRs(1L, List.of(new Dates()),"title", "price");
        events =  new Events(1L, "title", new ArrayList<>(), "price 100");
        eventsRq =  EventsRq.builder()
                .budget(10.0)
                .currency(eur).build();
        eventsList = new ArrayList<>();
        eventsList.add(events);
        currencyDto = CurrencyDto.builder()
                .toCurrency("RUB")
                .fromCurrency("EUR")
                .amount(10.0)
                .build();
    }

    @AfterEach
    void tearDown() {
        eventsRs = null;
        events =  null;
        eventsRq =  null;
        eventsList = null;
        currencyDto = null;
    }

    @Test
    void getEventsRs() {
        when(eventsMapper.toDto(events)).thenReturn(eventsRs);
        when(executeEventsService.getListByApi("", "")).thenReturn(eventsList);
        when(timeService.getNow()).thenReturn("");
        when(timeService.getPlusWeek()).thenReturn("");
        when(executeConvertCurrency.getAmount(currencyDto)).thenReturn(970.0);

        List<EventsRs> result = eventsService.getEventsRs(eventsRq);

        assertEquals(eventsRs, result.get(0));
        assertEquals(1, result.size());
        verify(eventsMapper, times(1)).toDto(events);
    }

    @Test
    void getFiltredEventsList() {
        when(executeEventsService.getListByApi("", "")).thenReturn(eventsList);
        when(timeService.getNow()).thenReturn("");
        when(timeService.getPlusWeek()).thenReturn("");
        when(executeConvertCurrency.getAmount(currencyDto)).thenReturn(970.0);

        List<Events> result = eventsService.getFiltredEventsList(eventsRq);

        assertEquals(events, result.get(0));

    }

    @Test
    void getPrice() {
        assertEquals(100, eventsService.getPrice("От 100 до 500"));
    }

    @Test
    void getAmount() {
        when(executeConvertCurrency.getAmount(currencyDto)).thenReturn(970.0);

        assertEquals(970.0, eventsService.getAmount(eventsRq));
    }

    @Test
    void getEventsList() {
        when(executeEventsService.getListByApi("", "")).thenReturn(eventsList);
        when(timeService.getNow()).thenReturn("");
        when(timeService.getPlusWeek()).thenReturn("");

        List<Events> result = eventsService.getEventsList(eventsRq);

        assertEquals(events, result.get(0));
        assertEquals(1, result.size());
        verify(executeEventsService, times(1)).getListByApi("", "");
    }
}