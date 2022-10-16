package filter;

import dao.impl.CruiseDAOImpl;
import dao.impl.OrderDAOImpl;
import dao.impl.ShipDAOImpl;
import dao.impl.UserDAOImpl;
import domain.Cruise;
import domain.Order;
import domain.OrderStatus;
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


import static util.Constants.FILTER_ORDER_STATUS;

@WebFilter(urlPatterns = FILTER_ORDER_STATUS)
public class OrderStatusFilter implements Filter {

    private final OrderService ORDER_SERVICE;
    private final CruiseService CRUISE_SERVICE;
    private final ShipService SHIP_SERVICE;
    private final UserService USER_SERVICE;

    private static final Logger LOGGER = LogManager.getLogger(OrderStatusFilter.class);

    public OrderStatusFilter() {
        super();
        ORDER_SERVICE = new OrderServiceImpl(new OrderDAOImpl());
        CRUISE_SERVICE = new CruiseServiceImpl(new CruiseDAOImpl());
        SHIP_SERVICE = new ShipServiceImpl(new ShipDAOImpl());
        USER_SERVICE = new UserServiceImpl(new UserDAOImpl());
    }


    @Override
    public void doFilter(final ServletRequest request,
                         final ServletResponse response,
                         final FilterChain filterChain) throws IOException {

        request.setCharacterEncoding("UTF-8");

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;
        /*String userLogPass = request.getParameter("userLogPass");*/
        String userStatus = request.getParameter("userStatus");
       /* String[] numbers = userLogPass.split(" ");
        UserService userService = new UserServiceImpl(new UserDAOImpl());*/
        OrderService orderService = new OrderServiceImpl(new OrderDAOImpl());
       // int cruiseId = Integer.parseInt(request.getParameter("cruiseId"));
        int orderId = Integer.parseInt(request.getParameter("userLogPass"));


        try {
            /*int orderId = orderService.getOrderIdByCruiseIdUserId(cruiseId, userService.idByLogPas(numbers[0], numbers[1]));*/
            Order o = orderService.getOrderById(orderId);
            o.setStatus(OrderStatus.valueOf(userStatus));
            orderService.updateOrder(o);

            Cruise cruise = CRUISE_SERVICE.getCruiseById(o.getCruiseId());
            req.getSession().setAttribute("cruise", cruise);
            User user = USER_SERVICE.getUserById(o.getUserId());
            req.getSession().setAttribute("user", user);
            req.getSession().setAttribute("order", o);
            //request.setAttribute("order", order);
            //request.getRequestDispatcher("/OrderPage.jsp").forward(request, response);
            LOGGER.info("Order N " + orderId + " is updated");
            res.sendRedirect("/OrderPage.jsp?newOrderId=" + orderId);


            //  res.sendRedirect("/filter/order/out?newOrderId=" + orderId);
        } catch (SQLException throwables) {
            LOGGER.error("Order can't be updated");
            throwables.printStackTrace();
        }
    }
}
