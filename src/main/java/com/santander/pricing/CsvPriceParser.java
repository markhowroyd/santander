package com.santander.pricing;

import com.santander.pricing.domain.CcyPair;
import com.santander.pricing.domain.Price;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Component
public class CsvPriceParser implements PriceParser {

    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS");
    private static final ZoneId UTC = ZoneId.of("UTC");

    @Override
    public Price parse(String priceMessage) throws PriceParserException {
        var parts = priceMessage.split(",");

        var id = parts[0].trim();
        if (isBlank(id)) {
            throw new PriceParserException("Price ID must not be empty");
        }

        try {
            CcyPair ccyPair = CcyPair.fromSlashString(parts[1].trim());
            return Price.builder()
                    .id(id)
                    .ccyPair(ccyPair)
                    .bid(parseValue(parts[2].trim()))
                    .ask(parseValue(parts[3].trim()))
                    .timestamp(parseTimestamp(parts[4]))
                    .build();
        } catch (IllegalArgumentException e) {
            throw new PriceParserException("Bad price message string", e);
        }
    }

    private static BigDecimal parseValue(String valueString) throws PriceParserException {
        try {
            return new BigDecimal(valueString);
        } catch (NumberFormatException e) {
            throw new PriceParserException("Bad format for decimal price, " + valueString, e);
        }
    }

    private static ZonedDateTime parseTimestamp(String value) throws PriceParserException {
        try {
            return LocalDateTime.parse(value, TIMESTAMP_FORMAT).atZone(UTC);
        } catch (DateTimeParseException e) {
            throw new PriceParserException("Bad format for timestamp, " + value, e);
        }
    }
}
