# Microservices for currency conversion

## AVAILABLE EXCHANGING

PLN, USD, EUR, UAH

## APPLICATION PORTS
SpringCloud Config Server 8888

Currency exchange service 8000 - 8099

Currency conversion service 8100 - 8199

Eureka 8761

Api Gateway 8765

Zipkin Tracing Server 9411


## URLS

**Currency Exchange Service**

http://localhost:8000/currency-exchange/from/PLN/to/USD

**Currency Conversion Service**

http://localhost:8100/currency-conversion/from/PLN/to/USD/quantity/11

**Eureka**

http://localhost:8761

**API Gateway**

http://localhost:8765/currency-exchange/from/PLN/to/USD
http://localhost:8765/currency-conversion/from/UAH/to/USD/quantity/2000

Limits Microservice 8080,8081


PLN, USD, EUR, UAH

PLN, USD, EUR, UAH

