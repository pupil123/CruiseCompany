package dao.impl;

import dao.CommonsOperable;
import dao.MySQLConnectorManager;
import dao.OrderDAO;
import domain.Order;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;

import static util.Constants.*;

/**
 * The {@code OrderDaoImpl} class is a JDBC implementation
 * of {@code OrderDao} interface
 */
public class OrderDAOImpl implements OrderDAO, CommonsOperable {
    private static final Logger LOGGER = LogManager.getLogger(OrderDAOImpl.class);

    /**
     * Receives order and saves it into DB
     *
     * @param order the instance of {@code Order} entity class
     * @return int of the new order Id from DB.
     */
    @Override
    public int addNewOrder(Order order) {

        int saveId = 0;
        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_ADD_NEW_ORDER, Statement.RETURN_GENERATED_KEYS)) {
            MySQLConnectorManager.startTransaction(connection);
            statement.setInt(1, order.getUserId());
            statement.setInt(2, order.getCruiseId());
            statement.setString(3, order.getStatus().toString());
            statement.setString(4, order.getDate().toString());
            statement.executeUpdate();
            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    saveId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
            MySQLConnectorManager.commitTransaction(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return saveId;
    }

    /**
     * Receives the id of Order and deletes order from DB.
     *
     * @param orderId instance of {@code int} Parameter specifies the Order.
     */
    @Override
    public void deleteOrderById(int orderId) throws SQLException {
        deleteItemById(orderId, SQL_DELETE_ORDER_BY_ID);
    }

    /**
     * Receives order and updates it in DB.
     *
     * @param order instance of {@code Order} entity class.
     */
    @Override
    public void updateOrder(Order order) throws SQLException {

        try (Connection connection = MySQLConnectorManager.getConnection();
             PreparedStatement statement =
                     connection.prepareStatement(SQL_UPDATE_ORDER)) {

            MySQLConnectorManager.startTransaction(connection);

            statement.setInt(1, order.getUserId());
            statement.setInt(2, order.getCruiseId());
            statement.setString(3, order.getStatus().toString());
            statement.setString(4, order.getDate().toString());
            statement.setInt(5, order.getId());

            statement.executeUpdate();

            MySQLConnectorManager.commitTransaction(connection);
        } catch (SQLException e) {
            throw new SQLException(COULD_NOT_PERSIST_ORDER);
        }
    }

    /**
     * Receives the connection and returns all the orders from DB.
     *
     * @param connection java.sql.Connection
     * @return ResultSet containing the data of all the Orders from DB.
     */
    @Override
    public ResultSet getAllOrders(Connection connection) throws SQLException {

        return getAllItems(connection, SQL_GET_ALL_ORDERS);
    }

    /**
     * Responsible for getting the Order with specified id from DB.
     *
     * @param connection java.sql.Connection
     * @param orderId    the {@code int} parameter, specifies order.
     * @return ResultSet with all the data of Order.
     */
    @Override
    public ResultSet getOrderById(int orderId, Connection connection) throws SQLException {

        return getItemById(connection, SQL_GET_ORDER_BY_ID, orderId);

    }

    /**
     * Responsible for getting number of orders with status PAID by specified cruise id from DB.
     *
     * @param connection java.sql.Connection
     * @param cruiseId   the {@code int} parameter, specifies cruise.
     * @return ResultSet with  the data of orders number.
     */
    @Override
    public ResultSet getNumPaidOrderByCruiseId(int cruiseId, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_GET_NUM_PAID_ORDERS_BY_CRUISEID);
        statement.setInt(1, cruiseId);
        return statement.executeQuery();
    }

    /**
     * Responsible for getting List of Users with specified cruise id from DB.
     *
     * @param connection java.sql.Connection
     * @param cruiseId   the {@code int} parameter, specifies cruise.
     * @return ResultSet with the data of Users' List.
     */
    @Override
    public ResultSet getUserListByCruiseId(int cruiseId, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_GET_USERSIDS_FROM_ORDER_BY_CRUISEID);
        statement.setInt(1, cruiseId);
        return statement.executeQuery();
    }

    /**
     * Responsible for getting Order Ids with specified cruise id and user's Id from DB.
     *
     * @param connection java.sql.Connection
     * @param cruiseId   the {@code int} parameter, specifies cruise.
     * @param userId     the {@code int} parameter, specifies user.
     * @return ResultSet with the data of Orders' Ids.
     */
    @Override
    public ResultSet getOrderIdsByCruiseIdUserId(int cruiseId, int userId, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_GET_ORDERSIDS_BY_CRUISEID_AND_USERID);

        statement.setInt(1, cruiseId);
        statement.setInt(2, userId);

        return statement.executeQuery();
    }

    /**
     * Responsible for getting Order Ids with specified cruise's finish date from DB.
     *
     * @param connection       java.sql.Connection
     * @param cruiseFinishDate instance of LocalDate {@code LocalDate} parameter, specifies LocalDate.
     * @return ResultSet with the data of Orders' Ids.
     */
    @Override
    public ResultSet getOrderIdsByCruiseFinishDate(LocalDate cruiseFinishDate, Connection connection) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(SQL_GET_ORDERSIDS_BY_CRUISE_FINISH_DATE);

        statement.setString(1, cruiseFinishDate.toString());

        return statement.executeQuery();
    }
}
