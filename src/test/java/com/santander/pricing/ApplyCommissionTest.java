package com.santander.pricing;

import com.santander.pricing.domain.CcyPair;
import com.santander.pricing.domain.Price;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplyCommissionTest {

    private static CcyPair SOME_CCY_PAIR = new CcyPair("GBP", "USD");

    @Mock
    private CcyPairService ccyPairService;

    @Test
    void apply_4dp_precision() {
       when(ccyPairService.getSpotPrecision(SOME_CCY_PAIR)).thenReturn(4);

        var applyCommission = new ApplyCommission(ccyPairService, new BigDecimal("0.05"));
        var price = priceOf(SOME_CCY_PAIR, "10.0001", "20.0001");
        var expected = priceOf(SOME_CCY_PAIR, "9.5001", "21.0001");
        assertThat(applyCommission.apply(price)).isEqualTo(expected);
    }

    @Test
    void apply_2dp_precision() {
        when(ccyPairService.getSpotPrecision(SOME_CCY_PAIR)).thenReturn(2);

        var applyCommission = new ApplyCommission(ccyPairService, new BigDecimal("0.05"));
        var price = priceOf(SOME_CCY_PAIR, "10.05", "20.05");
        var expected = priceOf(SOME_CCY_PAIR, "9.55", "21.05");
        assertThat(applyCommission.apply(price)).isEqualTo(expected);
    }

    private static Price priceOf(CcyPair ccyPair, String bid, String ask) {
        return Price.builder()
                .ccyPair(ccyPair)
                .bid(new BigDecimal(bid))
                .ask(new BigDecimal(ask)).build();
    }
}
