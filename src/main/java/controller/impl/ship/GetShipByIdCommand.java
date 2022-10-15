package controller.impl.ship;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.Command;
import domain.Ship;
import service.ShipService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static util.Constants.*;

public class GetShipByIdCommand implements Command {
    private final ShipService SERVICE;

    public GetShipByIdCommand(ShipService service) {
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

            Ship ship = SERVICE.getShipById(shipId);

            response.setContentType(CONTENT_TYPE);
            response.setCharacterEncoding(ENCODING);

            String value = OBJECT_MAPPER.writeValueAsString(ship);

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
     */
    private int getShipIdFromRequest(HttpServletRequest request) throws IOException {

        String shipId = request.getParameter("ship_id");

        return Integer.parseInt(shipId);
    }
}
