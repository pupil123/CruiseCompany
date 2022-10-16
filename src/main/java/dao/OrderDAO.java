package dao;

import domain.Order;
import domain.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

/**
 * The {@code OrderDAO} interface is responsible for
 * connecting {@code Order} entity class to DB
 */
public interface OrderDAO {
    /**
     * Receives order and saves it into DB
     *
     * @param order the instance of {@code Order} entity class
     * @return int of the new order Id from DB.
     * @throws SQLException when persist DB fails
     */
    int addNewOrder(Order order) throws SQLException;

    /**
     * Receives the id of Order and deletes order from DB.
     *
     * @param orderId instance of {@code int} Parameter specifies the Order.
     * @throws SQLException when persist DB fails
     */
    void deleteOrderById(int orderId) throws SQLException;

    /**
     * Receives order and updates it in DB.
     *
     * @param order instance of {@code Order} entity class.
     * @throws SQLException when persist DB fails
     */
    void updateOrder(Order order) throws SQLException;

    /**
     * Responsible for getting number of orders with status PAID by specified cruise id from DB.
     *
     * @param connection java.sql.Connection
     * @param cruiseId   the {@code int} parameter, specifies cruise.
     * @return ResultSet with  the data of orders number.
     * @throws SQLException when persist DB fails
     */
    ResultSet getNumPaidOrderByCruiseId(int cruiseId, Connection connection) throws SQLException;

    /**
     * Responsible for getting List of Users with specified cruise id from DB.
     *
     * @param connection java.sql.Connection
     * @param cruiseId   the {@code int} parameter, specifies cruise.
     * @return ResultSet with the data of Users' List.
     * @throws SQLException when persist DB fails
     */
    ResultSet getUserListByCruiseId(int cruiseId, Connection connection) throws SQLException;

    /**
     * Responsible for getting Order Ids with specified cruise id and user's Id from DB.
     *
     * @param connection java.sql.Connection
     * @param cruiseId   the {@code int} parameter, specifies cruise.
     * @param userId     the {@code int} parameter, specifies user.
     * @return ResultSet with the data of Orders' Ids.
     * @throws SQLException when persist DB fails
     */
    ResultSet getOrderIdsByCruiseIdUserId(int cruiseId, int userId, Connection connection) throws SQLException;

    /**
     * Responsible for getting Order Ids with specified cruise's finish date from DB.
     *
     * @param connection       java.sql.Connection
     * @param cruiseFinishDate instance of LocalDate {@code LocalDate} parameter, specifies LocalDate.
     * @return ResultSet with the data of Orders' Ids.
     * @throws SQLException when persist DB fails
     */
    ResultSet getOrderIdsByCruiseFinishDate(LocalDate cruiseFinishDate, Connection connection) throws SQLException;

    /**
     * Receives the connection and returns all the orders from DB.
     *
     * @param connection java.sql.Connection
     * @return ResultSet containing the data of all the Orders from DB.
     * @throws SQLException when persist DB fails
     */
    ResultSet getAllOrders(Connection connection) throws SQLException;

    /**
     * Responsible for getting the Order with specified id from DB.
     *
     * @param connection java.sql.Connection
     * @param orderId    the {@code int} parameter, specifies order.
     * @return ResultSet with all the data of Order.
     * @throws SQLException when persist DB fails
     */
    ResultSet getOrderById(int orderId, Connection connection) throws SQLException;


}
