package controller.impl.order;

import controller.Command;
import domain.Order;
import domain.Ship;
import service.OrderService;
import service.ShipService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static util.Constants.*;

public class GetAllOrdersCommand implements Command {
    private final OrderService SERVICE;

    public GetAllOrdersCommand(OrderService service) {
        this.SERVICE = service;
    }

    /**
     * Receives request and response gets CruiseId from request,
     * checks Cruise for existing and persists new Cruise to data base.
     * <p>
     * if Cruise exists, sets response status 406.
     *
     * @param request  {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @param response {@code HttpServletResponse} from {@code FrontControllerServlet} servlet
     */
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {

        try {

            List<Order> orders = SERVICE.getAllOrders();

            response.setContentType(CONTENT_TYPE);
            response.setCharacterEncoding(ENCODING);

            String value = OBJECT_MAPPER.writeValueAsString(orders);

            response.getWriter().println(value);

        } catch (SQLException | IOException e) {

            response.setStatus(406);
            return;
        }

        response.setStatus(200);
    }


}
