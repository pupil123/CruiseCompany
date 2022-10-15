package controller.impl.order;

import controller.Command;
import controller.impl.cruise.DeleteCruiseByIdCommand;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.OrderService;
import service.ShipService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class DeleteOrderByIdCommand implements Command {
    private final OrderService SERVICE;
    private static final Logger LOGGER =  LogManager.getLogger(DeleteOrderByIdCommand.class);

    public DeleteOrderByIdCommand(OrderService service) {
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

            SERVICE.deleteOrderById(orderId);
            LOGGER.info("Order N "+orderId+" is deleted");

        } catch (SQLException | IOException e) {
            LOGGER.error("Order can't be deleted");
            response.setStatus(406);
            return;
        }

        response.setStatus(200);
    }

    /**
     * Receives request gets  CruiseId from it.
     *
     * @param request {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     */
    private int getOrderIdFromRequest(HttpServletRequest request) throws IOException {

        String orderId = request.getParameter("order_id");

        return Integer.parseInt(orderId);
    }
}
