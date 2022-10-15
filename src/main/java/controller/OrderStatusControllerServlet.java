package controller;

import dao.impl.CruiseDAOImpl;
import dao.impl.OrderDAOImpl;
import dao.impl.ShipDAOImpl;
import dao.impl.UserDAOImpl;
import domain.Cruise;
import domain.Order;
import domain.User;
import service.CruiseService;
import service.OrderService;
import service.ShipService;
import service.UserService;
import service.impl.CruiseServiceImpl;
import service.impl.OrderServiceImpl;
import service.impl.ShipServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static util.Constants.ADMIN_ORDER_STATUS_CONTROLLER_SERVLET;
import static util.Constants.USER_CONTROLLER_SERVLET;

@WebServlet(urlPatterns = ADMIN_ORDER_STATUS_CONTROLLER_SERVLET)
public class OrderStatusControllerServlet extends HttpServlet {

    private UserService userService;
    private ShipService shipService;
    private CruiseService cruiseService;
    private OrderService orderService;

    public OrderStatusControllerServlet() {
        userService = new UserServiceImpl(new UserDAOImpl());
        shipService = new ShipServiceImpl(new ShipDAOImpl());
        cruiseService = new CruiseServiceImpl(new CruiseDAOImpl());
        orderService = new OrderServiceImpl(new OrderDAOImpl());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        request.setCharacterEncoding("UTF-8");


        Integer cruiseId = Integer.valueOf(request.getParameter("cruiseRoute"));


        List<Integer> userIds = null;
        try {
            userIds = orderService.getUserListByCruiseId(cruiseId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        /*List<User> userList = new ArrayList<>();
        for (Integer userId : userIds) {
            try {
                userList.add(userService.getUserById(userId));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }*/
        List<Order> orderList = new ArrayList<>();
        for(Integer userId : userIds) {
            try {
                orderList.add(orderService.getOrderById(orderService.getOrderIdByCruiseIdUserId(cruiseId, userId)));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        Map<Order, User> mapOrderUser= new HashMap<>();
        for(Order o: orderList) {
            try {
                mapOrderUser.put(orderService.getOrderById(orderService.getOrderIdByCruiseIdUserId(cruiseId, o.getUserId())),
                        userService.getUserById(o.getUserId()));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        request.setAttribute("mapOrderUser", mapOrderUser);

        request.setAttribute("cruiseId", cruiseId);
        //mapOrderUser.get()
        //request.setAttribute("orderList", orderList);
        req.getServletContext().getRequestDispatcher("/change_status_for_user_by_admin.jsp").forward(request, response);
    }
}
