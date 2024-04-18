package com.mca.config;

import com.mca.infrastructure.client.api.DefaultApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfig {

    @Bean
    public DefaultApi gameSagaApi() {
        return new DefaultApi();
    }

}
