package service;

import dao.impl.CruiseDAOImpl;
import dao.impl.OrderDAOImpl;
import dao.impl.ShipDAOImpl;
import dao.impl.UserDAOImpl;
import domain.*;
import domain.builder.CruiseBuilder;
import domain.builder.OrderBuilder;
import domain.builder.ShipBuilder;
import domain.builder.UserBuilder;
import org.junit.Assert;
import org.junit.Test;
import service.impl.CruiseServiceImpl;
import service.impl.OrderServiceImpl;
import service.impl.ShipServiceImpl;
import service.impl.UserServiceImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceImplTest {

    private OrderService orderService = new OrderServiceImpl(new OrderDAOImpl());
    private ShipService shipService = new ShipServiceImpl(new ShipDAOImpl());
    private UserService userService = new UserServiceImpl(new UserDAOImpl());
    private CruiseService cruiseService = new CruiseServiceImpl(new CruiseDAOImpl());

    @Test
    public void shouldAddNewOrder() throws SQLException, IOException {
        Ship ship = new ShipBuilder()
                .buildCapacity(20)
                .buildNumberPortsVisited(5)
                .buildStuffs("MyStaff")
                .build();

        int shipId = shipService.addNewShip(ship);

        User user = new UserBuilder()
                .buildPassword("123456")
                .buildLogin("a")
                .buildFirstName("A")
                .buildLastName("V")
                .build();
        int userId = userService.addNewUser(user);

        Cruise cruise = new CruiseBuilder()
                .buildStartDate(LocalDate.of(2021, Month.JANUARY, 5))
                .buildFinishDate(LocalDate.of(2021, Month.APRIL, 5))
                .buildRoute("MyRoute")
                .buildShipId(shipId)
                .build();

        int cruiseId = cruiseService.addNewCruise(cruise);

        Order order = new OrderBuilder()
                .buildStatus(OrderStatus.UNPAID)
                .buildDate(LocalDate.now())
                .buildCruiseId(cruiseId)
                .buildUserId(userId)
                .build();

        int orderId = orderService.addNewOrder(order);

        Order order1 = orderService.getOrderById(orderId);

        Assert.assertEquals(order1.getCruiseId(), cruiseId);
        Assert.assertEquals(order1.getUserId(), userId);

        orderService.deleteOrderById(orderId);
        userService.deleteUserById(userId);
        cruiseService.deleteCruiseById(cruiseId);
        shipService.deleteShipById(shipId);

    }

    @Test
    public void shouldUpdateOrder() throws SQLException, IOException {

        Order order1 = saveOrder();

        order1.setStatus(OrderStatus.COMPLETED);

        orderService.updateOrder(order1);

        Order order3 = orderService.getOrderById(order1.getId());

        Assert.assertEquals(order3.getStatus(), order1.getStatus());
        deleteTestItems(order1);

    }

    @Test
    public void shouldDeleteOrder() throws SQLException, IOException {

        List<Order> allOrders1 = orderService.getAllOrders();

        Order order1 = saveOrder();

        orderService.deleteOrderById(order1.getId());

        List<Order> allOrders2 = orderService.getAllOrders();

        Assert.assertEquals(allOrders1, allOrders2);

        userService.deleteUserById(order1.getUserId());
        int shipId = cruiseService.getCruiseById(order1.getCruiseId()).getShipId();
        cruiseService.deleteCruiseById(order1.getCruiseId());
        shipService.deleteShipById(shipId);

    }

    @Test
    public void shouldGetOrderById() throws SQLException, IOException {

        Order order1 = saveOrder();

        Order order2 = orderService.getOrderById(order1.getId());

        Assert.assertEquals(order1, order2);
        deleteTestItems(order1);
    }

    @Test
    public void shouldGetNumPaidOrderByCruiseId() throws SQLException, IOException {

        Order order1 = saveOrder();
        List<Order> allOrders1 = orderService.getAllOrders();
        int numPaidOrders = 0;
        for (Order o : allOrders1) {
            if (o.getStatus().equals(OrderStatus.PAID) &&
                    o.getCruiseId() == order1.getCruiseId())
                numPaidOrders++;
        }

        Assert.assertEquals(numPaidOrders, orderService.getNumOfPaidOrdersByCruiseId(order1.getCruiseId()));
        deleteTestItems(order1);
    }

    @Test
    public void shouldGetUserListByCruiseId() throws SQLException, IOException {

        Order order1 = saveOrder();
        List<Order> allOrders1 = orderService.getAllOrders();
        List<Integer> userIds = new ArrayList();
        ;
        for (Order o : allOrders1) {
            if (o.getCruiseId() == order1.getCruiseId())
                userIds.add(Integer.valueOf(o.getUserId()));
        }

        Assert.assertEquals(userIds, orderService.getUserListByCruiseId(order1.getCruiseId()));
        deleteTestItems(order1);
    }


    @Test
    public void shouldOrderIdByCruiseIdUserId() throws SQLException, IOException {

        Order order1 = saveOrder();
        List<Order> allOrders1 = orderService.getAllOrders();
        int orderId = 0;
        for (Order o : allOrders1) {
            if (o.getCruiseId() == order1.getCruiseId() && o.getUserId() == order1.getUserId())
                orderId = order1.getId();
        }

        Assert.assertEquals(orderId, orderService.getOrderIdByCruiseIdUserId(order1.getCruiseId(), order1.getUserId()));
        deleteTestItems(order1);
    }

    @Test
    public void shouldGetOrderIdListByCruiseFinishDate() throws SQLException, IOException {

        Order order1 = saveOrder();
        List<Order> allOrders1 = orderService.getAllOrders();
        List<Integer> orderIds = new ArrayList();
        ;
        for (Order o : allOrders1) {
            if (cruiseService.getCruiseById(o.getCruiseId()).getFinishDate().isEqual(
                    cruiseService.getCruiseById(order1.getCruiseId()).getFinishDate()))
                orderIds.add(Integer.valueOf(o.getId()));
        }

        Assert.assertEquals(orderIds,
                orderService.getOrderIdListByCruiseFinishDate(cruiseService.getCruiseById(order1.getCruiseId()).getFinishDate()));
        deleteTestItems(order1);
    }

    @Test
    public void shouldGetAllOrders() throws SQLException, IOException {
        List<Order> allOrders1 = orderService.getAllOrders();
        Order order1 = saveOrder();
        List<Order> allOrders2 = orderService.getAllOrders();

        Assert.assertEquals(allOrders1.size() + 1, allOrders2.size());
        deleteTestItems(order1);
    }

    private void deleteTestItems(Order order1) throws SQLException {
        orderService.deleteOrderById(order1.getId());
        userService.deleteUserById(order1.getUserId());
        int shipId = cruiseService.getCruiseById(order1.getCruiseId()).getShipId();
        cruiseService.deleteCruiseById(order1.getCruiseId());
        shipService.deleteShipById(shipId);
    }

    private Order saveOrder() throws IOException, SQLException {
        Ship ship = new ShipBuilder()
                .buildCapacity(20)
                .buildNumberPortsVisited(5)
                .buildStuffs("MyStuff")
                .build();

        int shipId = shipService.addNewShip(ship);

        User user = new UserBuilder()
                .buildPassword("123456")
                .buildLogin("a")
                .buildFirstName("A")
                .buildLastName("V")
                .build();
        int userId = userService.addNewUser(user);

        Cruise cruise = new CruiseBuilder()
                .buildStartDate(LocalDate.of(2021, Month.JANUARY, 5))
                .buildFinishDate(LocalDate.of(2021, Month.APRIL, 5))
                .buildRoute("MyRoute")
                .buildShipId(shipId)
                .build();

        int cruiseId = cruiseService.addNewCruise(cruise);

        Order order = new OrderBuilder()
                .buildStatus(OrderStatus.UNPAID)
                .buildDate(LocalDate.now())
                .buildCruiseId(cruiseId)
                .buildUserId(userId)
                .build();

        int orderId = orderService.addNewOrder(order);

        order.setId(orderId);

        return order;

    }
}
