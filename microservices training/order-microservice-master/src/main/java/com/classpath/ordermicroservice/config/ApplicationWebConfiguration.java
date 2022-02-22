package com.classpath.ordermicroservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
class ApplicationWebConfiguration implements WebMvcConfigurer {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer contentNegotiationConfigurer) {

        contentNegotiationConfigurer.favorParameter(true)
                                    .ignoreAcceptHeader(false)
                                    .parameterName("media-type")
                                    .defaultContentType(MediaType.APPLICATION_JSON)
                                    .mediaType("xml", MediaType.APPLICATION_XML)
                                    .mediaType("json", MediaType.APPLICATION_JSON);
    }

}

@Configuration
class AppConfig {

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

  //  @Bean
   /* public WebClient webClient(){
        return WebClient.builder().baseUrl("http://inventory-service")
                .build();
    }*/
}