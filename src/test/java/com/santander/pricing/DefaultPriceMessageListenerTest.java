package com.santander.pricing;

import com.santander.pricing.domain.Price;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultPriceMessageListenerTest {

    @Mock
    private PriceParser priceParser;

    @Mock
    private PriceListener priceListener;

    @Mock
    private ErrorLogger errorLogger;

    @InjectMocks
    private DefaultPriceMessageListener defaultPriceMessageListener;

    @Test
    void onMessage() throws PriceParserException, PricingException {
        var someMessage = "some message";
        var somePrice = Price.builder().build();
        when(priceParser.parse(someMessage)).thenReturn(somePrice);

        defaultPriceMessageListener.onMessage(someMessage);

        verify(priceListener).onPrice(somePrice);
    }

    @Test
    void onMessage_whenParsingError() throws PriceParserException, PricingException {
        var someMessage = "some message";
        var parserException = new PriceParserException("some price error");
        when(priceParser.parse(someMessage)).thenThrow(parserException);

        defaultPriceMessageListener.onMessage(someMessage);

        verify(priceListener, never()).onPrice(any());
        verify(errorLogger).log(parserException);
    }

    @Test
    void onMessage_whenPricingError() throws PriceParserException, PricingException {
        var someMessage = "some message";
        var pricingException = new PricingException("some price error", new RuntimeException());
        var somePrice = Price.builder().build();
        when(priceParser.parse(someMessage)).thenReturn(somePrice);
        doThrow(pricingException).when(priceListener).onPrice(somePrice);

        defaultPriceMessageListener.onMessage(someMessage);
        verify(errorLogger).log(pricingException);
    }
}
