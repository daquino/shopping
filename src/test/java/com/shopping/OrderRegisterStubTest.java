package com.shopping;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

public class OrderRegisterStubTest {

    private final static class StubTaxCalculator implements TaxCalculator {
        private final BigDecimal taxAmount;

        public StubTaxCalculator(final BigDecimal taxAmount) {
            this.taxAmount = taxAmount.setScale(2, BigDecimal.ROUND_HALF_UP);
        }

        @Override
        public BigDecimal calculate(final BigDecimal subtotal, final String state) {
            return taxAmount;
        }
    }

    @Test
    public void canPlaceOrder() {
        //given
        OrderRegister orderRegister = new OrderRegister(Mockito.mock(OrderRepository.class),
                new StubTaxCalculator(new BigDecimal(0.20)));
        Product product = new Product("SHPKNS-1", "Shopkins Blind Bag",
                new BigDecimal(2.99).setScale(2, BigDecimal.ROUND_HALF_UP));
        ShoppingCart cart = buildSampleShoppingCart(product);
        ShippingAddress shippingAddress = buildSampleShippingAddress();

        //when
        Order order = orderRegister.placeOrder(cart, "daniel.j.aquino@gmail.com", shippingAddress);

        //then
        Assert.assertEquals("daniel.j.aquino@gmail.com", order.getUserId());
        Assert.assertEquals(product.getPrice(), order.getSubtotal());
        Assert.assertEquals(new BigDecimal(0.20).setScale(2, BigDecimal.ROUND_HALF_UP), order.getTax());
        Assert.assertEquals(new BigDecimal(3.19).setScale(2, BigDecimal.ROUND_HALF_UP), order.getTotal());
        assertShippingAddress(shippingAddress, order);
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
