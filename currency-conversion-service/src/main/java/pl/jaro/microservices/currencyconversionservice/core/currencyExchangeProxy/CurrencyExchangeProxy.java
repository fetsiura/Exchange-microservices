package pl.jaro.microservices.currencyconversionservice.core.currencyExchangeProxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.jaro.microservices.currencyconversionservice.core.currencyConversion.CurrencyConversion;

@FeignClient(name = "currency-exchange")
//balancing of servers happening in FEIGN dependency (old tech could be Zool)
public interface CurrencyExchangeProxy {
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversion retrieveCurrencyExchange(@PathVariable String from,
                                                              @PathVariable String to);
}
