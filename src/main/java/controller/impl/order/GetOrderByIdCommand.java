package controller.impl.order;

import controller.Command;
import domain.Order;
import service.OrderService;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static util.Constants.*;

public class GetOrderByIdCommand implements Command {
    private final OrderService SERVICE;

    public GetOrderByIdCommand(OrderService service) {
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

            int orderId = getOrderIdFromRequest(request);

            Order order = SERVICE.getOrderById(orderId);

            response.setContentType(CONTENT_TYPE);
            response.setCharacterEncoding(ENCODING);

            String value = OBJECT_MAPPER.writeValueAsString(order);

            response.getWriter().println(value);

        } catch (SQLException | IOException e) {

            response.setStatus(406);
            return;
        }

        response.setStatus(200);
    }

    /**
     * Receives request gets  CruiseId from it.
     *
     * @param request {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @return parsed int from String
     */
    private int getOrderIdFromRequest(HttpServletRequest request) {

        String orderId = request.getParameter("order_id");

        return Integer.parseInt(orderId);
    }
}
