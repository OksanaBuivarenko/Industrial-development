package com.fintech.multithreading.dto.response;

import com.fintech.multithreading.model.Dates;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class EventsRs {

    @Schema(example = "4567")
    private Long id;

    @Schema(example = "[{start: 2017-03-23, end: 2017-03-23}]")
    private List<Dates> dates;

    @Schema(example = "выставка Максима Бойкова «Вепсский лес, или История одного дня»")
    private String title;

    @Schema(example = "от 0 до 200 рублей")
    private String price;
}