package com.sample.shopping.cart;

import java.math.BigDecimal;

public interface LineEntry {
    String getSku();
    String getName();
    int getQuantity();
    BigDecimal getCost();
}
