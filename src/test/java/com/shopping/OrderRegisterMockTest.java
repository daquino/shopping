package com.shopping;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;

public class OrderRegisterMockTest {

    @Test
    public void placedOrderIsPersisted() {
        //given
        OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
        TaxCalculator taxCalculator = Mockito.mock(TaxCalculator.class);
        OrderRegister orderRegister = new OrderRegister(orderRepository, taxCalculator);
        Product product = new Product("SHPKNS-1", "Shopkins Blind Bag",
                new BigDecimal(2.99).setScale(2, BigDecimal.ROUND_HALF_UP));
        ShoppingCart cart = buildSampleShoppingCart(product);
        ShippingAddress shippingAddress = buildSampleShippingAddress();
        Mockito.when(taxCalculator.calculate(Mockito.any(BigDecimal.class), Mockito.any(String.class)))
                .thenReturn(BigDecimal.ZERO);

        //when
        Order order = orderRegister.placeOrder(cart, "daniel.j.aquino@gmail.com", shippingAddress);

        //then
        Assert.assertEquals(BigDecimal.ZERO, order.getTax());
        Mockito.verify(orderRepository).save(Mockito.any(Order.class));
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
