package com.shopping;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class ShoppingCartTest {
    private ShoppingCart cart;

    @Before
    public void setUp() {
        cart = new ShoppingCart();
    }

    @After
    public void tearDown() {
        cart = null;
    }

    @Test
    public void canAddItems() {
        //given
        Product nomProduct = new Product("A71243E2", "Num Noms Series 2 Sparkle Cupcake Playset", new BigDecimal(9.59));
        Product ponyProduct = new Product("4459EAD4", "My Little Pony Pinkie Pie Sweet Style Pony Playset",
                new BigDecimal(21.99));
        LineItem firstExpectedItem = new TestLineItem("A71243E2", "Num Noms Series 2 Sparkle Cupcake Playset", 1,
                new BigDecimal(9.59).setScale(2, BigDecimal.ROUND_HALF_UP));
        LineItem secondExpectedItem = new TestLineItem("4459EAD4", "My Little Pony Pinkie Pie Sweet Style Pony Playset", 1,
                new BigDecimal(21.99).setScale(2, BigDecimal.ROUND_HALF_UP));

        //when
        cart.add(nomProduct);
        cart.add(ponyProduct);

        //then
        Assert.assertEquals(2, cart.getItemCount());
        Assert.assertEquals(new BigDecimal(31.58).setScale(2, BigDecimal.ROUND_HALF_UP), cart.getSubtotal());
        assertLineEntry(firstExpectedItem, cart.getLineItems().get(0));
        assertLineEntry(secondExpectedItem, cart.getLineItems().get(1));
    }

    private void assertLineEntry(final LineItem expectedItem, final LineItem actalItem) {
        Assert.assertEquals(expectedItem.getSku(), actalItem.getSku());
        Assert.assertEquals(expectedItem.getName(), actalItem.getName());
        Assert.assertEquals(expectedItem.getQuantity(), actalItem.getQuantity());
        Assert.assertEquals(expectedItem.getPrice(), actalItem.getPrice());
    }

    @Test
    public void canAddSameItemMultipleTimes() {
        //given
        Product ponyProduct = new Product("4459EAD4", "My Little Pony Pinkie Pie Sweet Style Pony Playset",
                new BigDecimal(21.99));
        LineItem expectedItem = new TestLineItem("4459EAD4", "My Little Pony Pinkie Pie Sweet Style Pony Playset", 3,
                new BigDecimal(21.99).setScale(2, BigDecimal.ROUND_HALF_UP));

        //when
        cart.add(ponyProduct);
        cart.add(ponyProduct);
        cart.add(ponyProduct);

        //then
        Assert.assertEquals(3, cart.getItemCount());
        Assert.assertEquals(new BigDecimal(65.97).setScale(2, BigDecimal.ROUND_HALF_UP), cart.getSubtotal());
        assertLineEntry(expectedItem, cart.getLineItems().get(0));
    }

    private class TestLineItem implements LineItem {
        private final String sku;
        private final String name;
        private final int quantity;
        private final BigDecimal price;

        public TestLineItem(final String sku, final String name, final int quantity, final BigDecimal price) {
            this.sku = sku;
            this.name = name;
            this.quantity = quantity;
            this.price = price;
        }


        public String getSku() {
            return sku;
        }

        public String getName() {
            return name;
        }

        public int getQuantity() {
            return quantity;
        }

        public BigDecimal getPrice() {
            return price;
        }
    }
}
