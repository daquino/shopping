package com.shopping;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class ShoppingCartTest {
    @Test
    public void canAddASingleItem() {
        //given
        ShoppingCart cart = new ShoppingCart();
        Product product = new Product("A71243E2", "Num Noms Series 2 Sparkle Cupcake Playset", new BigDecimal(9.59));
        LineItem expectedLineItem = new TestLineItem("A71243E2", "Num Noms Series 2 Sparkle Cupcake Playset", 1,
                new BigDecimal(9.59).setScale(2, BigDecimal.ROUND_HALF_UP));

        //when
        cart.add(product);

        //then
        Assert.assertEquals(1, cart.getItemCount());
        Assert.assertEquals(new BigDecimal(9.59).setScale(2, BigDecimal.ROUND_HALF_UP), cart.getSubtotal());
        assertLineEntry(expectedLineItem, cart.getLineItems().get(0));

    }

    private void assertLineEntry(final LineItem expectedItem, final LineItem actalItem) {
        Assert.assertEquals(expectedItem.getSku(), actalItem.getSku());
        Assert.assertEquals(expectedItem.getName(), actalItem.getName());
        Assert.assertEquals(expectedItem.getQuantity(), actalItem.getQuantity());
        Assert.assertEquals(expectedItem.getPrice(), actalItem.getPrice());
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
