package com.shopping;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

public class OrderRegisterSpyTest {

    private final static class FakeTaxCalculator implements TaxCalculator {
        private final BigDecimal TAX_RATE = new BigDecimal(0.0925).setScale(4, BigDecimal.ROUND_HALF_UP);

        @Override
        public BigDecimal calculate(final BigDecimal subtotal, final String state) {
            return subtotal.multiply(TAX_RATE).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }

    private final static class OrderRepositorySpy implements OrderRepository {
        private Order savedOrder;

        @Override
        public void save(final Order order) {
            this.savedOrder = order;
        }

        public Order getSavedOrder() {
            return savedOrder;
        }
    }

    @Test
    public void placedOrderIsPersisted() {
        //given
        OrderRepositorySpy orderRepositorySpy = new OrderRepositorySpy();
        OrderRegister orderRegister = new OrderRegister(orderRepositorySpy, new FakeTaxCalculator());
        Product product = new Product("SHPKNS-1", "Shopkins Blind Bag",
                new BigDecimal(2.99).setScale(2, BigDecimal.ROUND_HALF_UP));
        ShoppingCart cart = buildSampleShoppingCart(product);
        ShippingAddress shippingAddress = buildSampleShippingAddress();

        //when
        Order order = orderRegister.placeOrder(cart, "daniel.j.aquino@gmail.com", shippingAddress);

        //then
        Assert.assertEquals("daniel.j.aquino@gmail.com", orderRepositorySpy.getSavedOrder().getUserId());
    }

    private ShoppingCart buildSampleShoppingCart(final Product product) {
        ShoppingCart cart = new ShoppingCart();
        cart.add(product);
        return cart;
    }

    private ShippingAddress buildSampleShippingAddress() {
        return new ShippingAddress("Daniel Aquino", "1234 Test Street", "", "Nashville", "TN", "37217");
    }

    private void assertShippingAddress(final ShippingAddress shippingAddress, final Order order) {

    }
}
