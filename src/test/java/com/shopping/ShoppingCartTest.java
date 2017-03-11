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

    @Test
    public void canAddMultipleItems() {
        //given
        ShoppingCart cart = new ShoppingCart();
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
