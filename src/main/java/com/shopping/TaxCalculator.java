package com.shopping;

import java.math.BigDecimal;

public interface TaxCalculator {
    BigDecimal calculate(final BigDecimal subtotal, final String state);
}
