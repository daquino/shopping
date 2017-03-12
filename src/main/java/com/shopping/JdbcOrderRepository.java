package com.shopping;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class JdbcOrderRepository implements OrderRepository {
    private final DataSource dataSource;

    public JdbcOrderRepository(final DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void save(final Order order) {
        try (final Connection connection = dataSource.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement("insert into \"order\" values(?,?,?,?,?,?,?,?,?,?,?)")) {
            prepareOrderStatement(order, preparedStatement);
            preparedStatement.execute();
            insertOrderItems(connection, order);
            connection.commit();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void prepareOrderStatement(final Order order, final PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, order.getOrderId());
        preparedStatement.setBigDecimal(2, order.getSubtotal());
        preparedStatement.setBigDecimal(3, order.getTax());
        preparedStatement.setBigDecimal(4, order.getTotal());
        preparedStatement.setString(5, order.getUserId());
        preparedStatement.setString(6, order.getShippingAddressTo());
        preparedStatement.setString(7, order.getShippingAddressLineOne());
        preparedStatement.setString(8, order.getShippingAddressLineTwo());
        preparedStatement.setString(9, order.getShippingAddressCity());
        preparedStatement.setString(10, order.getShippingAddressState());
        preparedStatement.setString(11, order.getShippingAddressZip());
    }

    private void insertOrderItems(final Connection connection, final Order order) {
        try (PreparedStatement statement = connection.prepareStatement("insert into order_item values(?,?,?,?,?)")) {
            for (LineItem item : order.getItems()) {
                statement.setString(1, order.getOrderId());
                statement.setString(2, item.getSku());
                statement.setString(3, item.getName());
                statement.setBigDecimal(4, item.getPrice());
                statement.setInt(5, item.getQuantity());
                statement.addBatch();
            }
            statement.executeBatch();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
