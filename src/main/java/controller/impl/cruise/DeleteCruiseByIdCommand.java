package controller.impl.cruise;

import controller.Command;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import service.CruiseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

public class DeleteCruiseByIdCommand implements Command {
    private final CruiseService SERVICE;
    private static final Logger LOGGER =  LogManager.getLogger(DeleteCruiseByIdCommand.class);

    public DeleteCruiseByIdCommand(CruiseService service) {
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

            int cruiseId = getCruiseIdFromRequest(request);

            SERVICE.deleteCruiseById(cruiseId);
            LOGGER.info("Cruise N "+cruiseId+" is deleted");

        } catch (SQLException | IOException e) {
            LOGGER.error("Cruise can't be deleted");
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
    private int getCruiseIdFromRequest(HttpServletRequest request) throws IOException {

        String cruiseId = request.getParameter("cruise_id");

        return Integer.parseInt(cruiseId);
    }
}
