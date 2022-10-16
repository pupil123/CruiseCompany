package controller.impl.ship;

import controller.Command;
import controller.impl.cruise.DeleteCruiseByIdCommand;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.CruiseService;
import service.ShipService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class DeleteShipByIdCommand implements Command {
    private final ShipService SERVICE;
    private static final Logger LOGGER = LogManager.getLogger(DeleteShipByIdCommand.class);

    public DeleteShipByIdCommand(ShipService service) {
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

            int shipId = getShipIdFromRequest(request);

            SERVICE.deleteShipById(shipId);
            LOGGER.info("Ship N " + shipId + " is deleted");
        } catch (SQLException e) {
            LOGGER.error("Ship can't be deleted");
            response.setStatus(406);
            return;
        }

        response.setStatus(200);
    }

    /**
     * Receives request gets  CruiseId from it.
     *
     * @param request {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @return ship's Id {@code int} from request
     */
    private int getShipIdFromRequest(HttpServletRequest request) {

        String shipId = request.getParameter("ship_id");

        return Integer.parseInt(shipId);
    }
}
