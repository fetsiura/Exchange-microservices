package pl.jaro.microservices.limitsservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.jaro.microservices.limitsservice.config.Configuration;
import pl.jaro.microservices.limitsservice.model.Limit;

@RestController
@RequiredArgsConstructor
public class LimitsController {
    private final Configuration configuration;

    @GetMapping("/limits")
    public Limit limits() {
        return new Limit(configuration.getMinimum(), configuration.getMaximum());
    }
}
