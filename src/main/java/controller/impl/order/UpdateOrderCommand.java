package controller.impl.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.Command;
import dao.impl.OrderDAOImpl;
import dao.impl.UserDAOImpl;
import domain.Order;
import domain.OrderStatus;
import domain.Ship;
import domain.builder.OrderBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.OrderService;
import service.ShipService;
import service.UserService;
import service.impl.OrderServiceImpl;
import service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class UpdateOrderCommand implements Command {
    private final OrderService SERVICE;
    private static final Logger LOGGER =  LogManager.getLogger(UpdateOrderCommand.class);

    public UpdateOrderCommand(OrderService service) {
        this.SERVICE = service;
    }

    /**
     * Receives request and response gets Cruise from request,
     * checks Cruise for existing and persists new Cruise to data base.
     * <p>
     * if Cruise exists, sets response status 406.
     *
     * @param request  {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

       /* try {

            Order order = getOrderFromRequest(request);

            SERVICE.updateOrder(order);

        } catch (SQLException | IOException e) {

            response.setStatus(406);
            return;
        }

        response.setStatus(200);*/


        String userLogPass = request.getParameter("userLogPass");
        String userStatus = request.getParameter("userStatus");
        String[] numbers = userLogPass.split(" ");
        UserService userService = new UserServiceImpl(new UserDAOImpl());
        OrderService orderService=new OrderServiceImpl(new OrderDAOImpl());
        int cruiseId =Integer.parseInt(request.getParameter("cruiseId"));
       // orderService.getOrderById()
        try {
            int orderId =orderService.getOrderIdByCruiseIdUserId(cruiseId,userService.idByLogPas(numbers[0],numbers[1]));
           Order o= orderService.getOrderById(orderId);
           o.setStatus(OrderStatus.valueOf(userStatus));
            orderService.updateOrder(o);
            LOGGER.info("Order N "+orderId+" is updated");

        } catch (SQLException throwables) {
            LOGGER.error("Order can't be updated");
            throwables.printStackTrace();
        }


    }

    /**
     * Receives request gets builds Cruise from it.
     *
     * @param request {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     */
    private Order getOrderFromRequest(HttpServletRequest request) throws IOException {

        BufferedReader reader = request.getReader();

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(reader, Order.class);

    }
}
