package pl.jaro.microservices.currencyconversionservice.controller;

import feign.FeignException;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.jaro.microservices.currencyconversionservice.core.currencyConversion.CurrencyConversion;
import pl.jaro.microservices.currencyconversionservice.core.currencyExchangeProxy.CurrencyExchangeProxy;

import java.math.BigDecimal;
import java.util.HashMap;

@RestController
@RequiredArgsConstructor
@Slf4j
public class CurrencyConversionController {

    private final CurrencyExchangeProxy proxy;

    @GetMapping("/currency-conversion/from/{from}/to/{to}/quantity/{quantity}")
    @CircuitBreaker(name = "currency-conversion-feign", fallbackMethod = "errorMessage")
    @RateLimiter(name = "currency-conversion-feign")
    @Bulkhead(name = "currency-conversion-feign")
    public ResponseEntity<CurrencyConversion> retrieveCurrencyConversionFeign(@PathVariable String to,
                                                                              @PathVariable String from,
                                                                              @PathVariable BigDecimal quantity) {
        try {
            CurrencyConversion currencyConversion = proxy.retrieveCurrencyExchange(from, to);

            return ResponseEntity.ok(new CurrencyConversion(currencyConversion.getId(),
                    from,
                    to,
                    quantity,
                    currencyConversion.getConversionMultiple(),
                    quantity.multiply(currencyConversion.getConversionMultiple()),
                    currencyConversion.getEnvironment() + " feign"));
        } catch (FeignException ex) {
            log.error("Failed to retrieve currency exchange from Feign client", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new CurrencyConversion());
        }
    }

    private ResponseEntity<String> errorMessage(Exception e) {
        log.error("Exchange service temporary unavailable", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Exchange service temporary unavailable. Try again later.");
    }
}
