package com.santander.pricing;

import com.santander.pricing.domain.CcyPair;
import com.santander.pricing.domain.Price;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class E2EPricingTest {

    private static final String PRICE_MSG = "106, EUR/USD, 1.1000,1.2000,01-06-2020 12:01:02:003";
    private static final String ID = "106";
    private static final CcyPair CCY_PAIR = new CcyPair("EUR", "USD");
    private static final BigDecimal CLIENT_BID = new BigDecimal("1.0989");
    private static final BigDecimal CLIENT_ASK = new BigDecimal("1.2012");
    private static final ZonedDateTime TIMESTAMP = ZonedDateTime.of(2020, 6, 1,
            12, 1, 2, 3000000,
            ZoneId.of("UTC"));

    private static final Price EXPECTED_PRICE = Price.builder()
            .id(ID)
            .ccyPair(CCY_PAIR)
            .bid(CLIENT_BID)
            .ask(CLIENT_ASK)
            .timestamp(TIMESTAMP).build();

    @Autowired
    private PriceMessageListener priceMessageListener;

    @Autowired
    private PriceRepository priceRepository;

    @Test
    public void fetchPrice() {
        priceMessageListener.onMessage(PRICE_MSG);

        var result = priceRepository.fetchPrice(CCY_PAIR);

        result.ifPresent(price -> System.out.printf("** Fetched price of %s for %s ** \n", price, CCY_PAIR));
        assertThat(result).hasValue(EXPECTED_PRICE);
    }
}
