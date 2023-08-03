package com.example.application.api;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import jakarta.annotation.Nullable;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public OpenAPI apiInfo(@Nullable BuildProperties buildProperties) {
        return new OpenAPI()
                .info(new Info()
                        .title("My API")
                        .version(softwareVersion(buildProperties))
                        .description("""
                                <p>Hello</p>
                                """)
                        .contact(new Contact()
                                .email("Information@something.com")
                        )
                ).externalDocs(
                        new ExternalDocumentation()
                                .description("For more information, please visit the wiki")
                                .url("Some url"));
    }

    private String softwareVersion(@Nullable BuildProperties buildProperties) {
        if (buildProperties != null) {
            return buildProperties.getVersion();
        }
        return null;
    }

    @Bean
    public GroupedOpenApi apiOne() {
        final String[] pathsToInclude = {"/api/clock/**"};
        return GroupedOpenApi.builder()
                .group("API 1")
                .pathsToMatch(pathsToInclude)
                .packagesToScan(ClockController.class.getPackageName())
                .build();
    }
}
