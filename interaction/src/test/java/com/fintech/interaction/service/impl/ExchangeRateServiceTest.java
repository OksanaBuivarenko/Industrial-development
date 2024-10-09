package com.fintech.interaction.service.impl;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ExchangeRateServiceTest {
    @Autowired
    private ExchangeRateService exchangeRateService;

    @RegisterExtension
    static WireMockExtension wireMockServer = WireMockExtension.newInstance()
            .options(wireMockConfig().dynamicPort())
            .build();

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("cbr.base_url", wireMockServer::baseUrl);
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

    @Test
    void getListByApiSuccess() {
        stubFor200();
        assertEquals(43, exchangeRateService.getListByApi().size());
    }

    @Test
    void getListByApiFail() {
        stubFor503();
        Exception ex = assertThrows(WebClientResponseException.class,() -> exchangeRateService.getListByApi());
        assertTrue(ex.getMessage().contains("503 Service Unavailable from GET"));
    }
}