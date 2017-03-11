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
    public void canAddSingleItem() {
        //given
        Product product = new Product("A71243E2", "Num Noms Series 2 Sparkle Cupcake Playset", new BigDecimal(9.59));
        LineEntry expectedEntry = new TestLineEntry("A71243E2", "Num Noms Series 2 Sparkle Cupcake Playset", 1,
                new BigDecimal(9.59).setScale(2, BigDecimal.ROUND_HALF_UP));

        //when
        cart.add(product);

        //then
        LineEntry cartEntry = cart.getEntries().get(0);
        Assert.assertEquals(1, cart.getEntryCount());
        assertLineEntry(expectedEntry, cartEntry);
    }

    public void assertLineEntry(final LineEntry expectedEntry, final LineEntry actualEntry) {
        Assert.assertEquals(expectedEntry.getSku(), actualEntry.getSku());
        Assert.assertEquals(expectedEntry.getName(), actualEntry.getName());
        Assert.assertEquals(expectedEntry.getQuantity(), actualEntry.getQuantity());
        Assert.assertEquals(expectedEntry.getCost(), actualEntry.getCost());
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
