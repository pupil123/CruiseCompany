package service;

import domain.Order;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
/**
 * The {@code OrderService} interface is responsible for processing business logic
 * with {@code Order} entity class
 */
public interface OrderService {
    /**
     * Receives order and saves it into DB
     *
     * @param order the instance of {@code Order} entity class
     * @return int of the new order Id from DB.
     */
    int addNewOrder(Order order) throws SQLException, IOException;

    /**
     * Responsible for getting the list of all Orders from DB.
     *
     * @return {@code List<Order>} the list of all Orders from DB.
     */
    List<Order> getAllOrders() throws SQLException;

    /**
     * Responsible for deleting specified Order from DB.
     *
     * @param orderId the {@code int} parameter specifies Order.
     */
    void deleteOrderById(int orderId) throws SQLException;
    /**
     * Responsible for updating Order in DB
     *
     * @param order the {@code Order} entity class .
     */
    void updateOrder (Order order) throws SQLException;

    /**
     * Responsible for getting Order instance with specified id.
     *
     * @param orderId the {@code int} parameter specifies Order.
     * @return {@code Order} instance with specified id.
     */
    Order getOrderById(int orderId) throws SQLException;
    /**
     * Responsible for getting number of orders with status PAID by specified cruise id from DB.
     *
     * @param cruiseId the {@code int} parameter, specifies cruise.
     * @return {@code int} with  the data of orders number.
     */
    int getNumOfPaidOrdersByCruiseId (int cruiseId) throws SQLException;
    /**
     * Responsible for getting List of User Ids with specified cruise id from DB.
     *
     * @param cruiseId the {@code int} parameter, specifies cruise.
     * @return List<Integer> with the Ids of Users.
     */
    List<Integer> getUserListByCruiseId( int cruiseId) throws SQLException;
    /**
     * Responsible for getting Order Id with specified cruise id and user's Id from DB.
     *
     * @param cruiseId   the {@code int} parameter, specifies cruise.
     * @param userId     the {@code int} parameter, specifies user.
     * @return {@code int} with the data of Order's Id.
     */
    int getOrderIdByCruiseIdUserId (int cruiseId, int userId) throws SQLException;
    /**
     * Responsible for getting List of Order Ids with specified cruise's finish date from DB.
     *
     * @param cruiseFinishDate instance of LocalDate {@code LocalDate} parameter, specifies LocalDate.
     * @return List<Integer> with the data of Orders' Ids.
     */
    List<Integer> getOrderIdListByCruiseFinishDate(LocalDate cruiseFinishDate);

}
