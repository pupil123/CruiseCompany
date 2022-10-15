package filter;

import dao.impl.CruiseDAOImpl;
import dao.impl.OrderDAOImpl;
import dao.impl.ShipDAOImpl;
import dao.impl.UserDAOImpl;
import domain.Cruise;
import domain.Order;
import domain.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.CruiseService;
import service.OrderService;
import service.ShipService;
import service.UserService;
import service.impl.CruiseServiceImpl;
import service.impl.OrderServiceImpl;
import service.impl.ShipServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

import static util.Constants.FILTER_ORDER;
import static util.Constants.FILTER_ORDER_OUT;

@WebFilter(urlPatterns = FILTER_ORDER_OUT)
public class OrderOutFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger(OrderOutFilter.class);
    private final OrderService ORDER_SERVICE;
    private final CruiseService CRUISE_SERVICE;
    private final ShipService SHIP_SERVICE;
    private final UserService USER_SERVICE;

    public OrderOutFilter() {
        super();
        ORDER_SERVICE = new OrderServiceImpl(new OrderDAOImpl());
        CRUISE_SERVICE = new CruiseServiceImpl(new CruiseDAOImpl());
        SHIP_SERVICE = new ShipServiceImpl(new ShipDAOImpl());
        USER_SERVICE = new UserServiceImpl(new UserDAOImpl());
    }


    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain filterChain) throws IOException, ServletException {

        request.setCharacterEncoding("UTF-8");
        // filterChain.doFilter(request, response);

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;

        int newOrderId = Integer.parseInt(request.getParameter("newOrderId"));

        Order orderById = null;
        try {
            orderById = ORDER_SERVICE.getOrderById(newOrderId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        User userId1 = null;
        try {
            userId1 = USER_SERVICE.getUserById(orderById.getUserId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Cruise cruiseId1 = null;
        try {
            cruiseId1 = CRUISE_SERVICE.getCruiseById(orderById.getCruiseId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
       /* response.getWriter().println("Order N" + newOrderId + "\n" +
                "First Name : " + userId1.getFirstName() + "\n" +
                "Last Name : " + userId1.getLastName() + "\n" +
                "Start date : " + cruiseId1.getStartDate().toString() + "\n" +
                "Route : " + cruiseId1.getRoute() + "\n" +
                "Ship Num : " + cruiseId1.getShipId());*/

        String orderInfo = "Order N" + newOrderId + "\n" +
                "First Name : " + userId1.getFirstName() + "\n" +
                "Last Name : " + userId1.getLastName() + "\n" +
                "Start date : " + cruiseId1.getStartDate().toString() + "\n" +
                "Route : " + cruiseId1.getRoute() + "\n" +
                "Ship Num : " + cruiseId1.getShipId() + "\n" +
                "Status : " + orderById.getStatus();

        byte[] barr = orderInfo.getBytes();
        ServletOutputStream out = response.getOutputStream();
        out.write(barr);
    }
}
