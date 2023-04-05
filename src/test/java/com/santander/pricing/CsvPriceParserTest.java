package com.santander.pricing;

import com.santander.pricing.domain.CcyPair;
import com.santander.pricing.domain.Price;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class CsvPriceParserTest {

    private static final String ID = "107";
    private static final String BID = "119.60";
    private static final String ASK = "119.90";
    private static final String EUR = "EUR";
    private static final String JPY = "JPY";
    private static final String TIMESTAMP_STR = "01-06-2020 12:01:02:003";
    private static final ZonedDateTime TIMESTAMP = ZonedDateTime.of(2020, 6, 1, 12, 1, 2, 3000000,
            ZoneId.of("UTC"));

    private CsvPriceParser csvPriceParser = new CsvPriceParser();

    @Test
    void parse() throws PriceParserException {
        var msg = format(" %s , %s/%s , %s , %s ,%s", ID, EUR, JPY, BID, ASK, TIMESTAMP_STR);
        var result = csvPriceParser.parse(msg);
        var expected = Price.builder()
                .id(ID)
                .ccyPair(new CcyPair(EUR, JPY))
                .bid(new BigDecimal(BID))
                .ask(new BigDecimal(ASK))
                .timestamp(TIMESTAMP)
                .build();
        assertThat(result).isEqualTo(expected);
    }

    @Test
    void parse_whenBadCcyPair() {
        var msg = format(" %s , %s/%s , %s , %s ,%s", ID, "bad-ccy1", "bad-ccy2", BID, ASK, TIMESTAMP_STR);
        assertThatExceptionOfType(PriceParserException.class).isThrownBy(() -> csvPriceParser.parse(msg));
    }

    @Test
    void parse_whenBadBidPrice() {
        var msg = format(" %s , %s/%s , %s , %s ,%s", ID, EUR, JPY, "", ASK, TIMESTAMP_STR);
        assertThatExceptionOfType(PriceParserException.class).isThrownBy(() -> csvPriceParser.parse(msg));
    }

    @Test
    void parse_whenBadAskPrice() {
        var msg = format(" %s , %s/%s , %s , %s ,%s", ID, EUR, JPY, BID, "", TIMESTAMP_STR);
        assertThatExceptionOfType(PriceParserException.class).isThrownBy(() -> csvPriceParser.parse(msg));
    }

    @Test
    void parse_whenBadTimestamp() {
        var msg = format(" %s , %s/%s , %s , %s ,%s", ID, EUR, JPY, BID, ASK, "20230601 121314.001");
        assertThatExceptionOfType(PriceParserException.class).isThrownBy(() -> csvPriceParser.parse(msg));
    }

    @Test
    void parse_whenIdMissing() {
        var msg = format(" %s , %s/%s , %s , %s ,%s", "", EUR, JPY, BID, ASK, TIMESTAMP_STR);
        assertThatExceptionOfType(PriceParserException.class).isThrownBy(() -> csvPriceParser.parse(msg));
    }
}
