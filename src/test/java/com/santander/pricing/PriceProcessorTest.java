package com.santander.pricing;

import com.santander.pricing.domain.Price;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.function.Function;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PriceProcessorTest {

    @Mock
    private Function<Price, Price> priceFunction;

    @Mock
    private PriceRepository priceRepository;

    @InjectMocks
    private PriceProcessor priceProcessor;

    @Test
    void onPrice() throws PricingException, PriceRepositoryException {
        var somePrice = Price.builder().bid(BigDecimal.ONE).ask(BigDecimal.ONE).build();
        var someNewPrice = Price.builder().bid(BigDecimal.TEN).ask(BigDecimal.TEN).build();
        when(priceFunction.apply(somePrice)).thenReturn(someNewPrice);

        priceProcessor.onPrice(somePrice);

        verify(priceFunction).apply(somePrice);
        verify(priceRepository).insertPrice(someNewPrice);
    }
}