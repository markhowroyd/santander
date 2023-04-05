package com.santander.pricing.config;

import com.santander.pricing.CcyPairService;
import com.santander.pricing.ApplyCommission;
import com.santander.pricing.domain.Price;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.function.Function;

@Configuration
public class AppConfig {

    @Bean
    public Function<Price, Price> applyCommission(CcyPairService ccyPairService) {
        // Assume the correct commission is available, in real system would be looked up
        return new ApplyCommission(ccyPairService, new BigDecimal("0.001"));
    }
}
