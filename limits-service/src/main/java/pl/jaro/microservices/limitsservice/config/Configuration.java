package pl.jaro.microservices.limitsservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties("limits-service")
public class Configuration {
    private int minimum;
    private int maximum;
}
