package service.impl;

import dao.CruiseDAO;
import dao.MySQLConnectorManager;
import dao.OrderDAO;
import dao.ShipDAO;
import dao.impl.CruiseDAOImpl;
import dao.impl.OrderDAOImpl;
import dao.impl.ShipDAOImpl;
import domain.Cruise;
import domain.Order;
import domain.OrderStatus;
import domain.builder.OrderBuilder;
import service.OrderService;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * The {@code OrderServiceImpl} class is a service implementation
 * of {@code OrderService} interface
 */
public class OrderServiceImpl implements OrderService {
    private final OrderDAO ORDER_DAO;
    private final ShipDAO SHIP_DAO;
    private final CruiseDAO CRUISE_DAO;


    public OrderServiceImpl(OrderDAO ORDER_DAO) {
        this.ORDER_DAO = ORDER_DAO;
        this.SHIP_DAO = new ShipDAOImpl();
        this.CRUISE_DAO = new CruiseDAOImpl();
    }

    /**
     * Receives order and saves it into DB
     *
     * @param order the instance of {@code Order} entity class
     * @return int of the new order Id from DB.
     */
    @Override
    public int addNewOrder(Order order) throws SQLException {

        return ORDER_DAO.addNewOrder(order);
    }

    /**
     * Responsible for getting the list of all Orders from DB.
     *
     * @return {@code List<Order>} the list of all Orders from DB.
     */
    @Override
    public List<Order> getAllOrders() {

        List<Order> orders = new ArrayList<>();

        try (Connection connection = MySQLConnectorManager.getConnection()) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = ORDER_DAO.getAllOrders(connection);

            orders = getOrdersFromResultSet(resultSet);

            MySQLConnectorManager.commitTransaction(connection);
            resultSet.close();

        } catch (SQLException e) {

        }
        return orders;
    }

    /**
     * Responsible for building the list of Orders instance from the list of data encapsulated in ResultSet.
     *
     * @param resultSet the {@code ResultSet} from {@code getAllOrders()} method.
     * @return {@code List} of {@code Order}from ResultSet.
     * @throws SQLException when persist DB fails
     */
    private List<Order> getOrdersFromResultSet(ResultSet resultSet) throws SQLException {

        List<Order> orders = new ArrayList<>();

        while (resultSet.next()) {

            Order order = new OrderBuilder()
                    .buildId(resultSet.getInt(1))
                    .buildUserId(resultSet.getInt(2))
                    .buildCruiseId(resultSet.getInt(3))
                    .buildStatus(OrderStatus.valueOf(resultSet.getString(4)))
                    .buildDate(resultSet.getDate(5).toLocalDate())
                    .build();

            orders.add(order);
        }

        return orders;
    }

    /**
     * Responsible for deleting specified Order from DB.
     *
     * @param orderId instance of {@code int} parameter specifies Order.
     * @throws SQLException when persist DB fails
     */
    @Override
    public void deleteOrderById(int orderId) throws SQLException {

        ORDER_DAO.deleteOrderById(orderId);
    }

    /**
     * Responsible for updating Order in DB
     *
     * @param order instance of {@code Order} entity class .
     * @throws SQLException when persist DB fails
     */
    @Override
    public void updateOrder(Order order) throws SQLException {
        ORDER_DAO.updateOrder(order);
    }

    /**
     * Responsible for getting Order instance with specified id.
     *
     * @param orderId instance of {@code int} parameter specifies Order.
     * @return {@code Order} instance with specified id.
     */
    @Override
    public Order getOrderById(int orderId) {

        Order order = new Order();

        try (Connection connection = MySQLConnectorManager.getConnection()) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = ORDER_DAO.getOrderById(orderId, connection);

            order = getOrderFromResultSet(resultSet);

            MySQLConnectorManager.commitTransaction(connection);

            resultSet.close();

        } catch (SQLException e) {

        }

        return order;
    }

    /**
     * Responsible for building Order instance from ResultSet.
     *
     * @param resultSet the {@code ResultSet} from {@code getOrderById()} method instance
     *                  encapsulating data of Order.
     * @return {@code Order} from ResultSet.
     * @throws SQLException when persist DB fails
     */
    private Order getOrderFromResultSet(ResultSet resultSet) throws SQLException {

        Order order = new Order();

        while (resultSet.next()) {

            order = new OrderBuilder()
                    .buildId(resultSet.getInt(1))
                    .buildUserId(resultSet.getInt(2))
                    .buildCruiseId(resultSet.getInt(3))
                    .buildStatus(OrderStatus.valueOf(resultSet.getString(4)))
                    .buildDate(resultSet.getDate(5).toLocalDate())
                    .build();
        }
        return order;
    }

    /**
     * Responsible for getting number of paid orders as instance of int from ResultSet.
     *
     * @param resultSet the {@code ResultSet} from {@code getNumOfPaidOrdersByCruiseId()} method instance
     *                  encapsulating data of int.
     * @return {@code int} from ResultSet.
     * @throws SQLException when persist DB fails
     */
    private int getNumOfPaidOrdersByCruiseIdFromResultSet(ResultSet resultSet) throws SQLException {

        List<Integer> nums = new ArrayList<>();

        while (resultSet.next()) {

            nums.add(resultSet.getInt(1));
        }

        return nums.get(0);
    }

    /**
     * Responsible for getting List of Integers int from ResultSet.
     *
     * @param resultSet the {@code ResultSet} from {@code getUserListByCruiseId()} method instance
     *                  encapsulating data of UserIds.
     * @return {@code List<Integer>} from ResultSet.
     * @throws SQLException when persist DB fails
     */

    private List<Integer> getUserIdsFromResultSet(ResultSet resultSet) throws SQLException {

        List<Integer> userIds = new ArrayList<>();

        while (resultSet.next()) {

            userIds.add(resultSet.getInt(1));
        }

        return userIds;
    }

    /**
     * Responsible for getting  instance of int from ResultSet.
     *
     * @param resultSet the {@code ResultSet} from {@code getOrderIdByCruiseIdUserId()} method instance
     *                  encapsulating data of int.
     * @return {@code int} from ResultSet.
     * @throws SQLException when persist DB fails
     */
    private int getOrderIdFromResultSet(ResultSet resultSet) throws SQLException {

        List<Integer> orderIds = new ArrayList<>();

        while (resultSet.next()) {

            orderIds.add(resultSet.getInt(1));
        }

        return orderIds.get(0);
    }

    /**
     * Responsible for getting List of Integers int from ResultSet.
     *
     * @param resultSet the {@code ResultSet} from {@code getOrderIdListByCruiseFinishDate()} method instance
     *                  encapsulating data of {@code List} of {@code Integer}.
     * @return {@code List} of {@code Integer}from ResultSet.
     * @throws SQLException when persist DB fails
     */
    private List<Integer> getOrderIdsFromResultSet(ResultSet resultSet) throws SQLException {

        List<Integer> orderIds = new ArrayList<>();

        while (resultSet.next()) {

            orderIds.add(resultSet.getInt(1));
        }

        return orderIds;
    }

    /**
     * Responsible for getting number of orders with status PAID by specified cruise id from DB.
     *
     * @param cruiseId the {@code int} parameter, specifies cruise.
     * @return {@code int} with  the data of orders number.
     */
    public int getNumOfPaidOrdersByCruiseId(int cruiseId) {
        int num = 0;
        try (Connection connection = MySQLConnectorManager.getConnection()) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = ORDER_DAO.getNumPaidOrderByCruiseId(cruiseId, connection);

            num = getNumOfPaidOrdersByCruiseIdFromResultSet(resultSet);

            MySQLConnectorManager.commitTransaction(connection);
            resultSet.close();

        } catch (SQLException e) {

        }
        return num;
    }

    /**
     * Responsible for getting List of User Ids with specified cruise id from DB.
     *
     * @param cruiseId the {@code int} parameter, specifies cruise.
     * @return {@code List} of {@code Integer} with the Ids of Users.
     */
    public List<Integer> getUserListByCruiseId(int cruiseId) {

        List<Integer> userIds = new ArrayList<>();
        try (Connection connection = MySQLConnectorManager.getConnection()) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = ORDER_DAO.getUserListByCruiseId(cruiseId, connection);

            userIds = getUserIdsFromResultSet(resultSet);

            MySQLConnectorManager.commitTransaction(connection);
            resultSet.close();

        } catch (SQLException e) {

        }

        return userIds;
    }

    /**
     * Responsible for getting Order Id with specified cruise id and user's Id from DB.
     *
     * @param cruiseId the {@code int} parameter, specifies cruise.
     * @param userId   the {@code int} parameter, specifies user.
     * @return {@code int} with the data of Order's Id.
     */
    public int getOrderIdByCruiseIdUserId(int cruiseId, int userId) {
        int orderId = 0;
        try (Connection connection = MySQLConnectorManager.getConnection()) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = ORDER_DAO.getOrderIdsByCruiseIdUserId(cruiseId, userId, connection);

            orderId = getOrderIdFromResultSet(resultSet);

            MySQLConnectorManager.commitTransaction(connection);
            resultSet.close();

        } catch (SQLException e) {

        }
        return orderId;
    }

    /**
     * Responsible for getting List of Order Ids with specified cruise's finish date from DB.
     *
     * @param cruiseFinishDate instance of LocalDate {@code LocalDate} parameter, specifies LocalDate.
     * @return {@code List} of {@code Integer} with the data of Orders' Ids.
     */
    public List<Integer> getOrderIdListByCruiseFinishDate(LocalDate cruiseFinishDate) {

        List<Integer> orderIds = new ArrayList<>();
        try (Connection connection = MySQLConnectorManager.getConnection()) {

            MySQLConnectorManager.startTransaction(connection);

            ResultSet resultSet = ORDER_DAO.getOrderIdsByCruiseFinishDate(cruiseFinishDate, connection);

            orderIds = getOrderIdsFromResultSet(resultSet);

            MySQLConnectorManager.commitTransaction(connection);
            resultSet.close();

        } catch (SQLException e) {

        }
        return orderIds;
    }
}
