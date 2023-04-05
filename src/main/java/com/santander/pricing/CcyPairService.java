package com.santander.pricing;

import com.santander.pricing.domain.CcyPair;
import org.springframework.stereotype.Service;

@Service
public class CcyPairService {

    private static final String JPY = "JPY";

    public int getSpotPrecision(CcyPair ccyPair) {
        // In real system this static data would be in configuration and cover all currency pairs
        return JPY.equals(ccyPair.base()) || JPY.equals(ccyPair.term()) ? 2 : 4;
    }
}
