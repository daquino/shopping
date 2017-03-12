package com.shopping;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class OrderRegisterTest {
    private OrderRegister orderRegister;
    private OrderRepository orderRepository;
    private SalesTaxDeterminer salesTaxDeterminer;
    private ShoppingCart cart;
    private Product nomProduct;
    private Product ponyProduct;
    private ShippingAddress shippingAddress;

    @Before
    public void setUp() {
        orderRepository = Mockito.mock(OrderRepository.class);
        salesTaxDeterminer = new FakeSalesTaxDeterminer();
        orderRegister = new OrderRegister(orderRepository, salesTaxDeterminer);
        cart = new ShoppingCart();
        nomProduct = new Product("A71243E2", "Num Noms Series 2 Sparkle Cupcake Playset", new BigDecimal(9.59));
        ponyProduct = new Product("4459EAD4", "My Little Pony Pinkie Pie Sweet Style Pony Playset",
                new BigDecimal(21.99));
        shippingAddress = new ShippingAddress("Daniel Aquino", "1234 Test Street", "Suite 100", "Nashville", "TN", "37013");
    }

    @After
    public void tearDown() {
        orderRegister = null;
        orderRepository = null;
    }

    @Test
    public void canPlaceOrder() {
        //given
        setupMultiItemCart();
        BigDecimal expectedSubtotal = new BigDecimal(94.74).setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedTax = new BigDecimal(8.76).setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedTotal = new BigDecimal(103.50).setScale(2, RoundingMode.HALF_UP);
        LineItem expectedNomItem = new LineItem(nomProduct, 3);
        LineItem expectedPonyItem = new LineItem(ponyProduct, 3); 

        //when
        Order order = orderRegister.placeOrder(cart, "daniel.j.aquino@gmail.com", shippingAddress);

        //then
        Assert.assertTrue("Should have valid order id", order.getOrderId().length() > 0);
        assertOrderTotals(expectedSubtotal, expectedTax, expectedTotal, order);
        Assert.assertEquals("daniel.j.aquino@gmail.com", order.getUserId());
        assertShippingAddress(shippingAddress, order);
        assertLineItem(expectedNomItem, order.getItems().get(0));
        assertLineItem(expectedPonyItem, order.getItems().get(1));
    }

    private void setupMultiItemCart() {
        cart.add(nomProduct);
        cart.add(nomProduct);
        cart.add(nomProduct);
        cart.add(ponyProduct);
        cart.add(ponyProduct);
        cart.add(ponyProduct);
    }

    private void assertOrderTotals(final BigDecimal expectedSubtotal, final BigDecimal expectedTax,
                                   final BigDecimal expectedTotal, final Order order) {
        Assert.assertEquals(expectedSubtotal, order.getSubtotal());
        Assert.assertEquals(expectedTax, order.getTax());
        Assert.assertEquals(expectedTotal, order.getTotal());
    }

    private void assertShippingAddress(final ShippingAddress shippingAddress, final Order order) {
        Assert.assertEquals(order.getShippingAddressTo(), shippingAddress.getTo());
        Assert.assertEquals(order.getShippingAddressLineOne(), shippingAddress.getLineOne());
        Assert.assertEquals(order.getShippingAddressLineTwo(), shippingAddress.getLineTwo());
        Assert.assertEquals(order.getShippingAddressCity(), shippingAddress.getCity());
        Assert.assertEquals(order.getShippingAddressState(), shippingAddress.getState());
        Assert.assertEquals(order.getShippingAddressZip(), shippingAddress.getZip());
    }

    private void assertLineItem(final LineItem expectedItem, final LineItem actalItem) {
        Assert.assertEquals(expectedItem.getSku(), actalItem.getSku());
        Assert.assertEquals(expectedItem.getName(), actalItem.getName());
        Assert.assertEquals(expectedItem.getQuantity(), actalItem.getQuantity());
        Assert.assertEquals(expectedItem.getPrice(), actalItem.getPrice());
    }

    @Test
    public void placedOrderIsPersisted() {
        //given
        cart.add(nomProduct);

        //when
        orderRegister.placeOrder(cart, "daniel.j.aquino@gmail.com", shippingAddress);

        //then
        Mockito.verify(orderRepository).save(Mockito.any(Order.class));
    }

    private static class FakeSalesTaxDeterminer implements SalesTaxDeterminer {
        private final Map<String, Float> salesTaxMap;

        public FakeSalesTaxDeterminer() {
            salesTaxMap = new HashMap<>();
            salesTaxMap.put("TN", 0.0925f);
        }

        @Override
        public float determineSalesTax(final String state) {
            return salesTaxMap.getOrDefault(state, 0.01f);
        }
    }
}
