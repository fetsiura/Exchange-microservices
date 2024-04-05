package pl.jaro.microservices.currencyconversionservice.core.currencyExchangeProxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.jaro.microservices.currencyconversionservice.core.currencyConversion.CurrencyConversion;

//if use default kubernetes environment variable
//@FeignClient(name = "currency-exchange", url = "${CURRENCY_EXCHANGE_SERVICE_HOST:http://localhost}:8000")
@FeignClient(name = "currency-exchange", url = "${CURRENCY_EXCHANGE_URI:http://localhost}:8000")
//if use custom environment variable should add the configmap to kubernetes
public interface CurrencyExchangeProxy {
    @GetMapping("/currency-exchange/from/{from}/to/{to}")
    public CurrencyConversion retrieveCurrencyExchange(@PathVariable String from,
                                                              @PathVariable String to);
}
