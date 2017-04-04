package com.shopping;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.h2.tools.RunScript;
import org.junit.*;

import java.io.StringReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcOrderRepositoryITCase {

    private JdbcOrderRepository orderRepository;
    private RelationalInstance instance;

    @Before
    public void setup() throws SQLException {
        instance = new RelationalInstance(Paths.get(System.getProperty("sql.schema.path")));
        orderRepository = new JdbcOrderRepository(instance.getDataSource());
    }

    @After
    public void tearDown() throws SQLException {
        instance.cleanup();
    }

    @Test
    public void canSave() throws SQLException {
        //given
        Order order = createExampleOrder();

        //when
        orderRepository.save(order);

        //then
        assertOrderDetails(order);
        assertOrderItems(order);
    }

    private Order createExampleOrder() {
        Product ponyProduct = new Product("4459EAD4", "My Little Pony Pinkie Pie Sweet Style Pony Playset",
                new BigDecimal(21.99));
        BigDecimal subtotal = new BigDecimal(21.99).setScale(2, RoundingMode.HALF_UP);
        BigDecimal tax = new BigDecimal(2.20).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = new BigDecimal(24.19).setScale(2, BigDecimal.ROUND_HALF_UP);
        List<LineItem> items = new ArrayList<>();
        items.add(new LineItem(ponyProduct, 1));
        ShippingAddress shippingAddress = new ShippingAddress("Daniel Aquino", "1234 Test Street",
                "Suite 100", "Nashville", "TN", "37013");
        return new Order("12345", subtotal, tax, total, "daniel.j.aquino@gmail.com",
                shippingAddress, items);
    }

    @Test
    public void testSave() throws SQLException {
        //given
        Product ponyProduct = new Product("4459EAD4", "My Little Pony Pinkie Pie Sweet Style Pony Playset",
                new BigDecimal(21.99));
        Product nomProduct = new Product("A71243E2", "Num Noms Series 2 Sparkle Cupcake Playset", new BigDecimal(9.59));
        BigDecimal subtotal = new BigDecimal(41.17).setScale(2, RoundingMode.HALF_UP);
        BigDecimal tax = new BigDecimal(0.0925).setScale(2, RoundingMode.HALF_UP);
        BigDecimal total = new BigDecimal(44.98).setScale(2, BigDecimal.ROUND_HALF_UP);
        List<LineItem> items = new ArrayList<>();
        items.add(new LineItem(ponyProduct, 1));
        items.add(new LineItem(nomProduct, 2));
        ShippingAddress shippingAddress = new ShippingAddress("Daniel Aquino", "1234 Test Street",
                "Suite 100", "Nashville", "TN", "37013");
        Order order = new Order("12345", subtotal, tax, total, "daniel.j.aquino@gmail.com",
                shippingAddress, items);

        //when
        orderRepository.save(order);

        //then
        assertOrderDetails(order);
        assertOrderItems(order);

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

}
