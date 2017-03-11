package com.shopping;

import java.math.BigDecimal;

public interface LineItem {
    String getSku();
    String getName();
    int getQuantity();
    BigDecimal getPrice();
}
