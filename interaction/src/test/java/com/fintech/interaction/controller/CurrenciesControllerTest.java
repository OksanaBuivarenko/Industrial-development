package com.fintech.interaction.controller;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fintech.interaction.dto.request.ConvertCurrenciesRq;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;

@TestPropertySource(properties = {
        "cache.enabled=false"
})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CurrenciesControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    MockMvc mockMvc;

    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("cbr.base_url", wireMockServer::baseUrl);
    }

    @Test
    void getRatesByCodeWithStatus200() throws Exception {
        stubFor200();

        this.mockMvc.perform(get("http://localhost:" + port + "/currencies/rates/AUD"))
                .andDo(print())
                .andExpectAll(status().isOk())
                .andExpect(jsonPath("$.currency").value("AUD"))
                .andExpect(jsonPath("$.rate").value("64.7671"))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getRatesByCodeWithStatus404() throws Exception {
       stubFor200();

        this.mockMvc.perform(get("http://localhost:" + port + "/currencies/rates/AUDAUD"))
                .andDo(print())
                .andExpectAll(status().isNotFound())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("Currency with code AUDAUD was not found in the Central Bank"))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getRatesByCodeWithStatus503() throws Exception {
        stubFor503();

        this.mockMvc.perform(get("http://localhost:" + port + "/currencies/rates/AUD"))
                .andDo(print())
                .andExpectAll(status().isServiceUnavailable())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void convertCurrenciesWithStatus200() throws Exception {
        stubFor200();

        this.mockMvc.perform(post("http://localhost:" + port + "/currencies/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createConvertRq("USD", "AZN", 10.0)))
                .andDo(print())
                .andExpectAll(status().isOk())
                .andExpect(jsonPath("$.fromCurrency").value("USD"))
                .andExpect(jsonPath("$.toCurrency").value("AZN"))
                .andExpect(jsonPath("$.convertedAmount").value("17.0"));
    }

    @Test
    void convertCurrenciesWithStatus404IsNotFoundInCBCurrency() throws Exception {
        stubFor200();

        this.mockMvc.perform(post("http://localhost:" + port + "/currencies/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createConvertRq("GNF", "AZN", 10.0)))
                .andDo(print())
                .andExpectAll(status().isNotFound())
                .andExpect(jsonPath("$.code").value("404"))
                .andExpect(jsonPath("$.message").value("Currency with code GNF was not found in the Central Bank"))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void convertCurrenciesWithStatus400IsNotExistingCurrenciesAndNullAmount() throws Exception {
        this.mockMvc.perform(post("http://localhost:" + port + "/currencies/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createConvertRq("USDUSD", "AZN", 10.0)))
                .andDo(print())
                .andExpectAll(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value("Field fromCurrency must contain an " +
                        "existing currency code" + System.lineSeparator()))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void convertCurrenciesWithStatus400IsEmptyFieldsFromCurrency() throws Exception {
        this.mockMvc.perform(post("http://localhost:" + port + "/currencies/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createConvertRq("", "USD", 10.0)))
                .andDo(print())
                .andExpectAll(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value( "Field fromCurrency must not be blank"
                        + System.lineSeparator()))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void convertCurrenciesWithStatus400IsEmptyFieldsToCurrency() throws Exception {
        this.mockMvc.perform(post("http://localhost:" + port + "/currencies/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createConvertRq("USD", "", 10.0)))
                .andDo(print())
                .andExpectAll(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value( "Field toCurrency must not be blank"
                        + System.lineSeparator()))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void convertCurrenciesWithStatus400AmountIsNull() throws Exception {
        this.mockMvc.perform(post("http://localhost:" + port + "/currencies/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createConvertRq("USD", "AUD", null)))
                .andDo(print())
                .andExpectAll(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value( "Field amount must not be null"
                        + System.lineSeparator()))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void convertCurrenciesWithStatus400AmountIsNotPositive() throws Exception {
        this.mockMvc.perform(post("http://localhost:" + port + "/currencies/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createConvertRq("USD", "AUD", 0.0)))
                .andDo(print())
                .andExpectAll(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("400"))
                .andExpect(jsonPath("$.message").value( "Field amount must be greater than 0"
                        + System.lineSeparator()))
                .andExpect(jsonPath("$.length()").value(2));
    }

    private String createConvertRq(String from, String to, Double amount) throws JsonProcessingException {
        ConvertCurrenciesRq convertCurrenciesRq = ConvertCurrenciesRq.builder()
                .fromCurrency(from)
                .toCurrency(to)
                .amount(amount)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(convertCurrenciesRq);
    }

    private void stubFor200() {
        wireMockServer.stubFor(WireMock.get(urlEqualTo("/daily_utf8.xml"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/xml")
                        .withBodyFile("cbr.xml")));
    }

    private void stubFor503() {
        wireMockServer.stubFor(WireMock.get(urlEqualTo("/daily_utf8.xml"))
                .willReturn(aResponse()
                        .withStatus(503)));
    }
}