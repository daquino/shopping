package com.shopping;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class OrderRegisterDummyTest {

    @Test
    public void canPlaceOrder() {
        //given
        OrderRegister orderRegister = new OrderRegister(null, null);
        Product product = new Product("SHPKNS-1", "Shopkins Blind Bag",
                new BigDecimal(2.99).setScale(2, BigDecimal.ROUND_HALF_UP));
        ShoppingCart cart = buildSampleShoppingCart(product);
        ShippingAddress shippingAddress = buildSampleShippingAddress();

        //when
        Order order = orderRegister.placeOrder(cart, "daniel.j.aquino@gmail.com", shippingAddress);

        //then
        Assert.assertEquals("daniel.j.aquino@gmail.com", order.getUserId());
        Assert.assertEquals(product.getPrice(), order.getSubtotal());
        Assert.assertEquals(new BigDecimal(0.28).setScale(2, BigDecimal.ROUND_HALF_UP), order.getTax());
        Assert.assertEquals(new BigDecimal(3.27).setScale(2, BigDecimal.ROUND_HALF_UP), order.getTotal());
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
