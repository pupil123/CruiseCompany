package controller.impl.ship;

import com.fasterxml.jackson.databind.ObjectMapper;
import controller.Command;
import controller.impl.order.UpdateOrderCommand;
import domain.Cruise;
import domain.Ship;
import domain.builder.CruiseBuilder;
import domain.builder.ShipBuilder;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.ShipService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;


/**
 * The {@code AddNewRouteCommand} class is an implementation of
 * {@code Command} interface, that is responsible for creating new route.
 */
public class AddNewShipCommand implements Command {

    private final ShipService SERVICE;
    private static final Logger LOGGER = LogManager.getLogger(AddNewShipCommand.class);

    public AddNewShipCommand(ShipService service) {
        this.SERVICE = service;
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
    public void process(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            //Ship ship = getShipFromRequest(request);

            String capacity = request.getParameter("capacity");
            String numOfVisitedPorts = request.getParameter("num visited ports");
            String staffs = request.getParameter("staffs");
            String route = request.getParameter("route");

            Ship ship = new ShipBuilder()
                    .buildCapacity(Integer.parseInt(capacity))
                    .buildNumberPortsVisited(Integer.parseInt(numOfVisitedPorts))
                    .buildStuffs(staffs)
                    .build();


            int newShipId = SERVICE.addNewShip(ship);
            LOGGER.info("Ship N " + newShipId + " is added");
            response.sendRedirect("/display/addship?newShipId=" + newShipId);

        } catch (SQLException e) {
            LOGGER.error("Ship can't be added");
            response.setStatus(406);
            return;
        }
        response.setStatus(200);
    }

    /**
     * Receives request gets builds ship from it.
     *
     * @param request {@code HttpServletRequest} from {@code FrontControllerServlet} servlet
     */
    private Ship getShipFromRequest(HttpServletRequest request) throws IOException {

        String jsStr = request.getParameter("jsonShip");

//        Route route = null;

       /* try {
            JSONObject jsonObject = new JSONObject(jsStr);

            String name = jsonObject.getString("name");
            int id = jsonObject.getInt("id");

            route = new RouteBuilder()
                    .buildId(id)
                    .buildName(name)
                    .build();


        } catch (JSONException e) {
            //log4j
        }*/
        BufferedReader reader = request.getReader();

        ObjectMapper objectMapper = new ObjectMapper();

        Ship ship = objectMapper.readValue(reader, Ship.class);

        return ship;
    }
}
