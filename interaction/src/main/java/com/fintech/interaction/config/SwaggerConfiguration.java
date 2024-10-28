package com.fintech.interaction.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@OpenAPIDefinition(
        info = @Info(
                title = "Interaction application api",
                version = "1.0"),
        servers = @Server(
                description = "localhost",
                url = "http://localhost:8888/")
)
public class SwaggerConfiguration {
}