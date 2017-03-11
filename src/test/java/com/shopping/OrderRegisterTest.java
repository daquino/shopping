package com.shopping;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class OrderRegisterTest {
    private OrderRegister orderRegister;
    private OrderRepository orderRepository;

    @Before
    public void setUp() {
        orderRegister = new OrderRegister(orderRepository);
    }

    @After
    public void tearDown() {
        orderRegister = null;
        orderRepository = null;
    }

    public void canSubmitOrder() {
        //given
        List<LineItem> lineItems = new ArrayList<>();
        Product nomProduct = new Product("A71243E2", "Num Noms Series 2 Sparkle Cupcake Playset", new BigDecimal(9.59));
        Product ponyProduct = new Product("4459EAD4", "My Little Pony Pinkie Pie Sweet Style Pony Playset",
                new BigDecimal(21.99));
        lineItems.add(new LineItem(nomProduct, 3));
        lineItems.add(new LineItem(ponyProduct, 3));
        ShippingAddress shippingAddress = new ShippingAddress("Daniel Aquino", "1234 Test Street", "Suite 100", "Nashville", "TN", "37013");
        BigDecimal expectedSubtotal = new BigDecimal(94.74).setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedTax = new BigDecimal(8.76).setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedTotal = new BigDecimal(103.50).setScale(2, RoundingMode.HALF_UP);

        //when
        Order order = orderRegister.submitOrder(lineItems, "daniel.j.aquino@gmail.com", address);

        //then
        Assert.assertTrue(order.getOrderId().length() > 0, "Should have valid order id");
        Assert.assertEquals(expectedSubtotal, order.getSubtotal());
        Assert.assertEquals(expectedTax, order.getTax());
        Assert.assertEquals(expectedTotal, order.getTotal());
        Assert.assertEquals(order.getShippingAddressTo(), shippingAddress.getTo());
        Assert.assertEquals(order.getShippingAddressLineOne(), shippingAddress.getLineOne());
        Assert.assertEquals(order.getShippingAddressLineTwo(), shippingAddress.getLineTwo());
        Assert.assertEquals(order.getShippingAddressCity(), shippingAddress.getCity());
        Assert.assertEquals(order.getShippingAddressState(), shippingAddress.getState());
        Assert.assertEquals(order.getShippingAddressZip(), shippingAddress.getZip());
    }
}
