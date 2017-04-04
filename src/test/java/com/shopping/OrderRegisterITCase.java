package com.shopping;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.h2.tools.RunScript;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderRegisterITCase {

    private OrderRegister orderRegister;
    private RelationalInstance instance;

    @Before
    public void setUp() throws SQLException {
        instance = new RelationalInstance(Paths.get(System.getProperty("sql.schema.path")));
        orderRegister = new OrderRegister(new JdbcOrderRepository(instance.getDataSource()), new FakeTaxCalculator());
    }

    @Test
    public void orderIsPersistedWhenPlaced() {
        //setup
        Product product = new Product("SHPKNS-1", "Shopkins Blind Bag",
                new BigDecimal(2.99).setScale(2, BigDecimal.ROUND_HALF_UP));
        ShoppingCart cart = buildSampleShoppingCart(product);
        ShippingAddress shippingAddress = buildSampleShippingAddress();

        //when
        Order order = orderRegister.placeOrder(cart, "daniel.j.aquino@gmail.com", shippingAddress);

        //then
        assertOrderDetails(order);
        assertOrderItems(order);
    }

    private ShoppingCart buildSampleShoppingCart(final Product product) {
        ShoppingCart cart = new ShoppingCart();
        cart.add(product);
        return cart;
    }

    private ShippingAddress buildSampleShippingAddress() {
        return new ShippingAddress("Daniel Aquino", "1234 Test Street", "", "Nashville", "TN", "37013");
    }

    private void assertOrderDetails(final Order order) {
        try (Connection connection = instance.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from \"order\" limit 1")) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Assert.assertEquals(order.getOrderId(), resultSet.getString(1));
                Assert.assertEquals(order.getSubtotal(), resultSet.getBigDecimal(2));
                Assert.assertEquals(order.getTax(), resultSet.getBigDecimal(3));
                Assert.assertEquals(order.getTotal(), resultSet.getBigDecimal(4));
                Assert.assertEquals(order.getUserId(), resultSet.getString(5));
                Assert.assertEquals(order.getShippingAddressTo(), resultSet.getString(6));
                Assert.assertEquals(order.getShippingAddressLineOne(), resultSet.getString(7));
                Assert.assertEquals(order.getShippingAddressLineTwo(), resultSet.getString(8));
                Assert.assertEquals(order.getShippingAddressCity(), resultSet.getString(9));
                Assert.assertEquals(order.getShippingAddressState(), resultSet.getString(10));
                Assert.assertEquals(order.getShippingAddressZip(), resultSet.getString(11));
            }
            else {
                Assert.fail("Should have results");
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void assertOrderItems(final Order order) {
        try (Connection connection = instance.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement("select * from order_item")) {
            ResultSet resultSet = statement.executeQuery();
            for (LineItem item : order.getItems()) {
                if (resultSet.next()) {
                    Assert.assertEquals(order.getOrderId(), resultSet.getString(1));
                    Assert.assertEquals(item.getSku(), resultSet.getString(2));
                    Assert.assertEquals(item.getName(), resultSet.getString(3));
                    Assert.assertEquals(item.getPrice(), resultSet.getBigDecimal(4));
                    Assert.assertEquals(item.getQuantity(), resultSet.getInt(5));
                }
                else {
                    Assert.fail("Should have order items.");
                }
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private static class RelationalInstance {
        private DataSource dataSource;

        public RelationalInstance(final Path ddlPath) throws SQLException {
            RunScript.execute("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "",
                    ddlPath.toString(), Charset.forName("UTF-8"), false);
            dataSource = new DataSource();
            dataSource.setUrl("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
            dataSource.setUsername("sa");
            dataSource.setPassword("");
            dataSource.setDriverClassName("org.h2.Driver");
        }

        public DataSource getDataSource() {
            return dataSource;
        }

        public void cleanup() throws SQLException {
            RunScript.execute(dataSource.getConnection(), new StringReader("drop all objects"));
        }
    }

    private final static class FakeTaxCalculator implements TaxCalculator {
        private final BigDecimal TAX_RATE = new BigDecimal(0.0925).setScale(4, BigDecimal.ROUND_HALF_UP);

        @Override
        public BigDecimal calculate(final BigDecimal subtotal, final String state) {
            return subtotal.multiply(TAX_RATE).setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }
}
