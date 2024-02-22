package com.synoverge.leave.portal.config;

import io.swagger.annotations.ApiKeyAuthDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(name = "bearerAuth", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "Bearer")
public class OpenApiConfig {

    public static final String AUTHORIZATION_HEADER = "Authorization";


    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI().info(new Info().title("Your API Title")
                        .description("Your API Description")
                        .version("1.0"));
    }


}
