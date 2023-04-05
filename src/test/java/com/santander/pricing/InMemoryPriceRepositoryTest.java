package com.santander.pricing;

import com.santander.pricing.domain.CcyPair;
import com.santander.pricing.domain.Price;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class InMemoryPriceRepositoryTest {

    private static final CcyPair CCY_PAIR_1 = new CcyPair("EUR", "USD");
    private static final CcyPair CCY_PAIR_2 = new CcyPair("GBP", "JPY");
    private static final BigDecimal BID_1 = new BigDecimal("1.2345");
    private static final BigDecimal ASK_1 = new BigDecimal("2.3456");
    private static final BigDecimal BID_2 = new BigDecimal("10.2345");
    private static final BigDecimal ASK_2 = new BigDecimal("20.3456");
    private static final CcyPair SOME_CCY_PAIR_WITHOUT_PRICES = new CcyPair("EUR", "JPY");

    private final InMemoryPriceRepository inMemoryPriceRepository = new InMemoryPriceRepository();

    @Test
    void whenPricesInsertedTheyCanBeRetrieved() {
        var pair1price = newPrice(CCY_PAIR_1, BID_1, ASK_1);
        var pair2price = newPrice(CCY_PAIR_2, BID_2, ASK_2);
        inMemoryPriceRepository.insertPrice(pair1price);
        inMemoryPriceRepository.insertPrice(pair2price);

        assertThat(inMemoryPriceRepository.fetchPrice(CCY_PAIR_1)).hasValue(pair1price);
        assertThat(inMemoryPriceRepository.fetchPrice(CCY_PAIR_2)).hasValue(pair2price);
        assertThat(inMemoryPriceRepository.fetchPrice(SOME_CCY_PAIR_WITHOUT_PRICES)).isEmpty();
    }

    private static Price newPrice(CcyPair ccyPair, BigDecimal bid, BigDecimal ask) {
        var price = Price.builder()
                .ccyPair(ccyPair)
                .bid(bid)
                .ask(ask).build();
        return price;
    }
}
