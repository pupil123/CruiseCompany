package controller.impl.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.Command;
import controller.impl.cruise.UpdateCruiseCommand;
import dao.impl.CruiseDAOImpl;
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
import service.impl.ShipServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class AddNewOrderCommand implements Command {
    private final OrderService ORDER_SERVICE;
    private final CruiseService CRUISE_SERVICE;
    private final ShipService SHIP_SERVICE;
    private final UserService USER_SERVICE;

    private static final Logger LOGGER = LogManager.getLogger(AddNewOrderCommand.class);

   /* static int orderId = 0;

    public int getOrderId() {
        return orderId;
    }*/

    public AddNewOrderCommand(OrderService service) {
        this.ORDER_SERVICE = service;
        CRUISE_SERVICE = new CruiseServiceImpl(new CruiseDAOImpl());
        SHIP_SERVICE = new ShipServiceImpl(new ShipDAOImpl());
        USER_SERVICE = new UserServiceImpl(new UserDAOImpl());

    }

    /**
     * Receives request and response gets route from request,
     * checks route for existing and persists new route to data base.
     * <p>
     * if route exists, sets response status 406.
     *
     * @param request  {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        try {
            int successAdd = 0;
            int userId = getUserIdFromRequest(request);
            String lang = request.getParameter("Lang");

            String cruiseRoute = request.getParameter("cruiseRoute");
            String[] routeShipId = cruiseRoute.split(" by ship ");

            LocalDate startDate = getLocalDateFromRequest(request);

            int cruiseId = CRUISE_SERVICE.getCruiseIdByDateRoute(startDate, routeShipId[0]);

            Order order = new OrderBuilder()
                    .buildUserId(userId)
                    .buildCruiseId(cruiseId)
                    .buildDate(LocalDate.now())
                    .buildStatus(OrderStatus.UNPAID)
                    .build();

            int num =
                    ORDER_SERVICE.getNumOfPaidOrdersByCruiseId(cruiseId);
            int capacity =
                    SHIP_SERVICE.getShipById(CRUISE_SERVICE.getCruiseById(cruiseId).getShipId()).getCapacity();

            if (num < capacity) {
                int numOrders = ORDER_SERVICE.getAllOrders().size();

                int newOrderId = ORDER_SERVICE.addNewOrder(order);
                //  orderId = newOrderId;
                LOGGER.info("Order " + newOrderId + " is added");

                int numOrdersNew = ORDER_SERVICE.getAllOrders().size();

                if (numOrdersNew == numOrders + 1) {
                    successAdd = 1;
                    //       request.setAttribute("successAdd", successAdd);
                    //       request.setAttribute("newOrderId", newOrderId);
                    //    request.getServletContext().getRequestDispatcher("/display/addorder").forward(request, response);;}

                    response.sendRedirect("/display/addorder?successAdd=" +
                            successAdd + "&newOrderId=" + newOrderId);
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
                LOGGER.info("All tickets to cruise " + cruiseId + " are sold out");
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

        } catch (SQLException | IOException e) {
            LOGGER.error("Order can't be added");
            response.setStatus(406);
            return;
        }

        response.setStatus(200);
    }

    private LocalDate getLocalDateFromRequest(HttpServletRequest request) throws IOException {

        String localDateString = request.getParameter("startdate");
        return LocalDate.parse(localDateString);
    }

    /**
     * Receives request gets  CruiseId from it.
     *
     * @param request {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @return parsed user's Id {@code int} from request
     */
    private int getUserIdFromRequest(HttpServletRequest request) {

        String userId = request.getParameter("userId");

        return Integer.parseInt(userId);
    }

    /**
     * Receives request gets builds Order from it.
     *
     * @param request {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @return {@code Order} from rtequest
     * @throws IOException when process request fails
     */
    private Order getOrderFromRequest(HttpServletRequest request) throws IOException {

        BufferedReader reader = request.getReader();

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(reader, Order.class);
    }
}
