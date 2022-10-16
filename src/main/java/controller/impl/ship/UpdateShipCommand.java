package controller.impl.ship;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.Command;
import domain.Cruise;
import domain.Ship;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.CruiseService;
import service.ShipService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

public class UpdateShipCommand implements Command {
    private final ShipService SERVICE;
    private static final Logger LOGGER = LogManager.getLogger(UpdateShipCommand.class);

    public UpdateShipCommand(ShipService service) {
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

        try {

            Ship ship = getShipFromRequest(request);

            SERVICE.updateShip(ship);
            LOGGER.info("Ship N " + ship.getId() + " is updated");

        } catch (SQLException | IOException e) {
            LOGGER.error("Ship can't be updated");
            response.setStatus(406);
            return;
        }

        response.setStatus(200);
    }

    /**
     * Receives request gets builds Ship from it.
     *
     * @param request {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     * @throws IOException when process request or respond fails
     * @return {@code Ship} from request
     */
    private Ship getShipFromRequest(HttpServletRequest request) throws IOException {

        BufferedReader reader = request.getReader();

        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(reader, Ship.class);

    }
}
