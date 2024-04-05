package pl.jaro.microservices.currencyconversionservice.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@org.springframework.context.annotation.Configuration(proxyBeanMethods = false)
public class Configuration {

    @Bean
    RestTemplate restTemplate(RestTemplateBuilder builder){
        return builder.build();
    }
}
