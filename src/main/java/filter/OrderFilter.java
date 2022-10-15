package filter;

import controller.impl.order.AddNewOrderCommand;
import dao.impl.CruiseDAOImpl;
import dao.impl.OrderDAOImpl;
import dao.impl.ShipDAOImpl;
import dao.impl.UserDAOImpl;
import domain.Cruise;
import domain.Order;
import domain.OrderStatus;
import domain.User;
import domain.builder.OrderBuilder;
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
import java.time.LocalDate;


import static util.Constants.FILTER_ORDER;

@WebFilter(urlPatterns = FILTER_ORDER)
public class OrderFilter implements Filter {

    private final OrderService ORDER_SERVICE;
    private final CruiseService CRUISE_SERVICE;
    private final ShipService SHIP_SERVICE;
    private final UserService USER_SERVICE;

    private static final Logger LOGGER = LogManager.getLogger(OrderFilter.class);

    public OrderFilter() {
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
        // filterChain.doFilter(request, response);

        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;
        try {

            int userId = getUserIdFromRequest(req);
            String lang = request.getParameter("Lang");

            // String cruiseRoute = request.getParameter("cruiseRoute");
            //String[] routeShipId = cruiseRoute.split(" by ship ");

            LocalDate startDate = getLocalDateFromRequest(req);

            // String cr=request.getParameter("cruiseRoute");
            Integer cr = Integer.valueOf(request.getParameter("cruiseRoute"));
            // int cruiseId = CRUISE_SERVICE.getCruiseIdByDateRoute(startDate, routeShipId[0]);

            Order order = new OrderBuilder()
                    .buildUserId(userId)
                    .buildCruiseId(cr)
                    .buildDate(LocalDate.now())
                    .buildStatus(OrderStatus.UNPAID)
                    .build();

            int num =
                    ORDER_SERVICE.getNumOfPaidOrdersByCruiseId(cr);
            int capacity =
                    SHIP_SERVICE.getShipById(CRUISE_SERVICE.getCruiseById(cr).getShipId()).getCapacity();

            if (num < capacity) {
                int numOrders = ORDER_SERVICE.getAllOrders().size();

                int newOrderId = ORDER_SERVICE.addNewOrder(order);

                LOGGER.info("Order " + newOrderId + " is added");

                int numOrdersNew = ORDER_SERVICE.getAllOrders().size();

                if (numOrdersNew == numOrders + 1) {

                    //       request.setAttribute("successAdd", successAdd);
                    //       request.setAttribute("newOrderId", newOrderId);
                    //    request.getServletContext().getRequestDispatcher("/display/addorder").forward(request, response);;}



                   //   res.sendRedirect("/filter/order/out?newOrderId=" + newOrderId);
                    Cruise cruise = CRUISE_SERVICE.getCruiseById(cr);
                    req.getSession().setAttribute("cruise", cruise);
                    User user = USER_SERVICE.getUserById(userId);
                    req.getSession().setAttribute("user", user);
                    req.getSession().setAttribute("order", order);
                    //request.setAttribute("order", order);
                    //request.getRequestDispatcher("/OrderPage.jsp").forward(request, response);
                    res.sendRedirect("/OrderPage.jsp?newOrderId=" + newOrderId);
                    //res.sendRedirect("/OrderPage.jsp?order=" + order + "&cruise=" + cruise);
                }

               /* Order orderById = ORDER_SERVICE.getOrderById(newOrderId);
                User userId1 = USER_SERVICE.getUserById(orderById.getUserId());
                Cruise cruiseId1 = CRUISE_SERVICE.getCruiseById(orderById.getCruiseId());
                response.getWriter().println("Order N" + newOrderId + "\n" +
                        "First Name : " + userId1.getFirstName() + "\n" +
                        "Last Name : " + userId1.getLastName() + "\n" +
                        "Start date : " + cruiseId1.getStartDate().toString() + "\n" +
                        "Route : " + cruiseId1.getRoute() + "\n" +
                        "Ship Num : " + cruiseId1.getShipId()); */
            } else {
                LOGGER.info("All tickets to cruise " + cr + " are sold out");
                if (lang.equals("Ukr")) {
                    String s1 = "Вибачте, всі квитки на цей круїз розпродані." +
                            " Поверніться та оберіть інший круїз.";
                    byte[] barr = s1.getBytes();
                    ServletOutputStream out = response.getOutputStream();
                    out.write(barr);

                } else {
                    response.getWriter().println("Sorry, all tickets to this cruise are sold out." +
                            " Roll back and choose another cruise");
                }
            }

        } catch (SQLException | IOException  e) {
            LOGGER.error("Order can't be added");
            res.setStatus(406);
            return;
        }

        res.setStatus(200);
    }


    private LocalDate getLocalDateFromRequest(HttpServletRequest request) throws IOException {

        String localDateString = request.getParameter("startdate");
        return LocalDate.parse(localDateString);
    }

    /**
     * Receives request gets  CruiseId from it.
     *
     * @param request {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     */
    private int getUserIdFromRequest(HttpServletRequest request) throws IOException {

        String userId = request.getParameter("userId");

        return Integer.parseInt(userId);
    }
}
