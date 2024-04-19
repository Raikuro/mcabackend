package com.mca.config;

import com.mca.infrastructure.client.ApiClient;
import com.mca.infrastructure.client.api.DefaultApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApiConfig {

    @Value("${saga.api.url}")
    private String sagaApiUrl;

    @Bean
    public DefaultApi gameSagaApi(ApiClient apiClient) {
        return new DefaultApi(apiClient.setBasePath(sagaApiUrl));
    }

    @Bean
    public ApiClient apiClient(WebClient webClient){
        return new ApiClient(webClient);
    }

    @Bean
    public WebClient webClient(){
        return WebClient.builder().build();
    }

    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(false);
        filter.setAfterMessagePrefix("REQUEST DATA: ");
        return filter;
    }

}
