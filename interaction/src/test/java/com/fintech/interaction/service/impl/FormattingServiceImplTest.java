package com.fintech.interaction.service.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FormattingServiceImplTest {

    FormattingServiceImpl formattingService = new FormattingServiceImpl();

    @Test
    void formatting() {
        assertEquals(17.0, formattingService.formatting(16.999765));
    }
}