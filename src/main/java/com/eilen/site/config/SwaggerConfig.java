package com.eilen.site.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("****系统API")
                        .description("****系统 Restfull API")
                        .version("v0.0.1")
                        .termsOfService("https://www.jianshu.com/u/c8c1e5ac61e0")
                        .license(new License().name("****").url("https://www.jiahetng.com")))
                .externalDocs(new ExternalDocumentation()
                        .description("**** Issues Documentation")
                        .url("https://****"));
    }
}
