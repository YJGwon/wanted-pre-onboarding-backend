package com.wanted.preonboarding.common.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {

        final Info info = new Info()
                .title("API 문서")
                .description("원티드 프리온보딩 챌린지 - 권예진");

        final String tokenSchemaName = "access token";
        final SecurityRequirement tokenRequirement = new SecurityRequirement().addList(tokenSchemaName);
        final Components components = new Components()
                .addSecuritySchemes(tokenSchemaName, new SecurityScheme()
                        .name(tokenSchemaName)
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));

        return new OpenAPI()
                .info(info)
                .addSecurityItem(tokenRequirement)
                .components(components);
    }
}
