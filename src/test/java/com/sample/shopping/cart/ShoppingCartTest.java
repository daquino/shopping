package com.sample.shopping.cart;

import com.sample.shopping.product.Product;
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
        Product numProduct = new Product("A71243E2", "Num Noms Series 2 Sparkle Cupcake Playset", new BigDecimal(9.59));
        Product ponyProduct = new Product("4459EAD4", "My Little Pony Pinkie Pie Sweet Style Pony Playset",
                new BigDecimal(21.99));
        LineEntry firstExpectedEntry = new TestLineEntry("A71243E2", "Num Noms Series 2 Sparkle Cupcake Playset", 1,
                new BigDecimal(9.59).setScale(2, BigDecimal.ROUND_HALF_UP));
        LineEntry secondExpectedEntry = new TestLineEntry("4459EAD4", "My Little Pony Pinkie Pie Sweet Style Pony Playset",
                1,  new BigDecimal(21.99).setScale(2, BigDecimal.ROUND_HALF_UP));

        //when
        cart.add(numProduct);
        cart.add(ponyProduct);

        //then
        Assert.assertEquals(2, cart.getEntryCount());
        assertLineEntry(firstExpectedEntry, cart.getEntries().get(0));
        assertLineEntry(secondExpectedEntry, cart.getEntries().get(1));
    }

    private void assertLineEntry(final LineEntry expectedEntry, final LineEntry actualEntry) {
        Assert.assertEquals(expectedEntry.getSku(), actualEntry.getSku());
        Assert.assertEquals(expectedEntry.getName(), actualEntry.getName());
        Assert.assertEquals(expectedEntry.getQuantity(), actualEntry.getQuantity());
        Assert.assertEquals(expectedEntry.getCost(), actualEntry.getCost());
    }

    @Test
    public void canAddSameItemMultipleTimes() {
        //given
        Product ponyProduct = new Product("4459EAD4", "My Little Pony Pinkie Pie Sweet Style Pony Playset",
                new BigDecimal(21.99));
        LineEntry secondExpectedEntry = new TestLineEntry("4459EAD4", "My Little Pony Pinkie Pie Sweet Style Pony Playset",
                3,  new BigDecimal(65.97).setScale(2, BigDecimal.ROUND_HALF_UP));

        //when
        cart.add(ponyProduct);
        cart.add(ponyProduct);
        cart.add(ponyProduct);

        //then
        Assert.assertEquals(3, cart.getEntryCount());
        assertLineEntry(secondExpectedEntry, cart.getEntries().get(0));
    }

    private static class TestLineEntry implements LineEntry {
        private String sku;
        private String name;
        private int quantity;
        private BigDecimal cost;

        public TestLineEntry(final String sku, final String name, int quantity, final BigDecimal cost) {
            this.sku = sku;
            this.name = name;
            this.quantity = quantity;
            this.cost = cost;
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

        public BigDecimal getCost() {
            return cost;
        }
    }
}
