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
        BigDecimal expectedCost = new BigDecimal(9.59).setScale(2, BigDecimal.ROUND_HALF_UP);

        //when
        cart.add(product);

        //then
        CartEntry cartEntry = cart.getEntries().get(0);
        Assert.assertEquals(1, cart.getEntryCount());
        Assert.assertEquals("A71243E2", cartEntry.getSku());
        Assert.assertEquals("Num Noms Series 2 Sparkle Cupcake Playset", cartEntry.getName());
        Assert.assertEquals(1, cartEntry.getQuantity());
        Assert.assertEquals(expectedCost, cartEntry.getCost());
    }
}
