package com.kbarcenas.automation.main.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppPropertiesConfig {
    public @Value("${base.uri}") String baseUri;

}
