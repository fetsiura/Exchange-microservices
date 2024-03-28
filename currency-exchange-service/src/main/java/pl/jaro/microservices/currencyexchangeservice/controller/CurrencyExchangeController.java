package pl.jaro.microservices.currencyexchangeservice.controller;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.jaro.microservices.currencyexchangeservice.core.currency.CurrencyExchange;
import pl.jaro.microservices.currencyexchangeservice.core.currency.CurrencyExchangeRepository;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CurrencyExchangeController {
    private final CurrencyExchangeRepository currencyExchangeRepository;

    private final Environment environment;

    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    @CircuitBreaker(name = "retrieve-exchange", fallbackMethod = "errorMessage")
    @RateLimiter(name = "retrieve-exchange")
    @Bulkhead(name = "retrieve-exchange")
    public ResponseEntity<CurrencyExchange> retrieveExchangeValue(@PathVariable String from,
                                                  @PathVariable String to) {
        String port = environment.getProperty("local.server.port");

        CurrencyExchange currencyExchange = currencyExchangeRepository.findByFromAndTo(from, to);
        if (currencyExchange == null) {
            log.error("Unable to find data for {} to {}", from, to);
            return ResponseEntity.notFound().build();
        }
        currencyExchange.setEnvironment(port);
        log.info("Exchange value retrieved: {}", currencyExchange);
        return ResponseEntity.ok(currencyExchange);
    }

    private ResponseEntity<String> errorMessage(Exception e) {
        log.error("Exchange service temporary unavailable", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exchange service temporary unavailable. Try again later.");
    }

}
